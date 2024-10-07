package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.graph.Edge;
import uk.co.compendiumdev.allpairs.domain.graph.Graph;
import uk.co.compendiumdev.allpairs.domain.graph.Node;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratorOfAllPairsUsingGraph {
    private final AllPairsLists pairCombinations;
    private final AllPairsResults results = new AllPairsResults();
    private final Graph graph;
    private final DataSets dataSets;

    public GeneratorOfAllPairsUsingGraph(AllPairsLists pairCombinations, Graph graph, DataSets data) {

        this.pairCombinations = pairCombinations;
        this.graph = graph;
        this.dataSets = data;
        // I think the all pairs generation could be viewed as a graph traversal problem
        // rather than a sparse grid population problem

        // I haven't created a graph abstraction, but we could view the pairs lists as a graph
        // each 'pair' is an edge between two nodes
        // the usage count of the pair is an edge weighting




    }

    public AllPairsResults generateResults() {


        do {
            ResultsRow path = createAPath(graph, dataSets);

            results.addRow(path);

            System.out.println(results.renderAsMarkdown());

            pairCombinations.updateUsageForPairs(path.getPairs());
            // if not all pairs used then repeat above
        }while(!pairCombinations.allUsed());

        return results;
    }

    private ResultsRow createAPath(Graph graph, DataSets dataSets) {
        ResultsRow row = new ResultsRow();

        // choose a node - this could be a startingNodeStrategy param
        // = always go from left to right
        //Node nextStartingNode = graph.getLeastUsedNode(dataSets.getDataSetNames().get(0));
        // = start anywhere
        Node nextStartingNode = graph.getLeastUsedNode();

        // pick a path
        // add that pair
        // start at the end node

        Set<Edge> path = new LinkedHashSet<>();
        Set<Node> pathNodes = new LinkedHashSet<>();
        pathNodes.add(nextStartingNode);


        // find a path from this node
        boolean isRowComplete = false;
        do{
            System.out.println(getNodesAsPath(pathNodes));

            List<Edge> leastUsedEdges = graph.getLeastUsedPathsFrom(nextStartingNode);

            // we need to pick pairs that will fit in the row - this should be "good fit in path"
            List<Edge> bestFitCandidateEdges = leastUsedEdges.stream().filter(row::isPairAGoodFitInThisRow).collect(Collectors.toList());

            if (bestFitCandidateEdges.isEmpty()) {
                new RuntimeException("Error cannot find a path");
            }

            Edge lowestPathWeightedEdge = null;
            int lowestEdgeWeighting = 0;
            // choose path which leads to most new edges being covered if added to the path
            for(Edge anEdge : bestFitCandidateEdges){

                List<PairCombination> currentPairsInRow = row.getPairs();
                ResultsRow potentialNextRow = row.copyThis();
                Node addedNode = getAddedNode(nextStartingNode, anEdge);
                potentialNextRow.addColumn(new NameValuePair(addedNode.getName(), addedNode.getValue()));

                List<PairCombination> potentialPairsInRow = potentialNextRow.getPairs();
                List<PairCombination> newPairsInRow = potentialNextRow.pairsDiffFrom(currentPairsInRow);

                int weightings = 0;
                for(PairCombination newPair : newPairsInRow){
                    Edge pairAsEdge = graph.getEdgeFor(newPair);
                    if(pairAsEdge != null){
                        if(pairAsEdge.getUsageCount() == 0){
                            weightings = weightings - 10;
                        }else{
                            weightings = weightings + (pairAsEdge.getUsageCount() * 10);
                        }
                    }
                }

                if(lowestPathWeightedEdge==null){
                    lowestPathWeightedEdge = anEdge;
                    lowestEdgeWeighting = weightings;
                }else{
                    if(weightings<lowestEdgeWeighting){
                        lowestPathWeightedEdge = anEdge;
                        lowestEdgeWeighting = weightings;
                    }
                }
            }


                // for the candidates, choose the best to use
                // TODO: this should be a strategy, factor in follow on paths
                // if can't decide then keep decision points and follow them all as graph candidate snapshots with this edge
                // and see which graph is best for that decision generation, then use that going forwards
                //Edge followEdge = bestFitCandidateEdges.get(0);
                Edge followEdge = lowestPathWeightedEdge;

                Node lastAddedNode = getAddedNode(nextStartingNode, followEdge);

                List<PairCombination> existingPairsInRow = row.getPairs();

                // TODO: create a path concept and use those instead of rows
                // add the nodes to the row
                row.addPair(new WeightedNameValuePairCombination(
                                new WeightedNameValuePair(followEdge.getLeftName(), followEdge.getLeftValue()),
                                new WeightedNameValuePair(followEdge.getRightName(), followEdge.getRightValue())
                        )
                );

                // update the pairs now in the row
                graph.updateUsageForEdges(row.pairsDiffFrom(existingPairsInRow));
                graph.updateUsageForNodes(row.pairsDiffFrom(existingPairsInRow));

                // get the next and continue
                nextStartingNode = lastAddedNode;
                pathNodes.add(lastAddedNode);
                path.add(followEdge);

                // until row is filled - row contains a column from each data set
                isRowComplete=true;
                for(String dataSetName : dataSets.getDataSetNames()) {
                    if(!row.containsColumnWithValue(dataSetName)){
                        isRowComplete=false;
                    }
                }


                // find a least used pair
                // this does not take into account direction
                // by getting all pairs
                // none in this list of pairslist so skip it
                // we need to pick pairs that will fit in the row

                // none in this list of pairslist so skip it


                // TODO: need to be able to pass in a weighting algorithm for calculating edge weightings
                // for each of these candidate edges, if added, what additional edges would also be added?
//                Map<String, Integer> comboPathWeightingHashMap = new HashMap<>();
//                for(WeightedNameValuePairCombination candidateEdge : candidateEdges){
//                    List<PairCombination> existingPairsInRow = row.getPairs();
//                    ResultsRow newRow = row.cloneThis();
//                    newRow.addPair(candidateEdge);
//                    List<PairCombination> newPairsCovered = newRow.pairsDiffFrom(existingPairsInRow);
//
//
//                    System.out.println("++++ calculating path weighting for " + candidateEdge);
//                    Integer newPathWeighting = (candidateEdge.getUsageCount() * 20);
//                    System.out.println("initial weighting " + newPathWeighting);
//                    for(PairCombination newPairCovered : newPairsCovered){
//                        System.out.println("adjusting path weighting for " + newPairCovered);
//
//                        WeightedNameValuePairCombination path = pairCombinations.getWeightedPairCombinationFor(newPairCovered);
//                        if(path==null){
//                            System.out.println("ERROR: tried to find combo weighting for invalid combo " + newPairCovered);
//                        }else {
//
//                            int pathUsageCount = path.getUsageCount();
//
//                            if(pathUsageCount==0){
//                                newPathWeighting = newPathWeighting -25; // unused paths are good
//                                System.out.println("adjust for unused path weighting " + newPathWeighting);
//                            }
//                            newPathWeighting = newPathWeighting + (pathUsageCount * 20);
//                            System.out.println("adjust for usage weighting " + newPathWeighting);
//                        }
//                    }
//                    System.out.println(candidateEdge + " would create " + newPairsCovered.size() + " with weighting " + newPathWeighting);
//                    comboPathWeightingHashMap.put(candidateEdge.pairComboKey(), newPathWeighting);
//                    //new path weighting would be the usage count total for each of these paths
//                }

//                // TODO: this could be a bestFitEdgeStrategy
//                // but just pick the first one from sorted list
//                // edge usage weighting is not good enough, should also include node usage
//                final WeightedNameValuePair fromNode = nextStartingNode;
//                // to: needs to include a new row pairs usage as well
//                Comparator<WeightedNameValuePairCombination> compareByUsage = Comparator.comparing((WeightedNameValuePairCombination p) -> (p.getLeft().getWeighting() + p.getRight().getWeighting() - fromNode.getWeighting() + comboPathWeightingHashMap.get(p.pairComboKey())));
//
//                // TODO: sometimes there are candidates that are equally valid, and choosing the wrong one results in a different graph
//                // could keep track of these and re-run the graph and use the different options to see what the outcome would be
//
//                Collections.sort(bestFitCandidateEdges, compareByUsage);
//
//                WeightedNameValuePairCombination leastUsedBestFitPair = bestFitCandidateEdges.get(0);

        }while(!isRowComplete);



        System.out.println(getNodesAsPath(pathNodes));

        return row;
    }

    private Node getAddedNode(Node existingNode, Edge followEdge) {
        Node addedNode = followEdge.getLeft();
        if(existingNode.matches(followEdge.getLeftName(), followEdge.getLeftValue())){
            addedNode=followEdge.getRight();
        }
        return addedNode;
    }

    String getEdgesAsPath(Set<Edge> edges){
        Set<Node> nodes = new LinkedHashSet<>();
        for(Edge edge : edges){
            nodes.add(edge.getLeft());
            nodes.add(edge.getRight());
        }
        return getNodesAsPath(nodes);
    }

    String getNodesAsPath(Set<Node> nodes){
        String path = "";
        String pathPrefix = "";
        for(Node node : nodes){
            path = path + pathPrefix;
            path = path + node.getName() + ":" + node.getValue();
            pathPrefix = " -> ";
        }
        path = path + ";";
        return path;
    }
}
