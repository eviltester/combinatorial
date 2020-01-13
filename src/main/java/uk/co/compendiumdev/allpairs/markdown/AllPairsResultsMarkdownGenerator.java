package uk.co.compendiumdev.allpairs.markdown;

import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;
import uk.co.compendiumdev.allpairs.domain.results.AllPairsResults;

import java.util.List;

public class AllPairsResultsMarkdownGenerator {
    private final AllPairsResults results;

    public AllPairsResultsMarkdownGenerator(final AllPairsResults allPairsResults) {
        this.results = allPairsResults;
    }

    public String render() {
            List<String> columnNames = results.getColumnNames();

            StringBuilder title = new StringBuilder();
            StringBuilder underline = new StringBuilder();
            String endAppend = "";

            title.append("| id ");
            underline.append("|---");


            for(String columnName : columnNames){
                title.append(String.format("| %s ", columnName));
                underline.append("|---");
                endAppend = "|";
            }
            title.append(endAppend);
            underline.append(endAppend);


            StringBuilder output = new StringBuilder();
            output.append(title.toString());
            output.append(String.format("%n"));
            output.append(underline.toString());
            output.append(String.format("%n"));

            int rowId = 1;

            for(ResultsRow row : results.getRows()){
                StringBuilder rowOutput = new StringBuilder();
                endAppend = "";
                rowOutput.append(String.format("| %d ", rowId));
                for(String columnName : columnNames){
                    // at the moment it might be null as we haven't padded out sparse values
                    final NameValuePair tuple = row.getCellFor(columnName);
                    String tupleValue = "";
                    if (tuple != null) {
                        tupleValue = tuple.getValue();
                    }
                    rowOutput.append(String.format("| %s ", tupleValue));
                    endAppend = "|";
                }
                rowOutput.append(endAppend);
                output.append(rowOutput.toString());
                output.append(String.format("%n"));
                rowId++;
            }

            return output.toString();
    }
}
