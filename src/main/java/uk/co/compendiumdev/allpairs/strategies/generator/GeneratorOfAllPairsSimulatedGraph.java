package uk.co.compendiumdev.allpairs.strategies.generator;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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


        PairCombination firstLeastUsedPair=null;
        for(IndividualPairsList pairsList : pairCombinations.getPairsLists()){
            // find a least used edge
            // this does not take into account direction or node
            PairCombination aPair = pairsList.filter().getLowestValueUsagePair();
            if(firstLeastUsedPair == null || aPair.getUsageCount() < firstLeastUsedPair.getUsageCount()){
                firstLeastUsedPair = aPair;
            }
        }

        // follow the least used path
        WeightedNameValuePair nextStartingNode = firstLeastUsedPair.getRight();
        if(nextStartingNode.getWeighting() > firstLeastUsedPair.getLeft().getWeighting()){
            nextStartingNode = firstLeastUsedPair.getLeft();
        }

        row.addPair(firstLeastUsedPair);
        firstLeastUsedPair.incrementUsage();
        firstLeastUsedPair.getLeft().incrementWeighting();
        firstLeastUsedPair.getRight().incrementWeighting();



        // find a path from this node
        boolean isRowComplete = false;
        do{
            for(IndividualPairsList pairsList : pairCombinations.getPairsLists()){
                // find a least used pair
                // this does not take into account direction
                // by getting all pairs
                List<PairCombination> candidateEdges = pairsList.filter().getAllMatchingPairs(nextStartingNode);

                // none in this list of pairslist so skip it
                if(candidateEdges.isEmpty()){continue;}

                // we need to pick pairs that will fit in the row
                List<PairCombination> bestFitCandidateEdges = candidateEdges.stream().filter(row::isPairAGoodFitInThisRow).collect(Collectors.toList());

                // none in this list of pairslist so skip it
                if(bestFitCandidateEdges.isEmpty()){continue;}

                // TODO: this could be a bestFitEdgeStrategy
                // but just pick the first one from sorted list
                // edge usage weighting is not good enough, should also include node usage
                final WeightedNameValuePair fromNode = nextStartingNode;
                Comparator<PairCombination> compareByUsage = Comparator.comparing((PairCombination p) -> new Integer(p.getWeighting() - fromNode.getWeighting()));

                Collections.sort(bestFitCandidateEdges, compareByUsage);

                PairCombination leastUsedBestFitPair = bestFitCandidateEdges.get(0);

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

                // until row is filled
                isRowComplete = row.containsColumnWithValue(nextStartingNode.getName());
            }
        }while(!isRowComplete);

        return row;
    }
}
