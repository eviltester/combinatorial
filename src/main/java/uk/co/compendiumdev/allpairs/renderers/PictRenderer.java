package uk.co.compendiumdev.allpairs.renderers;

import uk.co.compendiumdev.allpairs.domain.DataSets;

import java.util.Comparator;
import java.util.List;

public class PictRenderer {
    public static String asTxt(DataSets data) {
        List<String> dataNames = data.getDataSetNames();
        dataNames.sort(Comparator.comparingInt(String::length));

        int largestName = dataNames.get(0).length();

        StringBuilder output = new StringBuilder();

        output.append("#\n");
        output.append("# Pict formatted data set for https://pairwise.yuuniworks.com\n");
        output.append("#\n");
        output.append("\n");

        for(String dataSetName : dataNames){
            String values = String.join(", ", data.getDataSetValues(dataSetName));
            String format = "%-" + (largestName + 2) + "s";

            output.append( String.format(format, dataSetName + ":") + values + "\n");
        }

        return output.toString();

    }
}
