package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;

public class RandomMatchingPairFromListFinder extends AbstractNextPairFinderStrategy {

    /*
        using a single pairs list return a random pair
     */
    @Override
    public WeightedNameValuePairCombination findMatchingPair() {
        return pairsList.filter().getARandomPair(populatedColumnData);
    }
}
