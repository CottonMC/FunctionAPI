package hu.frontrider.functionapi.events.context;

import net.minecraft.util.Identifier;

public class ValueStorage {
    private Class type;
    private Object value;

    public ValueStorage(Class type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public boolean isType(Class targetType){
        return type == targetType;
    }
}
