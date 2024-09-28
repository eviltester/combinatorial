package uk.co.compendiumdev.allpairs.domain;

import uk.co.compendiumdev.allpairs.domain.sparse.NameValue;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;

public class WeightedNameValuePair implements NameValue {

    private int weighting=0;
    private NameValuePair nvp;
    private WeightedNameValuePair cloneOf;

    public WeightedNameValuePair(String name, String value){
        this.nvp = new NameValuePair(name, value);
        this.weighting = 0;
        cloneOf = null;
    }

    public int getWeighting() {
        if(cloneOf!=null){
            return cloneOf.getWeighting();
        }else{
            return weighting;
        }
    }

    public void incrementWeighting(){
        if(cloneOf!=null){
            cloneOf.incrementWeighting();
        }else{
            weighting = weighting+1;
        }
    }

    @Override
    public boolean matchesName(String name) {
        return nvp.matchesName(name);
    }

    @Override
    public boolean matches(String name, String value) {
        return nvp.matches(name, value);
    }

    @Override
    public String getValue() {
        return nvp.getValue();
    }

    @Override
    public String getName() {
        return nvp.getName();
    }

    public WeightedNameValuePair cloneThis() {
        WeightedNameValuePair clone = new WeightedNameValuePair(nvp.getName(), nvp.getValue());
        clone.clonedFrom(this);
        return clone;
    }

    private void clonedFrom(WeightedNameValuePair weightedNameValuePair) {
        this.cloneOf = weightedNameValuePair;
    }
}
