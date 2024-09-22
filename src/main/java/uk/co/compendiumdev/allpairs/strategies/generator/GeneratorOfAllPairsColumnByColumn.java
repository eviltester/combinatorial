package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedMatchingPairFromListFinder;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.NextPairFinderStrategy;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.RandomMatchingPairFromListFinder;

import java.util.Comparator;
import java.util.List;


public class GeneratorOfAllPairsColumnByColumn {
    private final AllPairsLists pairCombinations;
    AllPairsResults results;

    public GeneratorOfAllPairsColumnByColumn(final AllPairsLists pairCombinations) {
        this.pairCombinations = pairCombinations;
    }

    public AllPairsResults generateResults(NextPairFinderStrategy pairFinderStrategy) {
        return generateResults(pairFinderStrategy, new LeastUsedMatchingPairFromListFinder());
    }

    public AllPairsResults generateResults(NextPairFinderStrategy pairFinderStrategy, NextPairFinderStrategy defaultPairFinderStrategy) {
        // work through pairsLists and add to results
        results = new AllPairsResults();
        while(results.pairsListsCount()<pairCombinations.countOf()){
            for(IndividualPairsList pairsList : pairCombinations.getPairsLists()){
                if(!results.includes(pairsList)){
                    System.out.println("Processing Pair " + pairsList.getLeftName() + " " + pairsList.getRightName());
                    // match the pairsList with the existing rows pairsList
                    // rows is sets of tuples (Enabled, value) (Choice type, value), (datasetName, value)
                    addTuplesForList(pairsList, pairCombinations, pairFinderStrategy, defaultPairFinderStrategy);
                    results.markListAsProcessed(pairsList);
                }
            }
        }
        return results;
    }



    private void addTuplesForList(final IndividualPairsList pairsList,
                                  AllPairsLists combinations,
                                  NextPairFinderStrategy pairFinderStrategy,
                                  NextPairFinderStrategy defaultPairFinderStrategy) {

        // need to find rows to add the pairs from this list to
        final IndividualPairsList clonedPairsToAdd = pairsList.cloneThis();

        // add the column names for the tuples
        results.addColumnNames(clonedPairsToAdd.getLeftName());
        results.addColumnNames(clonedPairsToAdd.getRightName());

        // work through each row and add the missing field i.e. fieldNameToAdd

        // TODO: support different row ordering strategies e.g. sparsest row first, added index, reverse adding, other?
        List<ResultsRow> rows = results.getRows();

        // by sparsest rows
        rows.sort(Comparator.comparingInt(ResultsRow::getColumnCount));
        // by most filled rows
        //rows.sort(Comparator.comparingInt(ResultsRow::getColumnCount).reversed());

        for(ResultsRow aRow : rows){

            // when processing a sparse array, sometimes we can add another pair side instead
            // assume we are starting from left to right
            String rowExistingFieldName = clonedPairsToAdd.getLeftName();
            String rowFieldNameToAdd = clonedPairsToAdd.getRightName();

            // if we already have data for both in this row, then skip this row
            if(aRow.containsColumnsWithValues(rowExistingFieldName, rowFieldNameToAdd)){
                System.out.println(
                        String.format("WARNING: row already contains a pair with these names - skipping row for this combination %s & %s - %s",
                                aRow.toString(), rowExistingFieldName, rowFieldNameToAdd));
                continue;
            }

            // just a quick check in case the row already contains the field we are looking for
            if(aRow.getCellFor(rowFieldNameToAdd)!=null){
                // this should probably be an error
                System.out.println(String.format("WARNING: row already contains a cell for the field we were going to add %s - %s (Switching fields)", rowFieldNameToAdd, aRow.toString()));
                // switch fields
                rowExistingFieldName = clonedPairsToAdd.getRightName();
                rowFieldNameToAdd = clonedPairsToAdd.getLeftName();
            }

            // get the data for the existingField from row
            NameValuePair existingColumnData = aRow.getCellFor(rowExistingFieldName);
            String existingColumnDataName = null;
            String existingColumnDataValue = null;
            if(existingColumnData==null){
                // adding a complete tuple
                //System.out.println(String.format("WARNING: could not find tuple for %s in row - assume processing sparse array, skipping row %s", rowExistingFieldName, aRow.toString()));
                // skip the row
                //continue;
            }else{
                existingColumnDataName = existingColumnData.getName();
                existingColumnDataValue = existingColumnData.getValue();
            }


            // apply the strategy to the clonedPairs - which is our list of things we really must add

            // apply a given NextPairFinderStrategy to first find a value from the cloned list to whittle down the immediate pairs

            pairFinderStrategy.
                    basedOnPairsList(clonedPairsToAdd).
                    fromCombinations(combinations).
                    basedOnCurrentRow(aRow).
                    withANameValuePair(existingColumnData).
                    matchingName(rowFieldNameToAdd);

            defaultPairFinderStrategy.
                    basedOnPairsList(clonedPairsToAdd).
                    fromCombinations(combinations).
                    basedOnCurrentRow(aRow).
                    withANameValuePair(existingColumnData).
                    matchingName(rowFieldNameToAdd);

            PairCombination pairToAdd = pairFinderStrategy.findMatchingPair();


            // if we have used all the high priority items in the cloned list then use the main list
            if(pairToAdd==null) {

                // apply the same matching strategy but use the full list rather than the cloned list
                System.out.println(String.format(
                        "WARNING: could not find a unique pair to add, applying strategy to full list for %s %s %s to %s",
                        rowExistingFieldName, existingColumnDataValue, rowFieldNameToAdd, aRow));

                pairToAdd = pairFinderStrategy.basedOnPairsList(pairsList).findMatchingPair();

                // if it is still null then apply the default strategy to the pairList
                if (pairToAdd == null) {
                    System.out.println(String.format(
                            "WARNING: could not find a unique pair to add, applying default strategy to full list for %s %s %s to %s",
                            rowExistingFieldName, existingColumnDataValue, rowFieldNameToAdd, aRow));
                    //pairToAdd = pairsList.filter().getLeastUsedPairMatching(existingColumnData);
                    pairToAdd = defaultPairFinderStrategy.basedOnPairsList(pairsList).matchingName(rowFieldNameToAdd).findMatchingPair();
                } else {
                    System.out.println(String.format("Applying strategy to full list resulted in a match %s%n", pairToAdd));
                }

                // default strategy might not be guaranteed to return a value so have a hard coded default guaranteed to return a value
                if(pairToAdd==null){
                    System.out.println(String.format(
                            "WARNING: default strategy used returned null HARD CODED random strategy being used for %s %s %s to %s",
                            rowExistingFieldName, existingColumnDataValue, rowFieldNameToAdd, aRow));
                    pairToAdd = new RandomMatchingPairFromListFinder().basedOnPairsList(pairsList).withANameValuePair(existingColumnData).findMatchingPair();
                }
            }

            // get current list of pairs in row
            List<PairCombination> existingPairsInRow = aRow.getPairs();

            System.out.println(String.format("Adding pair for %s - from list %s x %s - %s", rowFieldNameToAdd, pairsList.getLeftName(),  pairsList.getRightName(), pairToAdd.toString()));
                //columnValue = new NameValuePair(rowFieldNameToAdd, pairToAdd.getValueFor(rowFieldNameToAdd));
                aRow.addPair(pairToAdd);
                //aRow.addColumn(columnValue);

                // update the new pairs in the row
                combinations.updateUsageForPairs(aRow.pairsDiffFrom(existingPairsInRow));

                //pairToAdd.incrementUsage();
                // delete the pair if it is still in cloned
                clonedPairsToAdd.deleteCombination(pairToAdd);

            //incrementCountsForOtherPairedValuesInRow(combinations, rowExistingFieldName, rowFieldNameToAdd, aRow, columnValue);
        }

        if(clonedPairsToAdd.getPairs().size()>0){
            // we have some sparse combinations to add, these will need filling later
            for(PairCombination extraPair : clonedPairsToAdd.getPairs()){
                results.addPairToExistingOrNewSparseRow(extraPair, combinations);
            }
        }

        // results.debugPrintRows();

    }



}
