package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;

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

    public Iterable<ResultsRow> getRows() {
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

    public void addPairToExistingOrNewSparseRow(final PairCombination extraPair) {

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
            String missingColumnValueName = aRow.isMissingOneColumnValueFromThisPair(extraPair);
            if(missingColumnValueName!=null){
                final NameValuePair columnValueToAddToSparseRow = new NameValuePair(missingColumnValueName, extraPair.getValueFor(missingColumnValueName));
                System.out.println(String.format(
                        "WARNING: unused pair found adding sparse pair for EXISTING ROW with value %s - %s", missingColumnValueName, extraPair.toString()));
                aRow.addColumn(columnValueToAddToSparseRow);
                extraPair.incrementUsage();
                return;
            }
        }

        // we could not find the pair, or a place to position it so create a new row and add the pair
        final ResultsRow rowToAddTo = new ResultsRow();
        System.out.println(String.format(
                "WARNING: unused pairs adding sparse pair to NEW ROW for value %s", extraPair.toString()));
        rowToAddTo.addPair(extraPair);
        extraPair.incrementUsage();
        rows.add(rowToAddTo);
    }

    public void addRow(final ResultsRow row) {
        rows.add(row);
        // add all column names as well
        List<String> names = row.getColumnNames();
        addColumnNames(names.toArray(new String[names.size()]));
    }
}
