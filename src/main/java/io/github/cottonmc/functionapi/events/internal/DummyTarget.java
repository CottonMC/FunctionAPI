package io.github.cottonmc.functionapi.events.internal;

import io.github.cottonmc.functionapi.ScriptedObject;
import net.minecraft.util.Identifier;

/**
 * Used as a fake target for events.
 * */
public class DummyTarget implements ScriptedObject {

    public DummyTarget(String namespace,String path,String type){
        this(new Identifier(namespace,path),type);
    }

    private Identifier ID;
    private final String type;

    public DummyTarget(Identifier identifier,String type) {
        ID = identifier;
        this.type = type;
    }

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public String getType() {
        return type;
    }
}
