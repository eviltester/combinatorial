package uk.co.compendiumdev.allpairs.strategies.combinations;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.PairCombination;

import java.util.ArrayList;
import java.util.List;

public class AllPairsCombinator {
    private final DataSets dataDefinitions;

    public AllPairsCombinator(final DataSets data) {
        this.dataDefinitions = data;
    }

    public AllPairsLists generateAllPairCombinations() {
        List<String> dataSetNames = dataDefinitions.getDataSetNames();
        AllPairsLists pairCombinations = new AllPairsLists();

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
                pairs.add(new PairCombination(list.getLeftName(), left, list.getRightName(), right));
            }
        }

        list.addCombinations(pairs);
        return pairs;
    }
}
