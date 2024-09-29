package uk.co.compendiumdev.allpairs.domain;


import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

public class WeightedNameValuePairCombination implements PairCombination {
    private WeightedNameValuePair left;
    private WeightedNameValuePair right;
    private int usageCount;
    private WeightedNameValuePairCombination clonedFrom;

    public WeightedNameValuePairCombination(WeightedNameValuePair left, WeightedNameValuePair right) {

        // TODO: this was just a quick had to get the weighted pairs in, need to remove these fields
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

    public void setClonedFrom(final WeightedNameValuePairCombination clonedFrom) {
        this.clonedFrom = clonedFrom;
    }

    public void incrementUsage() {
        if(this.clonedFrom==null){
            this.usageCount++;
        }else{
            this.clonedFrom.incrementUsage();
        }
    }

    public int getUsageCount() {
        if(this.clonedFrom==null){
            return this.usageCount;
        }else{
            return this.clonedFrom.getUsageCount();
        }
    }

    public WeightedNameValuePairCombination cloneThis() {
        WeightedNameValuePairCombination cloned = new WeightedNameValuePairCombination(
                                            left.cloneThis(),
                                            right.cloneThis()
                                    );
        cloned.setClonedFrom(this);
        return cloned;
    }

    @Override
    public String toString() {
        String isCloned = this.clonedFrom!=null ? "(cloned)" : "";
        return String.format("%s (used %d) x %s (used %d) - %s, %s (used %d) %s", getLeftName(), getLeft().getWeighting(), getRightName(), getRight().getWeighting(), getLeftValue(), getRightValue(), getUsageCount(), isCloned);
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
        if (!(base instanceof WeightedNameValuePairCombination))
            return false;

        return this.matches((WeightedNameValuePairCombination) base);
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
    public WeightedNameValuePair getLeft() {
        return left;
    }

    @Override
    public WeightedNameValuePair getRight() {
        return right;
    }

    public int getWeighting() {
        return getUsageCount() + left.getWeighting() + right.getWeighting();
    }

    public String pairComboKey(){
        return left.getName() + ":" + getLeftValue() + "_" + right.getName() + ":" + getRightValue();
    }
}
