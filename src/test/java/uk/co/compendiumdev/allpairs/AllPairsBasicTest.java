package uk.co.compendiumdev.allpairs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.strategies.AllCombinations;

public class AllPairsBasicTest {

    @Test
    public void canCreateCartesianProductCombinations(){

        DataSets data = new DataSets();

        data.addDataSet("One", "1-1");
        data.addDataSet("Two", "2-1", "2-2");
        data.addDataSet("Three", "3-1", "3-2", "3-3");
        data.addDataSet("Four", "4-1", "4-2", "4-3", "4-4");

        final AllCombinations allcombinations = new AllCombinations(data);
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

        final AllCombinations allcombinations = new AllCombinations(data);
        Assertions.assertEquals(3*2*4*1, allcombinations.countCombinations());

        AllPairsResults combinationResults = allcombinations.expand();
        System.out.println(combinationResults.renderAsMarkdown());
    }

    @Test
    public void allPairsCreate(){

        AllPairs allPairs = new AllPairs();

        // https://en.wikipedia.org/wiki/All-pairs_testing
        allPairs.addDataSet("Enabled", "true", "false");

        Assertions.assertEquals(1,allPairs.countDataSets());
        Assertions.assertEquals(2,
                allPairs.getDataSetValues("Enabled").size());
        Assertions.assertTrue(allPairs.getDataSetValues("Enabled").contains("false"));
    }



    @Test
    public void allPairsUsageWikipediaExample(){

        AllPairs allPairs = new AllPairs();

        // https://en.wikipedia.org/wiki/All-pairs_testing
        allPairs.addDataSet("Enabled", "true", "false");
        allPairs.addDataSet("Choice_type", "1", "2", "3");
        allPairs.addDataSet("Category", "a", "b", "c", "d");

        allPairs.generate();

        System.out.println(allPairs.results().renderAsMarkdown());

        Assertions.assertEquals(12, allPairs.results().countRows());
        /*

result from

perl allpairs.pl wikipediaexample.tsv

TEST CASES
case	Enabled	Choice_type	Category	pairings
1	true	1	a	3
2	false	2	a	3
3	false	1	b	3
4	true	2	b	3
5	true	3	c	3
6	false	1	c	2
7	false	3	d	3
8	true	1	d	2
9	~true	3	a	1
10	~false	3	b	1
11	~true	2	c	1
12	~false	2	d	1
         */
    }

    @Test
    public void allPairsUsageJBExample(){

        AllPairs allPairs = new AllPairs();

        // https://en.wikipedia.org/wiki/All-pairs_testing


        allPairs.addDataSet("times", "night", "day", "dawn");
        allPairs.addDataSet("colors", "purple", "blue", "silver");
        allPairs.addDataSet("cars", "Mazda", "Ford");

        allPairs.generate();

        System.out.println(allPairs.results().renderAsMarkdown());

        Assertions.assertEquals(9, allPairs.results().countRows() );

        /*

        result from

perl allpairs.pl jbcars.tsv

TEST CASES
case	colors	cars	times	pairings
1	purple	Mazda	night	3
2	purple	Ford	day	3
3	blue	Ford	night	3
4	blue	Mazda	day	3
5	silver	Mazda	dawn	3
6	silver	Ford	night	2
7	purple	Ford	dawn	2
8	blue	~Mazda	dawn	1
9	silver	~Mazda	day	1

         */
    }

    @Test
    public void allPairsUsageJB10x10Example(){

        AllPairs allPairs = new AllPairs();

        // in paper James says all pairs generates about 170 combos, he thinks it can go down to about 130

        allPairs.addDataSet("p0", "001", "002", "003", "004", "005", "006", "007", "008", "009", "010");
        allPairs.addDataSet("p1", "a01", "a02", "a03", "a04", "a05", "a06", "a07", "a08", "a09", "a10");
        allPairs.addDataSet("p2", "b01", "b02", "b03", "b04", "b05", "b06", "b07", "b08", "b09", "b10");
        allPairs.addDataSet("p3", "c01", "c02", "c03", "c04", "c05", "c06", "c07", "c08", "c09", "c10");
        allPairs.addDataSet("p4", "d01", "d02", "d03", "d04", "d05", "d06", "d07", "d08", "d09", "d10");
        allPairs.addDataSet("p5", "e01", "e02", "e03", "e04", "e05", "e06", "e07", "e08", "e09", "e10");
        allPairs.addDataSet("p6", "f01", "f02", "f03", "f04", "f05", "f06", "f07", "f08", "f09", "f10");
        allPairs.addDataSet("p7", "g01", "g02", "g03", "g04", "g05", "g06", "g07", "g08", "g09", "g10");
        allPairs.addDataSet("p8", "h01", "h02", "h03", "h04", "h05", "h06", "h07", "h08", "h09", "h10");
        allPairs.addDataSet("p9", "i01", "i02", "i03", "i04", "i05", "i06", "i07", "i08", "i09", "i10");

        allPairs.generate();

        System.out.println(allPairs.results().renderAsMarkdown());

        // last best run for us was 262 combinations
        Assertions.assertEquals(262, allPairs.results().countRows() );

        // perl allpairs.pl jb10combos.tsv
        // results in 147 combinations
    }


    @Test
    public void allPairsBoltonRedGreenBluePairsExample(){

        AllPairs allPairs = new AllPairs();

        // https://www.developsense.com/pairwiseTesting.html


        allPairs.addDataSet("A", "red", "green", "blue");
        allPairs.addDataSet("B", "red", "green", "blue");
        allPairs.addDataSet("C", "red", "green", "blue");

        allPairs.generate();

        System.out.println(allPairs.results().renderAsMarkdown());

        // todo: ideally we want 9 (achieved via manual optimisation), currently we generate 11 - allpairs generates 10 pict also generates 10
        Assertions.assertEquals(11, allPairs.results().countRows() );

/*

perl allpairs.pl redgreenblue.tsv

TEST CASES
case	A	B	C	pairings
1	red	red	red	3
2	red	green	green	3
3	red	blue	blue	3
4	green	red	green	3
5	green	green	red	3
6	green	blue	red	2
7	blue	red	blue	3
8	blue	green	red	2
9	blue	blue	green	3
10	green	green	blue	2
 */

/*
Pict gives 10 https://pairwise.yuuniworks.com/
A	B	C
red	red	red
red	blue	green
blue	red	blue
green	red	green
blue	green	red
green	green	blue
red	green	green
green	blue	red
red	blue	blue
blue	blue	green

 */
    }

    @Test
    public void allPairsBoltonAirPairsExample(){

        DataSets destinationClassSeat = new DataSets();

        destinationClassSeat.addDataSet("Destination", "Canada", "Mexico", "USA");
        destinationClassSeat.addDataSet("Class", "Coach", "Business Class", "First Class");
        destinationClassSeat.addDataSet("Seat Preference", "Aisle", "Window");

        AllPairs allPairs = new AllPairs(destinationClassSeat);

        // https://www.developsense.com/pairwiseTesting.html


        allPairs.generate();

        System.out.println(allPairs.results().renderAsMarkdown());

        Assertions.assertEquals(9, allPairs.results().countRows() );

/* allpairs.pl generates

perl allpairs.pl boltonair.tsv

TEST CASES
case	Destination	Class	Seat Preference	pairings
1	Canada	Coach	Aisle	3
2	Canada	Business Class	Window	3
3	Mexico	Coach	Window	3
4	Mexico	Business Class	Aisle	3
5	USA	First Class	Aisle	3
6	USA	Coach	Window	2
7	Canada	First Class	Window	2
8	Mexico	First Class	~Aisle	1
9	USA	Business Class	~Aisle	1
 */
    }
}
