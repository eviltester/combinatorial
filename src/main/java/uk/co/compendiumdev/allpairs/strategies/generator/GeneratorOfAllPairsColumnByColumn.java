package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedMatchingPairFromListFinder;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.NextPairFinderStrategy;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.RandomMatchingPairFromListFinder;

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
                    if(results.countRows()==0){
                        // if first list then this is easy
                        addListAsRows(pairsList);
                    }else {
                        addTuplesForList(pairsList, pairCombinations, pairFinderStrategy, defaultPairFinderStrategy);
                    }
                    results.markListAsProcessed(pairsList);
                }
            }
        }
        return results;
    }

    private void addListAsRows(final IndividualPairsList pairsList) {
        for(PairCombination pair : pairsList.getPairs()){

            ResultsRow row = new ResultsRow();

            System.out.println(String.format("Adding initial pair list for %s x %s as %s", pairsList.getLeftName(), pairsList.getRightName(), pair.toString()));

            row.addPair(pair);
            pair.incrementUsage();

            results.addColumnNames(pairsList.getLeftName(), pairsList.getRightName());
            results.addRow(row);
        }
    }

    private void addTuplesForList(final IndividualPairsList pairsList, AllPairsLists combinations,
                                  NextPairFinderStrategy pairFinderStrategy,
                                  NextPairFinderStrategy defaultPairFinderStrategy) {

        // need to find rows to add the pair to
        final IndividualPairsList clonedPairsToAdd = pairsList.cloneThis();
        // what are we matching from the row?


        // identify which field in the pair is already existing in the results
        // and which is missing, which we need to add
        String existingFieldName = clonedPairsToAdd.getLeftName();
        String fieldNameToAdd = clonedPairsToAdd.getRightName();

        if( results.hasColumnNamed(fieldNameToAdd) && results.hasColumnNamed(existingFieldName) ){
            System.out.println(String.format("WARNING: results already exists with both fields %s, %s", existingFieldName, fieldNameToAdd));
        }

        if(!results.hasColumnNamed(existingFieldName)){

            existingFieldName = clonedPairsToAdd.getRightName();
            fieldNameToAdd = clonedPairsToAdd.getLeftName();

            if(!results.hasColumnNamed(existingFieldName)){
                System.out.println(String.format("ERROR: row contains neither field %s, %s", existingFieldName, fieldNameToAdd));
                new RuntimeException(String.format("ERROR: row contains neither field %s, %s", existingFieldName, fieldNameToAdd));
            }
        }

        // add the missing field as a column name
        // todo: this assumes we actually process it - what if we don't!
        results.addColumnNames(fieldNameToAdd);

        // work through each row and add the missing field i.e. fieldNameToAdd

        for(ResultsRow aRow : results.getRows()){

            // when processing a sparse array, sometimes we can add another pair side instead
            String rowExistingFieldName = existingFieldName;
            String rowFieldNameToAdd = fieldNameToAdd;

            // add an unmatched pair to this row
            // if we already have data for both in this row, then something went wrong somewhere
            if(aRow.containsPairNames(rowExistingFieldName, rowFieldNameToAdd)){
                System.out.println(
                        String.format("WARNING: row already contains a pair with these names - skipping row for this combination %s & %s",
                                aRow.toString(), clonedPairsToAdd.getPairs().get(0).toString()));
                continue;
            }

            // just a quick check in case the row already contains the field we are looking for
            if(aRow.getCellFor(rowFieldNameToAdd)!=null){
                // this should probably be an error
                System.out.println(String.format("WARNING: row already contains a cell for the field we were going to add %s - %s", rowFieldNameToAdd, aRow.toString()));
            }

            // get the data for the existingField from row
            NameValuePair existingColumnData = aRow.getCellFor(rowExistingFieldName);
            if(existingColumnData==null){
                // this should probably be an error
                System.out.println(String.format("WARNING: could not find tuple for %s in row - assume processing sparse array, skipping row %s", rowExistingFieldName, aRow.toString()));
                // skip the row
                continue;
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

            NameValuePair columnValue=null;

            // if we have used all the high priority items in the cloned list then use the main list
            if(pairToAdd==null) {

                // apply the same matching strategy but use the full list rather than the cloned list
                System.out.println(String.format(
                        "WARNING: could not find a unique pair to add, applying strategy to full list for %s %s %s to %s",
                        rowExistingFieldName, existingColumnData.getValue(), rowFieldNameToAdd, aRow));

                pairToAdd = pairFinderStrategy.basedOnPairsList(pairsList).findMatchingPair();

                // if it is still null then apply the default strategy to the pairList
                if (pairToAdd == null) {
                    System.out.println(String.format(
                            "WARNING: could not find a unique pair to add, applying default strategy to full list for %s %s %s to %s",
                            rowExistingFieldName, existingColumnData.getValue(), rowFieldNameToAdd, aRow));
                    //pairToAdd = pairsList.filter().getLeastUsedPairMatching(existingColumnData);
                    pairToAdd = defaultPairFinderStrategy.basedOnPairsList(pairsList).matchingName(rowFieldNameToAdd).findMatchingPair();
                } else {
                    System.out.println(String.format("Applying strategy to full list resulted in a match %s%n", pairToAdd));
                }

                // default strategy might not be guaranteed to return a value so have a hard coded default guaranteed to return a value
                if(pairToAdd==null){
                    System.out.println(String.format(
                            "WARNING: default strategy used returned null HARD CODED random strategy being used for %s %s %s to %s",
                            rowExistingFieldName, existingColumnData.getValue(), rowFieldNameToAdd, aRow));
                    pairToAdd = new RandomMatchingPairFromListFinder().basedOnPairsList(pairsList).withANameValuePair(existingColumnData).findMatchingPair();
                }
            }

            System.out.println(String.format("Adding pair for %s - from list %s x %s - %s", rowFieldNameToAdd, pairsList.getLeftName(),  pairsList.getRightName(), pairToAdd.toString()));
                columnValue = new NameValuePair(rowFieldNameToAdd, pairToAdd.getValueFor(rowFieldNameToAdd));
                aRow.addColumn(columnValue);
                pairToAdd.incrementUsage();
                // delete the pair if it is still in cloned
                clonedPairsToAdd.deleteCombination(pairToAdd);

            incrementCountsForOtherPairedValuesInRow(combinations, rowExistingFieldName, rowFieldNameToAdd, aRow, columnValue);
        }

        if(clonedPairsToAdd.getPairs().size()>0){
            // we have some sparse combinations to add, these will need filling later
            for(PairCombination extraPair : clonedPairsToAdd.getPairs()){
                results.addPairToExistingOrNewSparseRow(extraPair);
            }
        }

        results.debugPrintRows();

    }


    private void incrementCountsForOtherPairedValuesInRow(final AllPairsLists combinations, final String matchingField, final String valueField, final ResultsRow aRow, final NameValuePair tuple) {
        // increment the counts for other paired values in this row, not just the matchingField
        // allPairTuple.getName()
        final List<String> additionalColumns = aRow.getColumnNamesExcluding(matchingField, valueField);
        // mark all pairs between additionalMatches
        for(String additionalMatch : additionalColumns){
            NameValuePair additionalTupleToMatch = aRow.getCellFor(additionalMatch);
            // create tuple for, but don't add to row - this is to increase counts
            final IndividualPairsList listToUpdate = combinations.getPairListFor(additionalTupleToMatch.getName(), valueField);
            final PairCombination pairMentionedInRow = listToUpdate.getPair(additionalTupleToMatch.getName(),additionalTupleToMatch.getValue(), valueField, tuple.getValue());
            System.out.println(String.format("Incrementing count for %s x %s - %s, %s from %d to %d", additionalTupleToMatch.getName(), valueField, additionalTupleToMatch.getValue(), tuple.getValue(), pairMentionedInRow.getUsageCount(), pairMentionedInRow.getUsageCount()+1));
            pairMentionedInRow.incrementUsage();
        }
    }

}
