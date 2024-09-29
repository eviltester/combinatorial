package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;

public class LeastUsedPairFromListFinder extends AbstractNextPairFinderStrategy {

    /*
        using a single pairs list return the least used pair in the list
     */
    @Override
    public WeightedNameValuePairCombination findMatchingPair() {
        return pairsList.filter().getLowestValueUsagePair();
    }
}
