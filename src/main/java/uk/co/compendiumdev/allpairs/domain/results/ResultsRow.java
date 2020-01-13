package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;

import java.util.ArrayList;
import java.util.List;

public class ResultsRow {

    private List<NameValuePair> columns = new ArrayList<>();

    public void addColumn(final NameValuePair allPairTuple) {
        final NameValuePair existingValue = getCellFor(allPairTuple.getName());
        if(existingValue==null) {
            columns.add(allPairTuple);
        }else{
            if(!existingValue.getValue().equals(allPairTuple.getValue())){
                System.out.println(String.format(
                        "Attempt to overwrite %s : %s with value %s%n",
                        existingValue.getName(), existingValue.getValue(), allPairTuple.getValue()));
                new RuntimeException("addColumn can not be used to overwrite an existing value with a different value");
            }
        }
    }

    public NameValuePair getCellFor(final String name) {
        for(NameValuePair tuple : columns){
            if(tuple.matchesName(name)){
                return tuple;
            }
        }
        return null;
    }

    @Override
    public String toString(){

        StringBuilder rowOutput = new StringBuilder();
        String comma = "";
        rowOutput.append(" [");
        for(NameValuePair tuple : columns){
            rowOutput.append(comma);
            rowOutput.append(tuple.getName());
            rowOutput.append(" : ");
            rowOutput.append(tuple.getValue());
            comma = ", ";
        }
        rowOutput.append("]");

        return rowOutput.toString();
    }

    public void addPair(final PairCombination pair) {
        this.addColumn(new NameValuePair(pair.getLeftName(), pair.getLeftValue()));
        this.addColumn(new NameValuePair(pair.getRightName(), pair.getRightValue()));
    }

    public List<String> getColumnNamesExcluding(String ...fieldNames) {

        List<String>columnNamesInRow = getAllColumnNames();

        for(String fieldName : fieldNames){
            columnNamesInRow.remove(fieldName);
        }

        return columnNamesInRow;
    }

    private List<String> getAllColumnNames() {

        List<String>columnNamesInRow = new ArrayList<>();

        for(NameValuePair tuple : columns){
            columnNamesInRow.add(tuple.getName());
        }

        return columnNamesInRow;
    }

    public boolean containsPairNames(final String leftName, final String rightName) {
        return (getCellFor(leftName) != null &&
                getCellFor(rightName) !=null);
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
        List<String>collatedNames = new ArrayList<>();
        collatedNames.addAll(columnNames);

        for(NameValuePair column :columns){
            collatedNames.remove(column.getName());
        }

        // at this point, collatedNames contains only the names we didn't find
        return collatedNames;
    }

    public ResultsRow cloneThis() {
        ResultsRow clone = new ResultsRow();
        for(NameValuePair column : columns){
            clone.addColumn(new NameValuePair(column.getName(), column.getValue()));
        }
        return clone;
    }

    public int getColumnCount() {
        return columns.size();
    }

    public boolean matches(final ResultsRow candidateRow) {
        if(candidateRow.getColumnCount() != this.getColumnCount()){
            return false;
        }

        for(NameValuePair columnValue : columns){
            NameValuePair compareToCell = candidateRow.getCellFor(columnValue.getName());
            if(compareToCell==null){
                return false;
            }else{
                if(!columnValue.getValue().equals(compareToCell.getValue())){
                    return false;
                }
            }
        }

        return true;
    }

    public List<PairCombination> getPairs() {

        List<PairCombination> combos = new ArrayList<>();

        for(int leftFieldIndex = 0; leftFieldIndex < columns.size(); leftFieldIndex++){
            for(int rightFieldIndex = leftFieldIndex+1; rightFieldIndex < columns.size(); rightFieldIndex++){
                combos.add(new PairCombination(
                                    columns.get(leftFieldIndex).getName(), columns.get(leftFieldIndex).getValue(),
                                    columns.get(rightFieldIndex).getName(), columns.get(rightFieldIndex).getValue()
                            )
                        );
            }
        }
        return combos;
    }

    public boolean containsColumn(final String columnName) {
        return false;
    }

    public List<String> getColumnNames() {

        List<String> names = new ArrayList<>();

        for(NameValuePair column :columns){
            names.add(column.getName());
        }

        return names;
    }
}
