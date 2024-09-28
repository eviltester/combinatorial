package uk.co.compendiumdev.allpairs.strategies.combinations;

import uk.co.compendiumdev.allpairs.domain.*;

import java.util.ArrayList;
import java.util.List;

public class AllPairsCombinator {
    private final DataSets dataDefinitions;
    private AllPairsLists pairCombinations;


    public AllPairsCombinator(final DataSets data) {
        this.dataDefinitions = data;
    }

    public AllPairsLists generateAllPairCombinations() {
        List<String> dataSetNames = dataDefinitions.getDataSetNames();
        pairCombinations = new AllPairsLists();

        for(String leftName : dataSetNames){
            for(String rightName : dataSetNames){
                // combine left and right values
                if(!leftName.equals(rightName) && !pairCombinations.pairExists(leftName, rightName)) {
                    final IndividualPairsList list = pairCombinations.createList(leftName, rightName);

                    addCombinationsToList(
                                        list,
                                        dataDefinitions.getDataSetValues(leftName),
                                        dataDefinitions.getDataSetValues(rightName));
                }
            }
        }

        return pairCombinations;
    }

    private List<PairCombination> addCombinationsToList(final IndividualPairsList list, final List<String> leftValues, final List<String> rightValues) {
        List<PairCombination> pairs = new ArrayList();

        for(String left : leftValues){
            for (String right : rightValues){
                WeightedNameValuePair lwnvp = pairCombinations.getOrCreateWeightedNameValuePair(list.getLeftName(), left);
                WeightedNameValuePair rwnvp = pairCombinations.getOrCreateWeightedNameValuePair(list.getRightName(), right);
                pairs.add(new PairCombination(lwnvp, rwnvp));
            }
        }

        list.addCombinations(pairs);
        return pairs;
    }
}
