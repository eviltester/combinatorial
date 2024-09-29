package uk.co.compendiumdev.allpairs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
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

        WeightedNameValuePairCombination[] expectedPairs = {
                createPairCombo(pairCombinations, "numbers",  "one", "letters","a"),
                createPairCombo(pairCombinations, "numbers", "one", "letters","b"),
                createPairCombo(pairCombinations, "numbers",  "one", "letters","c"),
                createPairCombo(pairCombinations, "numbers", "two", "letters","a"),
                createPairCombo(pairCombinations, "numbers", "two", "letters","b"),
                createPairCombo(pairCombinations, "numbers",  "two", "letters","c"),
                createPairCombo(pairCombinations, "numbers", "three", "letters","a"),
                createPairCombo(pairCombinations, "numbers", "three", "letters","b"),
                createPairCombo(pairCombinations, "numbers",  "three", "letters","c")
        };

        for (WeightedNameValuePairCombination pair : expectedPairs){
            Assertions.assertTrue(numsAndLetters.containsPair(pair));
        }

    }

    private WeightedNameValuePairCombination createPairCombo(AllPairsLists pairCombinations, String lname, String lvalue, String rname, String rvalue) {
        return new WeightedNameValuePairCombination(
                pairCombinations.getWeightedNameValuePair(lname, lvalue),
                pairCombinations.getWeightedNameValuePair(rname, rvalue)
        );
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

        WeightedNameValuePairCombination[] expectedNumLetterPairs = {
                createPairCombo(pairCombinations, "numbers",  "one", "letters","a"),
                createPairCombo(pairCombinations, "numbers",  "one", "letters","b"),
        };

        WeightedNameValuePairCombination[] expectedNumAgePairs = {
                createPairCombo(pairCombinations, "numbers",  "one", "age","18"),
                createPairCombo(pairCombinations, "numbers",  "one", "age","19"),
                createPairCombo(pairCombinations, "numbers",  "one", "age","20"),
        };

        WeightedNameValuePairCombination[] expectedLettersAgePairs = {
                createPairCombo(pairCombinations, "letters",  "a", "age","18"),
                createPairCombo(pairCombinations, "letters",  "a", "age","19"),
                createPairCombo(pairCombinations, "letters",  "a", "age","20"),
                createPairCombo(pairCombinations, "letters",  "b", "age","18"),
                createPairCombo(pairCombinations, "letters",  "b", "age","19"),
                createPairCombo(pairCombinations, "letters",  "b", "age","20"),
        };

        for (WeightedNameValuePairCombination pair : expectedNumLetterPairs){
            Assertions.assertTrue(numsAndLetters.containsPair(pair));
        }

        for (WeightedNameValuePairCombination pair : expectedNumAgePairs){
            Assertions.assertTrue(numsAndAge.containsPair(pair));
        }

        for (WeightedNameValuePairCombination pair : expectedLettersAgePairs){
            Assertions.assertTrue(lettersAndAge.containsPair(pair));
        }

    }

}
