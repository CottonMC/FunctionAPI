package io.github.cottonmc.functionapi.content.templates.state;

import net.minecraft.state.property.AbstractProperty;

import java.util.*;

public class StringBlockstateProperty extends AbstractProperty<String> {


    private final Set<String> values = new HashSet<>();

    public StringBlockstateProperty(String name, String... values) {
        super(name, String.class);
        Collections.addAll(this.values, values);
    }

    public void addValue(String value) {
        values.add(value);
    }

    @Override
    public Collection<String> getValues() {
        return values;
    }

    @Override
    public Optional<String> getValue(String s) {
        if (values.contains(s))
            return Optional.of(s);
        return Optional.empty();
    }

    @Override
    public String getName(String s) {
        if (values.contains(s))
            return s;
        return "";
    }
}
