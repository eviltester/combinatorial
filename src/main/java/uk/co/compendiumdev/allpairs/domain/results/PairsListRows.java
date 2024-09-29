package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValueCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PairsListRows {

    List<ResultsRow> rows = new ArrayList<>();
    Set<String> columnNames = new HashSet<>();

    public int getRowCount() {
        return rows.size();
    }

    public List<ResultsRow> getRows() {
        return rows;
    }

    public ResultsRow findFirstRowWithoutColumnValue(final NameValuePair columnValue) {
        for(ResultsRow row : rows){
            if(row.getCellFor(columnValue.getName())==null){
                return row;
            }
        }
        return null; // no such row exists, all rows have this column populated
    }

    public List<String> getColumnNames() {
        return new ArrayList(columnNames);
    }


    public void addColumnNames(final String[] columnNamesToAdd) {
        for(String name : columnNamesToAdd){
            columnNames.add(name);
        }
    }

    public void debugPrintRows() {
        System.out.println("\n");
        for(ResultsRow row :rows){
            System.out.println(row.toString());
        }
        System.out.println("\n");
    }

    public void addPairToExistingOrNewSparseRow(final WeightedNameValuePairCombination extraPair, final AllPairsLists combinations) {

        // add this pair into any existing sparse row
        // for each row is there a row without one of the columnvalues?
        for(ResultsRow aRow : rows){
            // does the pair fit here? if so add it and exit
            if(aRow.containsPairNames(extraPair)){
                // if the row contains the same values then we should not be processing at all so break the loop
                if(aRow.containsPair(extraPair)){
                    return;
                }
                // both columns filled in this row, skip row
                continue;
            }
            if(!aRow.isPairAGoodFitInThisRow(extraPair)){
                // this pair would mismatch in the row, find another row
                continue;
            }
            String missingColumnValueName = aRow.isMissingOneColumnValueFromThisPair(extraPair);
            if(missingColumnValueName!=null){

                System.out.println(String.format(
                        "WARNING: unused pair found adding sparse pair for EXISTING ROW with value %s - %s", missingColumnValueName, extraPair.toString()));

                // get current list of pairs in row
                List<PairCombination> existingPairsInRow = aRow.getPairs();
                aRow.addPair(extraPair);
                combinations.updateUsageForPairs(aRow.pairsDiffFrom(existingPairsInRow));

                return;
            }
        }

        // we could not find the pair, or a place to position it so create a new row and add the pair
        final ResultsRow rowToAddTo = new ResultsRow();
        System.out.println(String.format(
                "WARNING: unused pairs adding sparse pair to NEW ROW for value %s", extraPair.toString()));
        List<PairCombination> existingPairsInRow = rowToAddTo.getPairs();
        rowToAddTo.addPair(extraPair);
        combinations.updateUsageForPairs(rowToAddTo.pairsDiffFrom(existingPairsInRow));
        rows.add(rowToAddTo);
    }

    public void addRow(final ResultsRow row) {
        rows.add(row);
        // add all column names as well
        List<String> names = row.getColumnNames();
        addColumnNames(names.toArray(new String[names.size()]));
    }
}
