package uk.co.compendiumdev.allpairs.domain;

public class PairCombination {
    private String leftName;
    private String leftValue;
    private String rightName;
    private String rightValue;
    private int usageCount;
    private PairCombination clonedFrom;

    public PairCombination(final String leftName, final String leftValue, final String rightName, final String rightValue) {
        this.leftName = leftName;
        this.rightName = rightName;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.usageCount=0;
    }

    public String getLeftName() {
        return this.leftName;
    }

    public String getLeftValue() {
        return this.leftValue;
    }

    public String getRightName() {
        return this.rightName;
    }

    public String getRightValue() {
        return this.rightValue;
    }

    public void setClonedFrom(final PairCombination clonedFrom) {
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

    public PairCombination cloneThis() {
        PairCombination cloned = new PairCombination(
                                        getLeftName(),
                                        getLeftValue(),
                                        getRightName(),
                                        getRightValue());
        cloned.setClonedFrom(this);
        return cloned;
    }

    @Override
    public String toString() {
        String isCloned = this.clonedFrom!=null ? "(cloned)" : "";
        return String.format("%s x %s - %s, %s (used %d) %s", getLeftName(), getRightName(), getLeftValue(), getRightValue(), getUsageCount(), isCloned);
    }

    public boolean hasValueFor(final String aFieldName) {

        return (
                leftName.equals(aFieldName) ||
                rightName.equals(aFieldName)
        );
    }

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

    public String getValueFor(final String fieldName) {
        if(fieldName.equals(leftName)){
            return leftValue;
        }
        if(fieldName.equals(rightName)){
            return rightValue;
        }
        System.out.println(String.format("***Warning tried to get field %s But it does not exist on this pair %s", fieldName, this.toString()));
        return null; // no matching field
    }

    /*
        I only know one field name, I want to know the other one
     */
    public String getOtherFieldName(final String existingFieldValueName) {
        if(leftName.equals(existingFieldValueName)){
            return rightName;
        }
        if(rightName.equals(existingFieldValueName)){
            return leftName;
        }
        return null; // I clearly didn't know any of the fieldnames
    }
}
