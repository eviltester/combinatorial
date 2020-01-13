package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.PairsListRows;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.strategies.lists.ListFilter;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedMatchingPairFromListFinder;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedPairFromListFinder;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.NextPairFinderStrategy;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.RandomMatchingPairFromListFinder;

import java.util.ArrayList;
import java.util.List;

/*
    create the results, row by row
 */
public class GeneratorOfAllPairsRowByRow {
    private final AllPairsLists pairCombinations;
    AllPairsResults results;

    public GeneratorOfAllPairsRowByRow(final AllPairsLists pairCombinations) {
        this.pairCombinations = pairCombinations;
    }

    public AllPairsResults generateResults(NextPairFinderStrategy pairFinderStrategy) {
        return generateResults(pairFinderStrategy, new LeastUsedMatchingPairFromListFinder());
    }

    public AllPairsResults generateResults(NextPairFinderStrategy pairFinderStrategy, NextPairFinderStrategy defaultPairFinderStrategy) {
        // work through pairsLists and add to results
        results = new AllPairsResults();
        while(!pairCombinations.allUsed()){

            System.out.println(results.countRows());
            if(results.countRows()==100){
                int a=1+1;
            }
            // pairFinderStrategy applies to a pair in a list
            // we need to choose a list to apply it to
            // apply the pairFinderStrategy to each list
            // apply the list identification strategy from the list of pairs
            List<PairCombination> candidatePairs = new ArrayList<>();
            for(IndividualPairsList pairList : pairCombinations.getPairsLists()){
                final PairCombination pairFromList = new LeastUsedPairFromListFinder().basedOnPairsList(pairList).findMatchingPair();
                if(pairFromList!=null){
                    candidatePairs.add(pairFromList);
                }
            }

            // filter list for lowest used pairs
            candidatePairs = new ListFilter(new IndividualPairsList("","").addCombinations(candidatePairs)).getLowestValueUsagePairsFrom();

            // found all lowest used pairs for each list
            // how to combine them?
            // combine all in rows and find which row uses the most pairs?
            List<ResultsRow> rows = new ArrayList<>();
            ResultsRow baseRow = new ResultsRow();
            //addCombinationRowsTo(rows, baseRow, candidatePairs); - haven't thought this through recursion not good at this point

            // build a row from all candidate pairs
            for(PairCombination candidatePair : candidatePairs){

                //ResultsRow candidateRow = baseRow.cloneThis();
                // if pair is already in the row then skip this
                if(baseRow.containsPairNames(candidatePair)){
                    continue;
                }
                baseRow.addPair(candidatePair);
            }
            rows.add(baseRow);

            // for these rows - which has the most columns - add that to results
            // todo: add more selection strategies here which has most unique results?
            int maxColumns=0;
            for(ResultsRow row : rows) {
                if(row.getColumnCount()>maxColumns){
                    maxColumns=row.getColumnCount();
                }
            }

            System.out.println("check rows " + maxColumns);
            if(rows.size()==0){
                System.out.println("check rows for error");
            }
            for(ResultsRow row : rows) {
                if(row.getColumnCount()==maxColumns){
                    // try and add this row
                    if(!results.doesRowExist(row)){
                        final List<PairCombination> pairsToAddToNewRow = row.getPairs();
                        // add this row
                        System.out.println(String.format("add row %s", row.toString()));
                        ResultsRow newRow = new ResultsRow();
                        for(PairCombination pairToAdd : pairsToAddToNewRow){
                            PairCombination trackablePair = pairCombinations.getPairListFor(pairToAdd.getLeftName(), pairToAdd.getRightName()).
                                    getPair(pairToAdd.getLeftName(), pairToAdd.getLeftValue(),
                                            pairToAdd.getRightName(), pairToAdd.getRightValue()
                                            );
                            newRow.addPair(trackablePair);
                            trackablePair.incrementUsage();
                        }
                        results.addRow(newRow);
                    }
                }
            }


        }
        return results;
    }

    private void addCombinationRowsTo(final List<ResultsRow> rows,
                                      final ResultsRow baseRow,
                                      final List<PairCombination> candidatePairs) {

        if(candidatePairs.size()==0){
            // done them all - unwind
            rows.add(baseRow);
            System.out.println(String.format("Recursive row add for %s%n", baseRow.toString()));
            return;
        }

        for(PairCombination candidatePair : candidatePairs){

            ResultsRow candidateRow = baseRow.cloneThis();
            // if pair is already in the row then unwind this path
            if(candidateRow.containsPairNames(candidatePair)){
                return;
            }
            addPairOrPartOfPairToRow(candidatePair, candidateRow);
            // now try and add all other pairs into that i.e. candidatePairs - candidatePair
            List<PairCombination> nextCandidatePairs = getNewCandidatePairListWithout(candidatePairs, candidatePair);
            addCombinationRowsTo(rows, candidateRow, nextCandidatePairs);
        }
    }

    private List<PairCombination> getNewCandidatePairListWithout(final List<PairCombination> candidatePairs,
                                                                 final PairCombination removePair) {
        List<PairCombination> newList = new ArrayList<>();
        for(PairCombination pairToAdd : candidatePairs){
            if(!pairToAdd.matches(removePair)){
                newList.add(pairToAdd);
            }
        }
        return newList;
    }

    private ResultsRow addPairOrPartOfPairToRow(final PairCombination candidatePair, final ResultsRow candidateRow) {
        // does row contain the pair?
        // if not then add it
        NameValuePair leftEntry = candidateRow.getCellFor(candidatePair.getLeftName());
        NameValuePair rightEntry = candidateRow.getCellFor(candidatePair.getRightName());

        System.out.println(String.format("Recursively processing %s in %s%n", candidatePair.toString(), candidateRow.toString()));
        if(leftEntry==null && rightEntry==null){
            // we can add the pair, neither field exists
            candidateRow.addPair(candidatePair);
        }else{
//            // can I match one of the values?
//            final String missingName = candidateRow.isMissingOneColumnValueFromThisPair(candidatePair);
//            if(missingName!=null){
//                // does the value of existing column match?
//                final String matchingField = candidatePair.getOtherFieldName(missingName);
//                final NameValuePair matchingFieldValue = candidateRow.getCellFor(matchingField);
//                if(candidatePair.getValueFor(matchingField).equals(matchingFieldValue.getValue())){
//                    // i can add this missing value
//                    candidateRow.addColumn(new NameValuePair(missingName, candidatePair.getValueFor(missingName)));
//                }
//            }
        }
        return candidateRow;
    }



}
