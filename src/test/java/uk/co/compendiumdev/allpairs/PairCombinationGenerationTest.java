package uk.co.compendiumdev.allpairs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.strategies.combinations.AllPairsCombinator;

public class PairCombinationGenerationTest {

    @Test
    public void givenNoDataWeReceiveNoPairs(){

        DataSets data = new DataSets();

        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                generateAllPairCombinations();

        Assertions.assertEquals(0, pairCombinations.countOf() );
    }


    @Test
    public void givenOneItemWeReceiveNoPairs(){

        DataSets data = new DataSets();
        data.addDataSet("numbers", "one", "two", "three");

        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                generateAllPairCombinations();

        Assertions.assertEquals(0, pairCombinations.countOf() );

    }

    @Test
    public void givenTwoItemSetsWeReceiveOnePair(){

        DataSets data = new DataSets();
        data.addDataSet("numbers", "one", "two", "three");
        data.addDataSet("letters", "a", "b", "c");

        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                generateAllPairCombinations();

        Assertions.assertEquals(1, pairCombinations.countOf() );

    }

    @Test
    public void givenTwoItemSetsWeReceiveCorrectPairList(){

        DataSets data = new DataSets();
        data.addDataSet("numbers", "one", "two", "three");
        data.addDataSet("letters", "a", "b", "c");

        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                generateAllPairCombinations();

        IndividualPairsList numsAndLetters = pairCombinations.getPairsLists().get(0);

        Assertions.assertEquals("numbers",numsAndLetters.getLeftName());
        Assertions.assertEquals("letters",numsAndLetters.getRightName());
        Assertions.assertEquals(9, numsAndLetters.getPairCount());

        PairCombination[] expectedPairs = {
                new PairCombination("numbers",  "one", "letters","a"),
                new PairCombination("numbers", "one", "letters","b"),
                new PairCombination("numbers",  "one", "letters","c"),
                new PairCombination("numbers", "two", "letters","a"),
                new PairCombination("numbers", "two", "letters","b"),
                new PairCombination("numbers",  "two", "letters","c"),
                new PairCombination("numbers", "three", "letters","a"),
                new PairCombination("numbers", "three", "letters","b"),
                new PairCombination("numbers",  "three", "letters","c")
        };

        for (PairCombination pair : expectedPairs){
            Assertions.assertTrue(numsAndLetters.containsPair(pair));
        }

    }

    @Test
    public void givenThreeItemSetsWeReceiveThreeCorrectPairLists(){

        DataSets data = new DataSets();
        data.addDataSet("numbers", "one");
        data.addDataSet("letters", "a", "b");
        data.addDataSet("age", "18", "19", "20");


        AllPairsLists pairCombinations = new AllPairsCombinator(data).
                generateAllPairCombinations();

        IndividualPairsList numsAndLetters = pairCombinations.getPairsLists().get(0);

        Assertions.assertEquals("numbers",numsAndLetters.getLeftName());
        Assertions.assertEquals("letters",numsAndLetters.getRightName());
        Assertions.assertEquals(2, numsAndLetters.getPairCount());

        IndividualPairsList numsAndAge = pairCombinations.getPairsLists().get(1);

        Assertions.assertEquals("numbers",numsAndAge.getLeftName());
        Assertions.assertEquals("age",numsAndAge.getRightName());
        Assertions.assertEquals(3, numsAndAge.getPairCount());

        IndividualPairsList lettersAndAge = pairCombinations.getPairsLists().get(2);

        Assertions.assertEquals("letters",lettersAndAge.getLeftName());
        Assertions.assertEquals("age",lettersAndAge.getRightName());
        Assertions.assertEquals(6, lettersAndAge.getPairCount());

        PairCombination[] expectedNumLetterPairs = {
                new PairCombination("numbers",  "one", "letters","a"),
                new PairCombination("numbers",  "one", "letters","b"),
        };

        PairCombination[] expectedNumAgePairs = {
                new PairCombination("numbers",  "one", "age","18"),
                new PairCombination("numbers",  "one", "age","19"),
                new PairCombination("numbers",  "one", "age","20"),
        };

        PairCombination[] expectedLettersAgePairs = {
                new PairCombination("letters",  "a", "age","18"),
                new PairCombination("letters",  "a", "age","19"),
                new PairCombination("letters",  "a", "age","20"),
                new PairCombination("letters",  "b", "age","18"),
                new PairCombination("letters",  "b", "age","19"),
                new PairCombination("letters",  "b", "age","20"),
        };

        for (PairCombination pair : expectedNumLetterPairs){
            Assertions.assertTrue(numsAndLetters.containsPair(pair));
        }

        for (PairCombination pair : expectedNumAgePairs){
            Assertions.assertTrue(numsAndAge.containsPair(pair));
        }

        for (PairCombination pair : expectedLettersAgePairs){
            Assertions.assertTrue(lettersAndAge.containsPair(pair));
        }

    }

}
