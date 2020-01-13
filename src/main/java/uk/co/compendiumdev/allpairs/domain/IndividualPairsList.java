package uk.co.compendiumdev.allpairs.domain;

import uk.co.compendiumdev.allpairs.strategies.lists.ListFilter;

import java.util.*;

public class IndividualPairsList {
    private final String rightName;
    private final String leftName;
    private List<PairCombination> pairs;
    private IndividualPairsList clonedFrom;

    public IndividualPairsList(final String leftName, final String rightName) {
        this.leftName = leftName;
        this.rightName = rightName;
        pairs = new ArrayList<>();
        clonedFrom=null;
    }

    @Override
    public String toString() {
        return String.format("%s x %s %d (%s cloned)", leftName, rightName, getPairCount(), clonedFrom==null ? "not" : "");
    }

    public boolean matchesDataSetNames(final String leftName, final String rightName) {
        if(leftName.equals(this.leftName) && rightName.equals(this.rightName)){
            return true;
        }
        if(rightName.equals(this.leftName) && leftName.equals(this.rightName)){
            return true;
        }

        return false;
    }

    public boolean includesDataSetName(final String dataSetName) {
        if(leftName.equals(dataSetName) || rightName.equals(dataSetName)){
            return true;
        }
        return false;
    }

    public IndividualPairsList addCombinations(final List<PairCombination> pairs) {
        this.pairs = pairs;
        return this;
    }

    public String getLeftName() {
        return this.leftName;
    }

    public String getRightName() {
        return this.rightName;
    }

    public List<PairCombination> getPairs() {
        return this.pairs;
    }


    public IndividualPairsList cloneThis() {
        IndividualPairsList cloned = new IndividualPairsList(this.leftName, this.rightName);
        cloned.setClonedFrom(this);
        for(PairCombination cloneThisPair : pairs){
            final PairCombination clonedPair = cloneThisPair.cloneThis();
            cloned.addPair(clonedPair);
        }
        return cloned;
    }

    private void setClonedFrom(final IndividualPairsList individualPairsList) {
        this.clonedFrom = individualPairsList;
    }

    private PairCombination addPair(final PairCombination pairCombination) {
        this.pairs.add(pairCombination);
        return pairCombination;
    }

    public ListFilter filter(){
        return new ListFilter(this);
    }

    /*
     given a pair with a set of values, delete the combination from the list
     note: this uses the values, rather than the pair object reference so we
     might be deleting a pair from a cloned list
     */
    public void deleteCombination(PairCombination baseCombination) {
        PairCombination deleteThis=null;

        // check that list matches pair combo
        if(!baseCombination.hasValueFor(leftName) || !baseCombination.hasValueFor(rightName)){
            return;
        }

        // check everything in this list
        for(PairCombination pair : pairs){
            if(pair.matches(baseCombination)){
                    deleteThis = pair;
                break;
            }
        }

        if(deleteThis!=null){
            pairs.remove(deleteThis);
        }
    }

    public void removePair(final PairCombination pairToAdd) {
        pairs.remove(pairToAdd);
    }

    public PairCombination getPair(final String name1, final String value1, final String name2, final String value2) {

        PairCombination comparison = new PairCombination(name1, value1, name2, value2);

        for(PairCombination aPair : pairs){
            if(aPair.matches(comparison)){
                return aPair;
            }
        }

        return null;
    }


    /*
        delete a pair if it has a field and value that match
     */
    public void deletePairsWith(final String fieldName, final String fieldValue) {

        List<PairCombination> deleteThese = new ArrayList<>();

        for(PairCombination pair : pairs){
            if(pair.hasValueFor(fieldName) && pair.getValueFor(fieldName).equals(fieldValue)){
                deleteThese.add(pair);
            }
        }

        for(PairCombination deleteMe : deleteThese){
            pairs.remove(deleteMe);
        }
    }


    public int getPairCount() {
        return pairs.size();
    }
}
