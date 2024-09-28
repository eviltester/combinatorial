package uk.co.compendiumdev.allpairs.domain.sparse;

public class NameValuePair implements NameValue {
    private final String value;
    private final String name;

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public boolean matchesName(final String name) {
        return this.name.equals(name);
    }

    public boolean matches(final String name, final String value) {
        return this.name.equals(name) && this.value.equals(value);
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
