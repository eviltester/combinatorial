package uk.co.compendiumdev.allpairs.domain.sparse;

import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePair;
import uk.co.compendiumdev.allpairs.domain.WeightedNameValuePairCombination;

public interface PairCombination {
    String getLeftName();

    String getLeftValue();

    String getRightName();

    String getRightValue();

    @Override
    String toString();

    boolean hasValueFor(String aFieldName);

    boolean matches(PairCombination base);

    @Override
    boolean equals(Object base);

    String getValueFor(String fieldName);

    /*
            I only know one field name, I want to know the other one
         */
    String getOtherFieldName(String existingFieldValueName);

    NameValue getLeft();

    NameValue getRight();
}
