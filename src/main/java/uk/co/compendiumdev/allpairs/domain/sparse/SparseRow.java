package uk.co.compendiumdev.allpairs.domain.sparse;

import java.util.ArrayList;
import java.util.List;

public class SparseRow {

    private List<NameValuePair> columns = new ArrayList<>();

    public void addColumn(final NameValuePair pair) {
        final NameValuePair existingValue = getCellFor(pair.getName());
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

        for(NameValuePair column :columns){
            collatedNames.remove(column.getName());
        }

        // at this point, collatedNames contains only the names we didn't find
        return collatedNames;
    }

    public SparseRow cloneThis() {
        SparseRow clone = new SparseRow();
        for(NameValuePair column : columns){
            clone.addColumn(new NameValuePair(column.getName(), column.getValue()));
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

    public NameValuePair getCellByIndex(int leftFieldIndex) {
        return columns.get(leftFieldIndex);
    }

    public boolean containsColumn(final String columnName) {
        return getColumnNames().contains(columnName);
    }

    public List<String> getColumnNames() {

        List<String> names = new ArrayList<>();

        for(NameValuePair column :columns){
            names.add(column.getName());
        }

        return names;
    }

}
