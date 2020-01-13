package uk.co.compendiumdev.allpairs;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.strategies.combinations.AllPairsCombinator;
import uk.co.compendiumdev.allpairs.strategies.generator.GeneratorOfAllPairsColumnByColumn;
import uk.co.compendiumdev.allpairs.strategies.generator.GeneratorOfAllPairsRowByRow;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedCombinationPairFinder;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.LeastUsedMatchingPairFromListFinder;
import uk.co.compendiumdev.allpairs.strategies.sparse.SparsePopulator;

import java.util.*;

public class AllPairs {

    DataSets data = new DataSets();

    private AllPairsResults lastResults;

    public AllPairs() {
    }

    public AllPairs(final DataSets dataSets) {
        this.data = dataSets;
    }

    public void addDataSet(final String dataSetName, final String ...values) {
        List<String> myValues = Arrays.asList(values);
        data.addDataSet(dataSetName, myValues);

    }

    public int countDataSets() {
        return data.size();
    }

    public List<String> getDataSetValues(final String dataSetName) {
        return data.getDataSetValues(dataSetName);
    }

    public AllPairsResults generate() {

        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                                                generateAllPairCombinations();


        // now combined into set of pair tuples
        // sort pairs list by number of combinations
        // sorting from high to low (processing the longest lists first) results in smaller output
        pairCombinations.sortBySizesHighToLow();
        //pairCombinations.sortBySizesLowToLow();

        // created a generation strategy class to allow experimenting with different approaches
        //  e.g. sort different columns, random orders, etc.

        // todo: iterate over different strategy combinations to find the best output for the list
        // todo: have a name on all strategies to aid reporting of which strategies gave best results e..g. strategy.getName()

        //AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new FirstMatchingPairFromListFinder());
        //AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new RandomPairFromListFinder());
        //AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new LeastUsedPairFromListFinder());
        AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(
                                                    new LeastUsedCombinationPairFinder(),
                                                    new LeastUsedMatchingPairFromListFinder());

    // My row by row strategy code hasn't been thought through properly and results in poor
        // output
//        AllPairsResults results = new GeneratorOfAllPairsRowByRow(pairCombinations).generateResults(
//                null,
//                null);

        System.out.println("Add any sparse values that have not been filled");

        new SparsePopulator(results).fillInTheBlanks(pairCombinations);

        pairCombinations.displayListReport();


        // TODO: have a result checker that checks results to make sure that all pair combinations have been used
        // TODO: report on suboptimal rows i.e. rows with least unique pairs etc as this might help optimisation

        System.out.println("check results");
        lastResults = results;
        return results;
    }

    public AllPairsResults results() {
        return lastResults;
    }
}
