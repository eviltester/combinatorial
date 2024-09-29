package uk.co.compendiumdev.allpairs.domain.sparse;


import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;

public class NameValueCombination implements PairCombination {
    private NameValue left;
    private NameValue right;

    public NameValueCombination(NameValue left, NameValue right) {
        this.left = left;
        this.right = right;
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


    @Override
    public String toString() {
        return String.format("%s x %s  - %s, %s ", getLeftName(), getRightName(), getLeftValue(), getRightValue());
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

        if(!(base instanceof PairCombination)){
            return false;
        }
        return this.matches((PairCombination) base);
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
    public NameValue getLeft() {
        return left;
    }

    @Override
    public NameValue getRight() {
        return right;
    }

}
