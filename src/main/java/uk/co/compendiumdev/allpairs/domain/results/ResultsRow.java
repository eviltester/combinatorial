package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValue;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValueCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;
import uk.co.compendiumdev.allpairs.domain.sparse.SparseRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResultsRow {

    private final SparseRow row;

    public ResultsRow(){
        row = new SparseRow();
    }

    public ResultsRow(SparseRow existingRow){
        row = existingRow;
    }

    public void addColumn(final NameValue allPairTuple) {
        row.addColumn(allPairTuple);
    }

    public NameValue getCellFor(final String name) {
        return row.getCellFor(name);
    }

    @Override
    public String toString(){
        return row.toString();
    }

    public void addPair(final WeightedNameValuePairCombination pair) {

        if(this.containsColumnsWithValues(pair)){ // column names only check
            System.out.println("WARNING: Attempt to add pair to a row with pair");
            return;
        }

        if(getCellFor(pair.getLeftName())!= null && !getCellFor(pair.getLeftName()).getValue().equals(pair.getLeftValue())) {
            System.out.printf("ERROR: Attempt to add pair to a row with mismatched value for %s%n", pair.getLeftName());
            throw new RuntimeException("ERROR: Attempt to add pair to a row with mismatched value");
            //return;
        }

        if(getCellFor(pair.getRightName())!= null && !getCellFor(pair.getRightName()).getValue().equals(pair.getRightValue())) {
            System.out.printf("ERROR: Attempt to add pair to a row with mismatched value for %s%n", pair.getRightName());
            throw new RuntimeException("ERROR: Attempt to add pair to a row with mismatched value");
            //return;
        }

        if(getCellFor(pair.getLeftName())== null) {
            this.addColumn(pair.getLeft());
        }
        if(getCellFor(pair.getRightName())== null) {
            this.addColumn(pair.getRight());
        }
    }

    public boolean containsColumnsWithValues(WeightedNameValuePairCombination pair) {
        return containsColumnsWithValues(pair.getLeftName(), pair.getRightName());
    }

    public boolean containsColumnsWithValues(final String leftName, final String rightName) {
        final NameValue leftPart = getCellFor(leftName);
        final NameValue rightPart = getCellFor(rightName);

        return (leftPart != null && rightPart != null);
    }

    public List<String> getColumnNamesExcluding(String ...fieldNames) {
        return row.getColumnNamesExcluding(fieldNames);
    }

    public boolean containsPairNames(final String leftName, final String rightName) {
        return row.containsValuesFor(leftName, rightName);
    }

    public boolean containsPairNames(final WeightedNameValuePairCombination aPair) {
        if(aPair==null){
            return false;
        }
        return containsPairNames(aPair.getLeftName(), aPair.getRightName());
    }

    public String isMissingOneColumnValueFromThisPair(WeightedNameValuePairCombination aPair) {
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

    public boolean containsPair(final WeightedNameValuePairCombination aPair) {
        final NameValue leftPart = getCellFor(aPair.getLeftName());
        final NameValue rightPart = getCellFor(aPair.getRightName());

        if(leftPart==null || !leftPart.getValue().equals(aPair.getLeftValue())){
            return false;
        }
        if(rightPart==null || !rightPart.getValue().equals(aPair.getRightValue())){
            return false;
        }

        return true;
    }

    /* A good fit is if neither column has a value, or if one column exists, with the value */
    public boolean isPairAGoodFitInThisRow(WeightedNameValuePairCombination pair) {
        if(this.containsColumnsWithValues(pair)){
            // both columns already exist with values
            return false;
        }

        if(getCellFor(pair.getLeftName())!= null && !getCellFor(pair.getLeftName()).getValue().equals(pair.getLeftValue())) {
            // left is mismatched
            return false;
        }

        if(getCellFor(pair.getRightName())!= null && !getCellFor(pair.getRightName()).getValue().equals(pair.getRightValue())) {
            // right is mismatched
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
                // Row does not contain pairs so create using existing name values
                combos.add(new NameValueCombination(row.getCellByIndex(leftFieldIndex), row.getCellByIndex(rightFieldIndex)));
            }
        }
        return combos;
    }

    public List<String> getColumnNames() {
        return row.getColumnNames();
    }

    /* Given a previous list of pairs, what are new in the current row */
    public List<PairCombination> pairsDiffFrom(List<PairCombination> oldPairsInRow) {
        List<PairCombination> currentPairsInRow = this.getPairs();
        List<PairCombination> newPairs = currentPairsInRow.stream().filter(it -> !oldPairsInRow.contains(it)).collect(Collectors.toList());
        return newPairs;
    }


    public boolean containsColumnWithValue(String name) {
        return getCellFor(name)!=null;
    }
}
