package uk.co.compendiumdev.allpairs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.strategies.combinations.CartesianProductCombinations;

public class CartesianProductTest {


    @Test
    public void canCreateCartesianProductCombinations(){

        DataSets data = new DataSets();

        data.addDataSet("One", "1-1");
        data.addDataSet("Two", "2-1", "2-2");
        data.addDataSet("Three", "3-1", "3-2", "3-3");
        data.addDataSet("Four", "4-1", "4-2", "4-3", "4-4");

        final CartesianProductCombinations allcombinations = new CartesianProductCombinations(data);
        Assertions.assertEquals(1*2*3*4, allcombinations.countCombinations());

        AllPairsResults combinationResults = allcombinations.expand();
        System.out.println(combinationResults.renderAsMarkdown());
    }

    @Test
    public void canCreateCartesianProductCombinationsWithDataInAnyOrder(){

        DataSets data = new DataSets();

        data.addDataSet("Three", "3-1", "3-2", "3-3");
        data.addDataSet("Two", "2-1", "2-2");
        data.addDataSet("Four", "4-1", "4-2", "4-3", "4-4");
        data.addDataSet("One", "1-1");

        final CartesianProductCombinations allcombinations = new CartesianProductCombinations(data);
        Assertions.assertEquals(3*2*4*1, allcombinations.countCombinations());

        AllPairsResults combinationResults = allcombinations.expand();
        System.out.println(combinationResults.renderAsMarkdown());
    }


}
