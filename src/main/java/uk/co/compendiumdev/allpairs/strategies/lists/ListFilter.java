package uk.co.compendiumdev.allpairs.strategies.lists;

import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.NameValuePair;
import uk.co.compendiumdev.allpairs.domain.PairCombination;

import java.util.*;

public class ListFilter {
    private final IndividualPairsList list;

    public ListFilter(final IndividualPairsList individualPairsList) {
        this.list = individualPairsList;
    }

    public List<PairCombination> getAllMatchingPairs(final String matchingFieldName, final String valueToMatch) {
        List<PairCombination> matches = new ArrayList<>();

        for(PairCombination aPair : list.getPairs()){
            if(aPair.getValueFor(matchingFieldName).equals(valueToMatch)){
                matches.add(aPair);
            }
        }
        return matches;
    }

    public PairCombination getFirstMatchingPair(final NameValuePair data) {
        return getFirstMatchingPair(data.getName(), data.getValue());
    }

    public PairCombination getFirstMatchingPair(final String matchingFieldName, final String valueToMatch) {
        final List<PairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        if(matches.size()==0){
            return null;
        }
        return matches.get(0);
    }

    public PairCombination getARandomPair(final NameValuePair data) {
        return getARandomPair(data.getName(), data.getValue());
    }

    public PairCombination getARandomPair(final String matchingFieldName, final String valueToMatch) {
        final List<PairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        int range = matches.size();
        if(range>0) {
            int value = new Random().nextInt(range);
            return matches.get(value);
        }else{
            return null;
        }
    }

    public PairCombination getLeastUsedPairMatching(final NameValuePair data) {
        return getLeastUsedPairMatching(data.getName(), data.getValue());
    }

    public PairCombination getLeastUsedPairMatching(final String matchingFieldName, final String valueToMatch) {
        final List<PairCombination> matches = getLowestValueUsagePairsMatching(matchingFieldName, valueToMatch);
        return getLeastUsedPairFrom(matches);
    }

    private PairCombination getLeastUsedPairFrom(final List<PairCombination> matches) {
        if(list.getPairs()==null){
            return null;
        }
        if(matches.size()>0) {
            return matches.get(0);
        }

        return null;

    }

    public PairCombination getLowestValueUsagePair() {
        return getLeastUsedPairFrom(getLowestValueUsagePairsFrom());
    }

    public List<PairCombination> getLowestValueUsagePairsFrom() {
        return getLowestValueUsagePairs(list.getPairs());
    }

    public List<PairCombination> getLowestValueUsagePairsFrom(final List<PairCombination> matches) {
        return getLowestValueUsagePairs(matches);
    }

    public List<PairCombination> getMostUsedPairs(final String name, final String value) {
        final List<PairCombination> allMatches = getAllMatchingPairs(name, value);
        final List<PairCombination> leastUsed = getLowestValueUsagePairsMatching(name, value);
        for(PairCombination deleteThis : leastUsed){
            allMatches.remove(deleteThis);
        }
        return allMatches;
    }

    private List<PairCombination> getLowestValueUsagePairs(final List<PairCombination> unsorted) {

        List<PairCombination> matches = new ArrayList<>();
        matches.addAll(unsorted);

        if(matches.size()>0) {

            Comparator<PairCombination> compareByUsage = (PairCombination p1, PairCombination p2) ->
                    new Integer(p1.getUsageCount()).compareTo(
                            new Integer(p2.getUsageCount())
                    );

            Collections.sort(matches, compareByUsage);

            int lowestUsage = matches.get(0).getUsageCount();
            List<PairCombination> deleteThese = new ArrayList<>();
            for (PairCombination combo : matches) {
                if (combo.getUsageCount() > lowestUsage) {
                    deleteThese.add(combo);
                }
            }

            for (PairCombination deleteMe : deleteThese) {
                matches.remove(deleteMe);
            }
        }

        return matches;
    }

    public List<PairCombination> getLowestValueUsagePairsMatching(final String matchingFieldName, final String valueToMatch) {
        final List<PairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        return getLowestValueUsagePairsFrom(matches);
    }


}
