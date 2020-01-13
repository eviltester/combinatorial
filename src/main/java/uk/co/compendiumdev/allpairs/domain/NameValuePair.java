package uk.co.compendiumdev.allpairs.domain;

public class NameValuePair {
    private final String value;
    private final String name;

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean matchesName(final String name) {
        return this.name.equals(name);
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
