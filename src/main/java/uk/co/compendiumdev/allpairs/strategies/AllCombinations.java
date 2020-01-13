package uk.co.compendiumdev.allpairs.strategies;

import uk.co.compendiumdev.allpairs.domain.DataSets;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

import java.util.List;

// note: for reference, and possibly use later to optimise code
//       these were not consulted during writing the code
// n-ary Cartesian Product
// https://en.wikipedia.org/wiki/Cartesian_product
// https://rosettacode.org/wiki/Cartesian_product_of_two_or_more_lists
// see also https://github.com/dpaukov/combinatoricslib3

public class AllCombinations {
    private final DataSets data;

    public AllCombinations(final DataSets dataSets) {
        this.data = dataSets;
    }

    public int countCombinations() {

        int count=0;

        for(String setName : data.getDataSetNames()){
            if(count==0){
                count = data.getDataSetValues(setName).size();
            }else{
                count *= data.getDataSetValues(setName).size();
            }
        }

        return count;
    }

    public AllPairsResults expand() {

        System.out.println(String.format("Generating %d combinations", countCombinations()));

        List<String>dataSetNames = data.getDataSetNames();

        // automatically set to 0
        int indexForSet[] = new int[dataSetNames.size()];
        int numberOfSets = indexForSet.length;


        final AllPairsResults results = new AllPairsResults();

        // I wanted a non-recursive algorithm
        boolean finished = false;
        while(!finished) {
            // create a row
            final ResultsRow row = new ResultsRow();

            // populate row using indexForSet values
            for(int index = 0; index<numberOfSets; index++ ){
                String dataSetName = dataSetNames.get(index);
                row.addColumn(
                        new NameValuePair(
                                dataSetName,
                                data.getDataSetValues(dataSetName).
                                        get(indexForSet[index])));
            }

            //System.out.println(row.toString());

            results.addRow(row);

            // increment the counters from right to left, rolling over as we go
            for(int incrementThis=numberOfSets-1; incrementThis>=0; incrementThis--){

                boolean finishedIncrementing = false;
                // always increment the right most item
                // only increment other items if the item to the right is 0
                if(incrementThis!=numberOfSets-1){
                    if(indexForSet[incrementThis+1]!=0){
                        finishedIncrementing=true;
                    }
                }

                if(finishedIncrementing){
                    break;
                }

                indexForSet[incrementThis] = indexForSet[incrementThis]+1;

                // not using modulus to keep this easier to understand
                // note this is an example below and has never been executed
                // just added it to remind myself it could have been a modulus operation
//                indexForSet[incrementThis] = indexForSet[incrementThis]%
//                        data.getDataSetValues(
//                                dataSetNames.get(incrementThis)).size();

                if(indexForSet[incrementThis]>= data.getDataSetValues(
                                                    dataSetNames.get(incrementThis)).size()){
                    // roll over if too high
                    indexForSet[incrementThis] = 0;
                    if(incrementThis==0){
                        // we just tried to roll over the final item so we are done
                        finished=true;
                    }
                }
            }

//            StringBuilder counter = new StringBuilder();
//            counter.append("Counter : ");
//            for(int index=0; index<indexForSet.length; index++){
//                counter.append(String.format("%d :", indexForSet[index]));
//            }
//            System.out.println(counter.toString());

        }

        return results;
    }
}
