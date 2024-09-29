package uk.co.compendiumdev.allpairs.strategies.sparse;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValueCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.List;
import java.util.Random;

public class SparsePopulator {
    private final AllPairsResults results;

    public SparsePopulator(final AllPairsResults results) {
        this.results = results;
    }


    /*
        For the given results, loop through all rows, and any blank columns
        fill in, using the combinations as a basis
     */
    public void fillInTheBlanks(final AllPairsLists combinations) {

        int amendedRows = 0;

        for(ResultsRow row : results.getRows()){
            List<String> columnNamesToFill = row.getBlankColumnNames(results.getColumnNames());
            if(!columnNamesToFill.isEmpty()){
                amendedRows++;
            }
            for(String columnName : columnNamesToFill){
                // so many ways we could do this - pick a random value from data set, pick least used pairs
                // start by picking a random pair that contains this column
                final IndividualPairsList pairListToChooseFrom = selectRandomPairListContainingColumn(columnName, combinations);
                // do we want a pair from this? or match an existing value from this pair combo?
                String missingCol = row.isMissingOneColumnValueFromThisPair(pairListToChooseFrom.getPairs().get(0));
                List<PairCombination> existingPairs = row.getPairs();
                if(missingCol==null){
                    // actually no, two are missing - add a whole pair
                    final WeightedNameValuePairCombination pair = pairListToChooseFrom.filter().getLowestValueUsagePair();
                    row.addPair(pair);
                    //pair.incrementUsage();
                }else{
                    String columnToMatch=pairListToChooseFrom.getRightName();
                    if(pairListToChooseFrom.getRightName().equals(missingCol)){
                        columnToMatch = pairListToChooseFrom.getLeftName();
                    }

                    final WeightedNameValuePairCombination pairToChooseValueFrom = pairListToChooseFrom.filter().getLeastUsedPairMatching(columnToMatch, row.getCellFor(columnToMatch).getValue());
                    final String columnValueToAdd = pairToChooseValueFrom.getValueFor(missingCol);
                    row.addColumn(new NameValuePair(missingCol, columnValueToAdd));
                    //pairToChooseValueFrom.incrementUsage();
                }
                combinations.updateUsageForPairs(row.pairsDiffFrom(existingPairs));
            }
        }

        System.out.println(amendedRows + " had to be completed in the sparse grid");
    }

    private IndividualPairsList selectRandomPairListContainingColumn(final String columnName, final AllPairsLists combinations) {

        final List<IndividualPairsList> combos = combinations.getPairsListsContaining(columnName);

        if(combos.size()==0){
            return null; // could not find any - which is odd, are you sure this isn't a test?
        }
        // select a random list from that selection
        int selectedIndex = new Random().nextInt(combos.size());
        return combos.get(selectedIndex);
    }
}
