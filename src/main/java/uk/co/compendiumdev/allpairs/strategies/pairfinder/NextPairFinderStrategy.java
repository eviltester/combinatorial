package uk.co.compendiumdev.allpairs.strategies.pairfinder;

import uk.co.compendiumdev.allpairs.domain.AllPairsLists;
import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;
import uk.co.compendiumdev.allpairs.domain.results.ResultsRow;

public interface NextPairFinderStrategy {
    NextPairFinderStrategy fromCombinations(AllPairsLists combinations);
    NextPairFinderStrategy basedOnPairsList(final IndividualPairsList pairsList);
    NextPairFinderStrategy basedOnCurrentRow(ResultsRow aRow);
    NextPairFinderStrategy withANameValuePair(NameValuePair existingColumnData);
    NextPairFinderStrategy matchingName(String rowFieldNameToAdd);
    PairCombination findMatchingPair();
}
