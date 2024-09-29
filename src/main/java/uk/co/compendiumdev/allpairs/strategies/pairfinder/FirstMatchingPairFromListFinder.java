package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;

public class FirstMatchingPairFromListFinder extends AbstractNextPairFinderStrategy {

    /*
        using a single pairs list return the first matching pair in the list
     */
    @Override
    public WeightedNameValuePairCombination findMatchingPair() {
        return pairsList.filter().getFirstMatchingPair(populatedColumnData);
    }
}
