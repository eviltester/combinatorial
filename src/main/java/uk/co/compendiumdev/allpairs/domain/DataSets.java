package uk.co.compendiumdev.allpairs.domain;

import java.util.*;

public class DataSets {

    Map<String, List<String>> dataSets = new HashMap<>();

    public void addDataSet(final String dataSetName, final List<String> myValues) {
        dataSets.put(dataSetName, myValues);
    }

    public void addDataSet(final String name, final String ...values) {
        List<String> myValues = Arrays.asList(values);
        addDataSet(name, myValues);
    }

    public int size() {
        return dataSets.size();
    }

    public List<String> getDataSetValues(final String dataSetName) {
        return dataSets.get(dataSetName);
    }

    public List<String> getDataSetNames() {
        return new ArrayList<>(dataSets.keySet());
    }
}
