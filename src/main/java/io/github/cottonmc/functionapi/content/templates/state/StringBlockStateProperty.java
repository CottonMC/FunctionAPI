package io.github.cottonmc.functionapi.content.templates.state;

import net.minecraft.state.property.AbstractProperty;

import java.util.*;

public class StringBlockStateProperty extends AbstractProperty<StringBlockStateValue> {

    private final Set<StringBlockStateValue> values = new HashSet<>();

    public StringBlockStateProperty(String name, String... values) {
        super(name, StringBlockStateValue.class);

        for (String value : values) {
            this.values.add(new StringBlockStateValue(value.trim().toLowerCase()));
        }
    }

    public void addValue(String value) {
        values.add(new StringBlockStateValue(value.trim().toLowerCase()));
    }

    @Override
    public Collection<StringBlockStateValue> getValues() {
        return values;
    }

    @Override
    public Optional<StringBlockStateValue> getValue(String s) {
        s = s.trim().toLowerCase();
        for (StringBlockStateValue value : values) {
            if(value.getValue().equals(s)){
                return Optional.of(value);
            }
        }

        return Optional.empty();
    }

    @Override
    public String getName(StringBlockStateValue s) {
        if (values.contains(s))
            return s.getValue();
        return "";
    }
}
