package uk.co.compendiumdev.allpairs.strategies.combinations;

import uk.co.compendiumdev.allpairs.domain.*;
import uk.co.compendiumdev.allpairs.domain.graph.Graph;
import uk.co.compendiumdev.allpairs.domain.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class AllPairsCombinator {
    private final DataSets dataDefinitions;
    private AllPairsLists pairCombinations;
    private Graph graph;


    public AllPairsCombinator(final DataSets data) {
        this.dataDefinitions = data;
    }

    public AllPairsLists generateAllPairCombinations() {
        List<String> dataSetNames = dataDefinitions.getDataSetNames();
        pairCombinations = new AllPairsLists();
        graph = new Graph();

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

    private List<WeightedNameValuePairCombination> addCombinationsToList(final IndividualPairsList list, final List<String> leftValues, final List<String> rightValues) {
        List<WeightedNameValuePairCombination> pairs = new ArrayList();

        for(String left : leftValues){
            for (String right : rightValues){
                WeightedNameValuePair lwnvp = pairCombinations.getOrCreateWeightedNameValuePair(list.getLeftName(), left);
                WeightedNameValuePair rwnvp = pairCombinations.getOrCreateWeightedNameValuePair(list.getRightName(), right);
                pairs.add(new WeightedNameValuePairCombination(lwnvp, rwnvp));

                Node lnode = graph.createOrGetNode(list.getLeftName(), left);
                Node rnode = graph.createOrGetNode(list.getRightName(), right);
                graph.createOrGetEdge(lnode, rnode);
            }
        }

        list.addCombinations(pairs);
        return pairs;
    }

    public Graph getGraph(){
        return graph;
    }
}
