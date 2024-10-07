package uk.co.compendiumdev.allpairs.domain.graph;


import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

public class Edge implements PairCombination {
    private Node left;
    private Node right;
    private int usageCount;

    public Edge(Node left, Node right) {

        this.left = left;
        this.right = right;
        this.usageCount=0;
    }

    @Override
    public String getLeftName() {
        return left.getName();
    }

    @Override
    public String getLeftValue() {
        return left.getValue();
    }

    @Override
    public String getRightName() {
        return right.getName();
    }

    @Override
    public String getRightValue() {
        return right.getValue();
    }

    public void incrementUsage() {
        this.usageCount++;
    }

    public int getUsageCount() {
        return this.usageCount;
    }

    public Edge cloneThis() {
        Edge cloned = new Edge(
            left.cloneThis(),
            right.cloneThis()
        );
        return cloned;
    }

    @Override
    public String toString() {
        return String.format("%s (used %d) x %s (used %d) - %s, %s (used %d)", getLeftName(), getLeft().getWeighting(), getRightName(), getRight().getWeighting(), getLeftValue(), getRightValue(), getUsageCount());
    }

    @Override
    public boolean hasValueFor(final String aFieldName) {

        return (
                left.getName().equals(aFieldName) ||
                right.getName().equals(aFieldName)
        );
    }

    @Override
    public boolean matches(final PairCombination base) {
        if(! this.hasValueFor(base.getLeftName()) || !this.hasValueFor(base.getRightName())){
            return false;
        }

        if(!this.getValueFor(base.getLeftName()).equals(base.getLeftValue())){
            return false;
        }

        if(!this.getValueFor(base.getRightName()).equals(base.getRightValue())){
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object base) {
        if (base == this)
            return true;
        if (!(base instanceof Edge))
            return false;

        return this.matches((Edge) base);
    }

    @Override
    public String getValueFor(final String fieldName) {
        if(fieldName.equals(left.getName())){
            return left.getValue();
        }
        if(fieldName.equals(right.getName())){
            return right.getValue();
        }
        //System.out.println(String.format("***Warning tried to get field %s But it does not exist on this pair %s", fieldName, this.toString()));
        return null; // no matching field
    }

    /*
        I only know one field name, I want to know the other one
     */
    @Override
    public String getOtherFieldName(final String existingFieldValueName) {
        if(left.getName().equals(existingFieldValueName)){
            return right.getName();
        }
        if(right.getName().equals(existingFieldValueName)){
            return left.getName();
        }
        return null; // I clearly didn't know any of the fieldnames
    }

    @Override
    public Node getLeft() {
        return left;
    }

    @Override
    public Node getRight() {
        return right;
    }

    public int getWeighting() {
        return getUsageCount() + left.getWeighting() + right.getWeighting();
    }

    public String key(){
        return left.getName() + ":" + getLeftValue() + "_" + right.getName() + ":" + getRightValue();
    }

    public boolean isBetween(Node lnode, Node rnode) {
        return matches(new Edge(lnode, rnode));
    }
}
