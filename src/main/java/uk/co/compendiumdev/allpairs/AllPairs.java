package uk.co.compendiumdev.allpairs;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.strategies.combinations.AllPairsCombinator;
import uk.co.compendiumdev.allpairs.strategies.generator.GeneratorOfAllPairsColumnByColumn;
import uk.co.compendiumdev.allpairs.strategies.pairfinder.*;
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
        // order pairs list in different ways results in different number of rows generated

        // sorting from high to low (processing the longest lists first) often results in smaller output
        // TODO: allow passing in an order strategy or using a fixed order or partial fixed order
        pairCombinations.sortBySizesHighToLow();
        // random is hard to predict number but sometimes results in a small amount
        //pairCombinations.sortByRandom();
        //pairCombinations.sortBySizesLowToHigh();

        // created a generation strategy class to allow experimenting with different approaches
        //  e.g. sort different columns, random orders, etc.

        // todo: iterate over different strategy combinations to find the best output for the list
        // todo: have a name on all strategies to aid reporting of which strategies gave best results e..g. strategy.getName()

        //AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new FirstMatchingPairFromListFinder());
        //AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new RandomMatchingPairFromListFinder());
        // AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(new LeastUsedMatchingPairFromListFinder());
        AllPairsResults results = new GeneratorOfAllPairsColumnByColumn(pairCombinations).generateResults(
                                                    new LeastUsedCombinationPairFinder(),
                                                    new LeastUsedMatchingPairFromListFinder());

        System.out.printf("Populated %d rows%n", results.countRows());


    // My row by row strategy code hasn't been thought through properly and results in poor
        // output
//        AllPairsResults results = new GeneratorOfAllPairsRowByRow(pairCombinations).generateResults(
//                null,
//                null);

        System.out.println("Add any sparse values that have not been filled");

        new SparsePopulator(results).fillInTheBlanks(pairCombinations);

        pairCombinations.displayListReport();


        // TODO: have a result checker that checks results to make sure that all pair combinations have been used

        if(pairCombinations.allUsed()==false){
            // TODO: fix usage counting
            System.out.println("ERROR NOT ALL USED");
        }

        // TODO: report on suboptimal rows i.e. rows with least unique pairs etc as this might help optimisation


        System.out.println("check results");
        lastResults = results;
        return results;
    }

    public AllPairsResults results() {
        return lastResults;
    }
}
