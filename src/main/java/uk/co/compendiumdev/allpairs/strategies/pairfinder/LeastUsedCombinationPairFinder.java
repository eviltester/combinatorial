package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

import java.util.ArrayList;
import java.util.List;

/*
    NOTE: this is not guaranteed to return a value
 */
public class LeastUsedCombinationPairFinder extends AbstractNextPairFinderStrategy{

    @Override
    public PairCombination findMatchingPair() {
        return findLeastUsedCombinationPair(pairsList, matchingFieldName, populatedColumnData, combinations, currentRow);
    }

    private PairCombination findLeastUsedCombinationPair(final IndividualPairsList clonedPairsToAdd, final String fieldNameToAdd, final NameValuePair matchingData, final AllPairsLists combinations, final ResultsRow aRow) {
        return findLeastUsedCombinationPair(clonedPairsToAdd, fieldNameToAdd, matchingData.getName(), matchingData.getValue(), combinations, aRow);
    }

    private PairCombination findLeastUsedCombinationPair(final IndividualPairsList clonedPairsToAdd, final String fieldNameToAdd, final String existingFieldValueName, final String existingFieldValue, final AllPairsLists combinations, final ResultsRow aRow) {
        /*
        When adding match pairs e.g. Desination, Seat
            - find least used values for match - Destination, Seat
                *    these are our preferred
            - also find most used values for - Coach and Seat (all other combinations in the table) and remove from list
                * if there is nothing in the list then pick the least used value for match - Destination, Seat
                * if there are things in the list then pick the least used item in that list
         */

        List<PairCombination> preferredPairs = clonedPairsToAdd.filter().getLowestValueUsagePairsMatching(existingFieldValueName, existingFieldValue);




        if(preferredPairs!=null && preferredPairs.size()==0) {
            // identify any pairs from my peers and see if that helps
            // if I have no preferred pairs then... do any of the things in the row have preferred pairs? if so, use those

            // NOTE: I added this code and it made no difference to the output
            //final List<String> otherListNames = aRow.getColumnNamesExcluding(fieldNameToAdd, existingFieldValueName);
            // get the first preferred pairs - select the first reported preference
            //preferredPairs = getPreferredPairsForMatchingField(fieldNameToAdd, existingFieldValueName, existingFieldValue, combinations, aRow, preferredPairs, otherListNames);

            // todo: do any of my other pairs have preferred items which are lower? currently I just pick the first

        }

        // if I have no preferred pairs and neither do my peers so get all relevant pairs for me
        if(preferredPairs!=null &&  preferredPairs.size()==0) {
            preferredPairs = combinations.getPairListFor(clonedPairsToAdd.getLeftName(), clonedPairsToAdd.getRightName()).filter().getAllMatchingPairs(existingFieldValueName, existingFieldValue);
        }


        IndividualPairsList preferredPairsList = new IndividualPairsList(clonedPairsToAdd.getLeftName(), clonedPairsToAdd.getRightName()).addCombinations(preferredPairs);



        // what other lists are matched with valueField that I care about now? i.e fields in the row
        // perhaps some of those have already been used with some of those suggested values, so they are less 'good' for us
        final List<String> otherListNames = aRow.getColumnNamesExcluding(fieldNameToAdd, existingFieldValueName);

        for(String otherListName : otherListNames){
            final IndividualPairsList aList = combinations.getPairListFor(otherListName, fieldNameToAdd);
            if(aList != null){

                // for this row value pair what are the items already used
                final NameValuePair rowValue = aRow.getCellFor(otherListName);
                final List<PairCombination> mostUsedOtherMatchingPairs = aList.filter().getMostUsedPairs(rowValue.getName(), rowValue.getValue());
                // delete these values from preferredPairs

                for(PairCombination combo : mostUsedOtherMatchingPairs){
                    // find the value and delete it
                    String valueOfPair= combo.getValueFor(fieldNameToAdd);
                    preferredPairsList.deletePairsWith(fieldNameToAdd, valueOfPair);
                }
            }
        }

        return preferredPairsList.filter().getLeastUsedPairMatching(existingFieldValueName, existingFieldValue);

    }

    private List<PairCombination> getPreferredPairsForMatchingField(final String matchingField, final String valueField, final String valueToAdd, final AllPairsLists combinations, final ResultsRow aRow, List<PairCombination> preferredPairs, final List<String> otherListNames) {
        for(String otherRowItems : otherListNames){
            final NameValuePair rowEntry = aRow.getCellFor(otherRowItems);
            final IndividualPairsList otherListToCheck = combinations.getPairListFor(otherRowItems, matchingField);
            preferredPairs = otherListToCheck.filter().getLowestValueUsagePairsMatching(rowEntry.getName(), rowEntry.getValue());
            if(preferredPairs.size()>0){
                // get real pairs matching and fake a list
                final IndividualPairsList realPairList = combinations.getPairListFor(valueField, matchingField);
                List<PairCombination> fakePairs = new ArrayList<>();
                for(PairCombination pair : preferredPairs){
                    String fakedValue = pair.getValueFor(matchingField);
                    PairCombination realPair = realPairList.getPair(valueField, valueToAdd, matchingField, fakedValue);
                    fakePairs.add(realPair);
                }
                preferredPairs = fakePairs;

                break;
            }
        }
        return preferredPairs;
    }

}
