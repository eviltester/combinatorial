package uk.co.compendiumdev.allpairs.renderers;

import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.graph.Edge;
import uk.co.compendiumdev.allpairs.domain.graph.Graph;
import uk.co.compendiumdev.allpairs.domain.graph.Node;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;

import java.util.List;

public class GraphvizRenderer {

    // TODO: move datasets into the results as well
    public static String asDot(AllPairsResults results, DataSets data) {

        List<IndividualPairsList> dataNames = results.getPairsLists();

        // output the pairs lists as a graph with edges weighted by usage
        StringBuilder output = new StringBuilder();

        output.append("digraph G {\n");

        for(String dataSetName : data.getDataSetNames()){
            for(String dataValueName : data.getDataSetValues(dataSetName)){

                output.append( String.format("%s_%s [label=\"%s.%s\"];%n",
                        dataSetName.replace(" ", "_"),
                        dataValueName.replace(" ", "_"),
                        dataSetName,
                        dataValueName
                ));
            }
        }

        for(IndividualPairsList pairs : dataNames){
            for(WeightedNameValuePairCombination pair : pairs.getPairs()){

                String node1 = String.format("%s_%s",
                    pairs.getLeftName().replace(" ", "_"),
                    pair.getLeftValue().replace(" ", "_")
                );

                String node2 = String.format("%s_%s",
                        pairs.getRightName().replace(" ", "_"),
                        pair.getRightValue().replace(" ", "_")
                );

                output.append(String.format("%s -> %s [label=\"%d\"];%n",
                        node1, node2, pair.getUsageCount()
                        ));

            }
        }

        output.append("}\n");

        return output.toString();

    }

    public static String asDot(Graph graph) {


        // output the pairs lists as a graph with edges weighted by usage
        StringBuilder output = new StringBuilder();

        output.append("digraph G {\n");

        for(Node aNode : graph.getNodes()){

                output.append( String.format("%s_%s [label=\"%s.%s\"];%n",
                        aNode.getName().replace(" ", "_"),
                        aNode.getValue().replace(" ", "_"),
                        aNode.getName(),
                        aNode.getValue()
                ));
        }

        for(Edge anEdge : graph.getEdges()){

            String node1 = String.format("%s_%s",
                    anEdge.getLeftName().replace(" ", "_"),
                    anEdge.getLeftValue().replace(" ", "_")
            );

            String node2 = String.format("%s_%s",
                    anEdge.getRightName().replace(" ", "_"),
                    anEdge.getRightValue().replace(" ", "_")
            );

            output.append(String.format("%s -> %s [label=\"%d\" dir=\"both\"];%n",
                    node1, node2, anEdge.getUsageCount()
            ));
        }

        output.append("}\n");

        return output.toString();

    }
}
