package uk.co.compendiumdev.allpairs.domain.sparse;

public interface NameValue {

    boolean matchesName(final String name);
    String getValue();
    String getName();

    boolean matches(String name, String value);
    NameValue cloneThis();
}
