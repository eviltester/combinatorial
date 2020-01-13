package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.PairCombination;

public class LeastUsedPairFromListFinder extends AbstractNextPairFinderStrategy {

    /*
        using a single pairs list return the least used pair in the list
     */
    @Override
    public PairCombination findMatchingPair() {
        return pairsList.filter().getLowestValueUsagePair();
    }
}
