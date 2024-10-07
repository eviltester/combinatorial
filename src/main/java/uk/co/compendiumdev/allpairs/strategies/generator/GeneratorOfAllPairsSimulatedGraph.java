package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratorOfAllPairsSimulatedGraph {
    private final AllPairsLists pairCombinations;
    private final AllPairsResults results = new AllPairsResults();

    public GeneratorOfAllPairsSimulatedGraph(AllPairsLists pairCombinations) {

        this.pairCombinations = pairCombinations;

        // I think the all pairs generation could be viewed as a graph traversal problem
        // rather than a sparse grid population problem

        // I haven't created a graph abstraction, but we could view the pairs lists as a graph
        // each 'pair' is an edge between two nodes
        // the usage count of the pair is an edge weighting




    }

    public AllPairsResults generateResults() {


        do {
            ResultsRow path = createAPath(pairCombinations);

            results.addRow(path);

            System.out.println(results.renderAsMarkdown());

            // if not all pairs used then repeat above
        }while(!pairCombinations.allUsed());

        return results;
    }

    private ResultsRow createAPath(AllPairsLists pairCombinations) {
        ResultsRow row = new ResultsRow();

        // choose a node
        // pick a path
        // add that pair
        // start at the end node

        Set<WeightedNameValuePair> nodes = new LinkedHashSet<>();


        WeightedNameValuePairCombination firstLeastUsedPair=null;
        for(IndividualPairsList pairsList : pairCombinations.getPairsLists()){
            // find a least used edge
            // this does not take into account direction or node
            WeightedNameValuePairCombination aPair = pairsList.filter().getLowestValueUsagePair();
            if(firstLeastUsedPair == null || aPair.getUsageCount() < firstLeastUsedPair.getUsageCount()){
                firstLeastUsedPair = aPair;
            }
        }



        // follow the least used path
        WeightedNameValuePair nextStartingNode = firstLeastUsedPair.getRight();
        if(nextStartingNode.getWeighting() > firstLeastUsedPair.getLeft().getWeighting()){
            nextStartingNode = firstLeastUsedPair.getLeft();
            nodes.add(firstLeastUsedPair.getRight());
            nodes.add(firstLeastUsedPair.getLeft());
        }else{
            nodes.add(firstLeastUsedPair.getLeft());
            nodes.add(firstLeastUsedPair.getRight());
        }

        row.addPair(firstLeastUsedPair);
        firstLeastUsedPair.incrementUsage();
        firstLeastUsedPair.getLeft().incrementWeighting();
        firstLeastUsedPair.getRight().incrementWeighting();



        // find a path from this node
        boolean isRowComplete = false;
        do{
            System.out.println(getNodesAsPath(nodes));

            for(IndividualPairsList pairsList : pairCombinations.getPairsLists()){


                // find a least used pair
                // this does not take into account direction
                // by getting all pairs
                List<WeightedNameValuePairCombination> candidateEdges = pairsList.filter().getAllMatchingPairs(nextStartingNode);

                // none in this list of pairslist so skip it
                if(candidateEdges.isEmpty()){continue;}

                // we need to pick pairs that will fit in the row
                List<WeightedNameValuePairCombination> bestFitCandidateEdges = candidateEdges.stream().filter(row::isPairAGoodFitInThisRow).collect(Collectors.toList());

                // none in this list of pairslist so skip it
                if(bestFitCandidateEdges.isEmpty()){continue;}

                // TODO: need to be able to pass in a weighting algorithm for calculating edge weightings
                // for each of these candidate edges, if added, what additional edges would also be added?
                Map<String, Integer> comboPathWeightingHashMap = new HashMap<>();
                for(WeightedNameValuePairCombination candidateEdge : candidateEdges){
                    List<PairCombination> existingPairsInRow = row.getPairs();
                    ResultsRow newRow = row.cloneThis();
                    newRow.addPair(candidateEdge);
                    List<PairCombination> newPairsCovered = newRow.pairsDiffFrom(existingPairsInRow);


                    System.out.println("++++ calculating path weighting for " + candidateEdge);
                    Integer newPathWeighting = (candidateEdge.getUsageCount() * 20);
                    System.out.println("initial weighting " + newPathWeighting);
                    for(PairCombination newPairCovered : newPairsCovered){
                        System.out.println("adjusting path weighting for " + newPairCovered);

                        WeightedNameValuePairCombination path = pairCombinations.getWeightedPairCombinationFor(newPairCovered);
                        if(path==null){
                            System.out.println("ERROR: tried to find combo weighting for invalid combo " + newPairCovered);
                        }else {

                            int pathUsageCount = path.getUsageCount();

                            if(pathUsageCount==0){
                                newPathWeighting = newPathWeighting -25; // unused paths are good
                                System.out.println("adjust for unused path weighting " + newPathWeighting);
                            }
                            newPathWeighting = newPathWeighting + (pathUsageCount * 20);
                            System.out.println("adjust for usage weighting " + newPathWeighting);
                        }
                    }
                    System.out.println(candidateEdge + " would create " + newPairsCovered.size() + " with weighting " + newPathWeighting);
                    comboPathWeightingHashMap.put(candidateEdge.pairComboKey(), newPathWeighting);
                    //new path weighting would be the usage count total for each of these paths
                }
                // TODO: this could be a bestFitEdgeStrategy
                // but just pick the first one from sorted list
                // edge usage weighting is not good enough, should also include node usage
                final WeightedNameValuePair fromNode = nextStartingNode;
                // to: needs to include a new row pairs usage as well
                Comparator<WeightedNameValuePairCombination> compareByUsage = Comparator.comparing((WeightedNameValuePairCombination p) -> (p.getLeft().getWeighting() + p.getRight().getWeighting() - fromNode.getWeighting() + comboPathWeightingHashMap.get(p.pairComboKey())));

                // TODO: sometimes there are candidates that are equally valid, and choosing the wrong one results in a different graph
                // could keep track of these and re-run the graph and use the different options to see what the outcome would be

                Collections.sort(bestFitCandidateEdges, compareByUsage);

                WeightedNameValuePairCombination leastUsedBestFitPair = bestFitCandidateEdges.get(0);

                // remember which column is missing
                WeightedNameValuePair lastAdded = leastUsedBestFitPair.getLeft();
                if(row.containsColumnWithValue(lastAdded.getName())){
                    lastAdded=leastUsedBestFitPair.getRight();
                }

                List<PairCombination> existingPairsInRow = row.getPairs();

                // add the pair to the row
                row.addPair(leastUsedBestFitPair);
                //leastUsedBestFitPair.incrementUsage();

                // update the pairs now in the row
                pairCombinations.updateUsageForPairs(row.pairsDiffFrom(existingPairsInRow));
                pairCombinations.updateUsageForNodes(row.pairsDiffFrom(existingPairsInRow));

                // get the next and continue
                nextStartingNode = lastAdded;
                nodes.add(lastAdded);
                // until row is filled
                isRowComplete = row.containsColumnWithValue(nextStartingNode.getName());
            }
        }while(!isRowComplete);



        System.out.println(getNodesAsPath(nodes));

        return row;
    }

    String getNodesAsPath(Set<WeightedNameValuePair> nodes){
        String path = "";
        String pathPrefix = "";
        for(WeightedNameValuePair node : nodes){
            path = path + pathPrefix;
            path = path + node.getName() + ":" + node.getValue();
            pathPrefix = " -> ";
        }
        path = path + ";";
        return path;
    }
}
