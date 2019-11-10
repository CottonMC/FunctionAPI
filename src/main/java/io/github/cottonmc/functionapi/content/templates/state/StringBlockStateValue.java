package io.github.cottonmc.functionapi.content.templates.state;

import java.util.Objects;

public class StringBlockStateValue implements Comparable<StringBlockStateValue>{

    private final String value;

    public StringBlockStateValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(StringBlockStateValue stringBlockstateValue) {

        if(stringBlockstateValue.value.equals(value))
            return 0;

        return value.compareTo(stringBlockstateValue.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringBlockStateValue that = (StringBlockStateValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "StringBlockStateValue{" +
                "value='" + value + '\'' +
                '}';
    }
}
