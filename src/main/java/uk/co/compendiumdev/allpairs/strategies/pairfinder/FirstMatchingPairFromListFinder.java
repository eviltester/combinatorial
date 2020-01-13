package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.PairCombination;

public class FirstMatchingPairFromListFinder extends AbstractNextPairFinderStrategy {

    /*
        using a single pairs list return the first matching pair in the list
     */
    @Override
    public PairCombination findMatchingPair() {
        return pairsList.filter().getFirstMatchingPair(populatedColumnData);
    }
}
