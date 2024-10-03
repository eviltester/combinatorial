package uk.co.compendiumdev.allpairs.domain;

import uk.co.compendiumdev.allpairs.domain.graph.Graph;
import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.*;

public class AllPairsLists {

    List<IndividualPairsList> pairsList;
    List<WeightedNameValuePair> weightedNameValues;

    public AllPairsLists(){
        pairsList = new ArrayList<>();
        weightedNameValues = new ArrayList<>();
    }

    public AllPairsLists(List<IndividualPairsList> givenPairsList){
        pairsList = givenPairsList;
    }

    public boolean pairExists(final String leftName, final String rightName) {
        return getPairListFor(leftName, rightName)!=null;
    }

    public IndividualPairsList createList(final String leftName, final String rightName) {

        IndividualPairsList list = new IndividualPairsList(leftName, rightName);
        pairsList.add(list);
        return list;
    }

    public void sortBySizesHighToLow() {
        Collections.sort(pairsList, compareByListSize().reversed());
    }

    public void sortBySizesLowToHigh() {
        Collections.sort(pairsList, compareByListSize());
    }

    public void sortByRandom() {
        Collections.shuffle(pairsList);
    }

    private Comparator<IndividualPairsList> compareByListSize() {
        return (IndividualPairsList o1, IndividualPairsList o2) ->
                new Integer(o1.getPairs().size()).compareTo(
                        new Integer(o2.getPairs().size())
                );
    }

    public IndividualPairsList getPairListFor(final String leftName, final String rightName) {
        for(IndividualPairsList list : pairsList){
            if(list.matchesDataSetNames(leftName, rightName)){
                return list;
            }
        }
        return null;
    }

    public List<IndividualPairsList> getPairsListsContaining(final String columnName) {
        List<IndividualPairsList>matchingPairs = new ArrayList<>();

        for(IndividualPairsList list : pairsList){
            if(list.includesDataSetName(columnName)){
                matchingPairs.add(list);
            }
        }

        return matchingPairs;
    }

    public List<IndividualPairsList> getPairsLists() {
        return pairsList;
    }

    public int countOf() {
        return pairsList.size();
    }

    public void displayListReport() {
        System.out.println(String.format("%nFinished: List Report Follows%n"));

        for(IndividualPairsList aList : pairsList){
            String left = aList.getLeftName();
            String right = aList.getRightName();

            System.out.println(String.format("%nLIST: %s x %s", left, right));
            System.out.println(String.format("--------", left, right));
            for(WeightedNameValuePairCombination pair : aList.getPairs()){
                System.out.println(String.format("%s", pair.toString()));
            }
        }
    }


    public boolean allUsed() {
        for(IndividualPairsList aList : pairsList){
            for(WeightedNameValuePairCombination pair : aList.getPairs()){
                if(pair.getUsageCount()==0){
                    return false;
                }
            }
        }
        return true;
    }


    public void updateUsageForPairs(List<PairCombination> pairsToUpdate) {
        // increment the counts for all the 'new' pairs
        for(PairCombination pairToUpdate : pairsToUpdate){
            this.getPairListFor(pairToUpdate.getLeftName(), pairToUpdate.getRightName()).getPair(pairToUpdate).incrementUsage();
        }
    }

    public void updateUsageForNodes(List<PairCombination> pairsToUpdate) {
        // increment the counts for all the nodes on the 'new' pairs
        Set<WeightedNameValuePair> nodes = new HashSet<>();

        for(PairCombination pairToUpdate : pairsToUpdate){
            nodes.add(getWeightedNameValuePair(pairToUpdate.getLeftName(), pairToUpdate.getLeftValue()));
            nodes.add(getWeightedNameValuePair(pairToUpdate.getRightName(), pairToUpdate.getRightValue()));
        }

        for(WeightedNameValuePair node : nodes){
            node.incrementWeighting();
        }
    }

    public WeightedNameValuePair getOrCreateWeightedNameValuePair(String name, String value) {

        WeightedNameValuePair wnvp;

        wnvp = getWeightedNameValuePair(name, value);

        if(wnvp == null){
            wnvp = new WeightedNameValuePair(name, value);
            weightedNameValues.add(wnvp);
        }

        return wnvp;
    }

    public WeightedNameValuePair getWeightedNameValuePair(String name, String value) {
        for(WeightedNameValuePair aPair : weightedNameValues){
            if(aPair.matches(name, value)){
                return aPair;
            }
        }
        return null;
    }


    public WeightedNameValuePairCombination getWeightedPairCombinationFor(PairCombination newPairCovered) {
        WeightedNameValuePairCombination combo = null;
        for(IndividualPairsList pairList : pairsList){
            combo = pairList.getPair(newPairCovered);
            if(combo != null){
                return combo;
            }
        }
        return null;
    }

    public Graph getGraph() {
        return null;
    }
}
