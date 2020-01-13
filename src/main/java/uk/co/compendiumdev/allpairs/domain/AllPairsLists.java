package uk.co.compendiumdev.allpairs.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllPairsLists {

    List<IndividualPairsList> pairsList = new ArrayList<>();

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

    public void sortBySizesLowToLow() {
        Collections.sort(pairsList, compareByListSize());
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
            for(PairCombination pair : aList.getPairs()){
                System.out.println(String.format("%s", pair.toString()));
            }
        }
    }


    public boolean allUsed() {
        for(IndividualPairsList aList : pairsList){
            for(PairCombination pair : aList.getPairs()){
                if(pair.getUsageCount()==0){
                    return false;
                }
            }
        }
        return true;
    }
}
