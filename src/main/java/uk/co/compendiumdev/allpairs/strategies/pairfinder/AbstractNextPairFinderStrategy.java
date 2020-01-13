package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

public abstract class AbstractNextPairFinderStrategy implements NextPairFinderStrategy{

    protected IndividualPairsList pairsList;
    protected AllPairsLists combinations;
    protected ResultsRow currentRow;
    protected NameValuePair populatedColumnData;
    protected String matchingFieldName;

    public NextPairFinderStrategy fromCombinations(final AllPairsLists combinations) {
        this.combinations = combinations;
        return this;
    }

    public NextPairFinderStrategy basedOnPairsList(final IndividualPairsList pairsList) {
        this.pairsList = pairsList;
        return this;
    }

    @Override
    public NextPairFinderStrategy basedOnCurrentRow(final ResultsRow aRow) {
        this.currentRow = aRow;
        return this;
    }

    @Override
    public NextPairFinderStrategy withANameValuePair(final NameValuePair columnData) {
        this.populatedColumnData = columnData;
        return this;
    }

    @Override
    public NextPairFinderStrategy matchingName(final String fieldName) {
        this.matchingFieldName = fieldName;
        return this;
    }

    public abstract PairCombination findMatchingPair();
}
