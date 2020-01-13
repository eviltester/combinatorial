package uk.co.compendiumdev.allpairs.domain.results;

import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.markdown.AllPairsResultsMarkdownGenerator;

import java.util.ArrayList;
import java.util.List;

public class AllPairsResults {

    List<IndividualPairsList> processedLists = new ArrayList<>();
    PairsListRows rows = new PairsListRows();

    public String renderAsMarkdown() {
        return new AllPairsResultsMarkdownGenerator(this).render();
    }

    public int pairsListsCount() {
        return rows.getRowCount();
    }

    public boolean includes(final IndividualPairsList pairsList) {
        return processedLists.contains(pairsList);
    }

    public void markListAsProcessed(final IndividualPairsList pairsList) {
        processedLists.add(pairsList);
    }


    public int countRows() {
        return rows.getRowCount();
    }

    public List<String> getColumnNames() {
        return rows.getColumnNames();
    }

    public PairsListRows getResultRows(){
        return rows;
    }

    public Iterable<ResultsRow> getRows() {
        return rows.getRows();
    }

    public void addColumnNames(final String ...columnNames) {
        rows.addColumnNames(columnNames);
    }

    public void addRow(final ResultsRow row) {
        rows.addRow(row);
    }

    public boolean hasColumnNamed(final String columnName) {
        return rows.getColumnNames().contains(columnName);
    }

    public void debugPrintRows(){
        rows.debugPrintRows();
    }

    public void addPairToExistingOrNewSparseRow(final PairCombination extraPair) {
        rows.addPairToExistingOrNewSparseRow(extraPair);
    }

    public boolean doesRowExist(final ResultsRow candidateRow) {
        for(ResultsRow aRow : rows.getRows()){
            if(aRow.matches(candidateRow)){
                return true;
            }
        }

        return false;
    }
}
