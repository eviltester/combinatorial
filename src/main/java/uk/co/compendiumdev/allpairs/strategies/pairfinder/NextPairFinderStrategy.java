package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValue;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

public interface NextPairFinderStrategy {
    NextPairFinderStrategy fromCombinations(AllPairsLists combinations);
    NextPairFinderStrategy basedOnPairsList(final IndividualPairsList pairsList);
    NextPairFinderStrategy basedOnCurrentRow(ResultsRow aRow);
    NextPairFinderStrategy withANameValuePair(NameValue existingColumnData);
    NextPairFinderStrategy matchingName(String rowFieldNameToAdd);
    WeightedNameValuePairCombination findMatchingPair();
}
