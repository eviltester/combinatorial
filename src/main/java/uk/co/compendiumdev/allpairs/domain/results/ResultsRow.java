package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.SparseRow;

import java.util.ArrayList;
import java.util.List;

public class ResultsRow {

    private final SparseRow row;

    public ResultsRow(){
        row = new SparseRow();
    }

    public ResultsRow(SparseRow existingRow){
        row = existingRow;
    }

    public void addColumn(final NameValuePair allPairTuple) {
        row.addColumn(allPairTuple);
    }

    public NameValuePair getCellFor(final String name) {
        return row.getCellFor(name);
    }

    @Override
    public String toString(){
        return row.toString();
    }

    public void addPair(final PairCombination pair) {
        this.addColumn(new NameValuePair(pair.getLeftName(), pair.getLeftValue()));
        this.addColumn(new NameValuePair(pair.getRightName(), pair.getRightValue()));
    }

    public List<String> getColumnNamesExcluding(String ...fieldNames) {
        return row.getColumnNamesExcluding(fieldNames);
    }

    public boolean containsPairNames(final String leftName, final String rightName) {
        return row.containsValuesFor(leftName, rightName);
    }

    public boolean containsPairNames(final PairCombination aPair) {
        if(aPair==null){
            return false;
        }
        return containsPairNames(aPair.getLeftName(), aPair.getRightName());
    }

    public String isMissingOneColumnValueFromThisPair(PairCombination aPair) {
        if(getCellFor(aPair.getLeftName()) == null &&
                getCellFor(aPair.getRightName()) !=null){
            // left named value is missing
            return aPair.getLeftName();
        }
        if(getCellFor(aPair.getLeftName()) != null &&
                getCellFor(aPair.getRightName()) ==null){
            // right named value is missing
            return aPair.getRightName();
        }
        return null; // both are missing or both are present - use containsPairNames if you don't already know that
    }

    public boolean containsPair(final PairCombination aPair) {
        final NameValuePair leftPart = getCellFor(aPair.getLeftName());
        final NameValuePair rightPart = getCellFor(aPair.getRightName());

        if(leftPart==null || !leftPart.getValue().equals(aPair.getLeftValue())){
            return false;
        }
        if(rightPart==null || !rightPart.getValue().equals(aPair.getRightValue())){
            return false;
        }

        return true;
    }


    public List<String> getBlankColumnNames(final List<String> columnNames) {
        return row.getEmptyColumnNames(columnNames);
    }

    public ResultsRow cloneThis() {
        return new ResultsRow(row.cloneThis());
    }

    public int getColumnCount() {
        return row.getColumnCount();
    }

    public boolean matches(final ResultsRow candidateRow) {
        return this.row.matches(candidateRow.getSparseRow());
    }

    private SparseRow getSparseRow() {
        return row;
    }

    public List<PairCombination> getPairs() {

        List<PairCombination> combos = new ArrayList<>();

        for(int leftFieldIndex = 0; leftFieldIndex < getColumnCount(); leftFieldIndex++){
            for(int rightFieldIndex = leftFieldIndex+1; rightFieldIndex < getColumnCount(); rightFieldIndex++){
                combos.add(new PairCombination(
                        row.getCellByIndex(leftFieldIndex).getName(), row.getCellByIndex(leftFieldIndex).getValue(),
                        row.getCellByIndex(rightFieldIndex).getName(), row.getCellByIndex(rightFieldIndex).getValue()
                            )
                        );
            }
        }
        return combos;
    }

    public List<String> getColumnNames() {
        return row.getColumnNames();
    }

}
