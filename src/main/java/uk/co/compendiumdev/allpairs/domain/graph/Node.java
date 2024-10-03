package uk.co.compendiumdev.allpairs.domain.graph;

import uk.co.compendiumdev.allpairs.domain.sparse.NameValue;
import uk.co.compendiumdev.allpairs.domain.sparse.NameValuePair;

public class Node implements NameValue {

    private int weighting=0;
    private NameValuePair nvp;

    public Node(String name, String value){
        this.nvp = new NameValuePair(name, value);
        this.weighting = 0;
    }

    public int getWeighting() {
            return weighting;
    }

    public void incrementWeighting(){
            weighting = weighting+1;
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

    public Node cloneThis() {
        Node clone = new Node(nvp.getName(), nvp.getValue());
        return clone;
    }

}
