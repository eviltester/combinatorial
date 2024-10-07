package uk.co.compendiumdev.allpairs.domain.sparse;

import java.util.ArrayList;
import java.util.List;

public class SparseRow {

    private List<NameValue> columns = new ArrayList<>();

    public void addColumn(final NameValue pair) {
        final NameValue existingValue = getCellFor(pair.getName());
        if(existingValue==null) {
            columns.add(pair);
        }else{
            if(!existingValue.getValue().equals(pair.getValue())){
                System.out.println(String.format(
                        "Attempt to overwrite %s : %s with value %s%n",
                        existingValue.getName(), existingValue.getValue(), pair.getValue()));
                new RuntimeException("addColumn can not be used to overwrite an existing value with a different value");
            }
        }
    }

    public NameValue getCellFor(final String name) {
        for(NameValue tuple : columns){
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
        for(NameValue tuple : columns){
            rowOutput.append(comma);
            rowOutput.append(tuple.getName());
            rowOutput.append(" : ");
            rowOutput.append(tuple.getValue());
            comma = ", ";
        }
        rowOutput.append("]");

        return rowOutput.toString();
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

        for(NameValue tuple : columns){
            columnNamesInRow.add(tuple.getName());
        }

        return columnNamesInRow;
    }

    public boolean containsValuesFor(String ...columnNames){

        for(String columnName : columnNames){
            if(getCellFor(columnName)==null){
                return false;
            }
        }

        return true;

    }

    public List<String> getEmptyColumnNames(final List<String> columnNames) {
        List<String>collatedNames = new ArrayList<>();
        collatedNames.addAll(columnNames);

        for(NameValue column :columns){
            collatedNames.remove(column.getName());
        }

        // at this point, collatedNames contains only the names we didn't find
        return collatedNames;
    }

    public SparseRow cloneThis() {
        SparseRow clone = new SparseRow();
        for(NameValue cell : columns){
            clone.addColumn(cell.cloneThis());
        }
        return clone;
    }

    public int getColumnCount() {
        return columns.size();
    }

    public boolean matches(final SparseRow candidateRow) {
        if(candidateRow.getColumnCount() != this.getColumnCount()){
            return false;
        }

        for(NameValue columnValue : columns){
            NameValue compareToCell = candidateRow.getCellFor(columnValue.getName());
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

    public NameValue getCellByIndex(int leftFieldIndex) {
        return columns.get(leftFieldIndex);
    }

    public boolean containsColumn(final String columnName) {
        return getColumnNames().contains(columnName);
    }

    public List<String> getColumnNames() {

        List<String> names = new ArrayList<>();

        for(NameValue column :columns){
            names.add(column.getName());
        }

        return names;
    }

    public SparseRow copyThis() {
        SparseRow copy = new SparseRow();
        for(NameValue cell : columns){
            copy.addColumn(new NameValuePair(cell.getName(), cell.getValue()));
        }
        return copy;
    }
}
