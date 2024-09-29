package uk.co.compendiumdev.allpairs.strategies.lists;

import uk.co.compendiumdev.allpairs.domain.IndividualPairsList;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValue;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;

import java.util.*;

public class ListFilter {
    private final IndividualPairsList list;

    public ListFilter(final IndividualPairsList individualPairsList) {
        this.list = individualPairsList;
    }

    public List<WeightedNameValuePairCombination> getAllMatchingPairs(final String matchingFieldName, final String valueToMatch) {
        List<WeightedNameValuePairCombination> matches = new ArrayList<>();

        for(WeightedNameValuePairCombination aPair : list.getPairs()){
            if(matchingFieldName == null || valueToMatch == null){
                // we have no preference, add it anyway
                matches.add(aPair);
                continue;
            }else {
                if (aPair.getValueFor(matchingFieldName) != null && aPair.getValueFor(matchingFieldName).equals(valueToMatch)) {
                    matches.add(aPair);
                }
            }
        }
        return matches;
    }

    public WeightedNameValuePairCombination getFirstMatchingPair(final NameValue data) {
        return getFirstMatchingPair(data.getName(), data.getValue());
    }

    public WeightedNameValuePairCombination getFirstMatchingPair(final String matchingFieldName, final String valueToMatch) {
        final List<WeightedNameValuePairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        if(matches.size()==0){
            return null;
        }
        return matches.get(0);
    }

    public WeightedNameValuePairCombination getARandomPair(final NameValue data) {
        if(data==null){
            return getARandomPair(null, null);
        }else {
            return getARandomPair(data.getName(), data.getValue());
        }
    }

    public WeightedNameValuePairCombination getARandomPair(final String matchingFieldName, final String valueToMatch) {
        final List<WeightedNameValuePairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        int range = matches.size();
        if(range>0) {
            int value = new Random().nextInt(range);
            return matches.get(value);
        }else{
            return null;
        }
    }

    public WeightedNameValuePairCombination getLeastUsedPairMatching(final NameValue data) {
        String name = null;
        String value = null;

        if(data!=null){
            name = data.getName();
            value = data.getValue();
        }

        return getLeastUsedPairMatching(name, value);
    }

    public WeightedNameValuePairCombination getLeastUsedPairMatching(final String matchingFieldName, final String valueToMatch) {
        final List<WeightedNameValuePairCombination> matches = getLowestValueUsagePairsMatching(matchingFieldName, valueToMatch);
        return getLeastUsedPairFrom(matches);
    }

    private WeightedNameValuePairCombination getLeastUsedPairFrom(final List<WeightedNameValuePairCombination> matches) {
        if(list.getPairs()==null){
            return null;
        }
        if(matches.size()>0) {
            return matches.get(0);
        }

        return null;

    }

    public WeightedNameValuePairCombination getLowestValueUsagePair() {
        return getLeastUsedPairFrom(getLowestValueUsagePairsFrom());
    }

    public List<WeightedNameValuePairCombination> getLowestValueUsagePairsFrom() {
        return getLowestValueUsagePairs(list.getPairs());
    }

    public List<WeightedNameValuePairCombination> getLowestValueUsagePairsFrom(final List<WeightedNameValuePairCombination> matches) {
        return getLowestValueUsagePairs(matches);
    }

    public List<WeightedNameValuePairCombination> getMostUsedPairs(final String name, final String value) {
        final List<WeightedNameValuePairCombination> allMatches = getAllMatchingPairs(name, value);
        final List<WeightedNameValuePairCombination> leastUsed = getLowestValueUsagePairsMatching(name, value);
        for(WeightedNameValuePairCombination deleteThis : leastUsed){
            allMatches.remove(deleteThis);
        }
        return allMatches;
    }

    private List<WeightedNameValuePairCombination> getLowestValueUsagePairs(final List<WeightedNameValuePairCombination> unsorted) {

        List<WeightedNameValuePairCombination> matches = new ArrayList<>();
        matches.addAll(unsorted);

        if(matches.size()>0) {

            Comparator<WeightedNameValuePairCombination> compareByUsage = (WeightedNameValuePairCombination p1, WeightedNameValuePairCombination p2) ->
                    new Integer(p1.getUsageCount()).compareTo(
                            new Integer(p2.getUsageCount())
                    );

            Collections.sort(matches, compareByUsage);

            int lowestUsage = matches.get(0).getUsageCount();
            List<WeightedNameValuePairCombination> deleteThese = new ArrayList<>();
            for (WeightedNameValuePairCombination combo : matches) {
                if (combo.getUsageCount() > lowestUsage) {
                    deleteThese.add(combo);
                }
            }

            for (WeightedNameValuePairCombination deleteMe : deleteThese) {
                matches.remove(deleteMe);
            }
        }

        return matches;
    }

    public List<WeightedNameValuePairCombination> getLowestValueUsagePairsMatching(final String matchingFieldName, final String valueToMatch) {
        final List<WeightedNameValuePairCombination> matches = getAllMatchingPairs(matchingFieldName, valueToMatch);
        return getLowestValueUsagePairsFrom(matches);
    }


    public List<WeightedNameValuePairCombination> getLowestValueUsagePairsMatching(NameValue node) {
        return getLowestValueUsagePairsMatching(node.getName(), node.getValue());
    }

    public List<WeightedNameValuePairCombination> getAllMatchingPairs(NameValue node) {
        return getAllMatchingPairs(node.getName(), node.getValue());
    }

}
