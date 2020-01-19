package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;

public class Target implements ScriptedObject {

    public static Target SERVER_TARGET = new Target(FunctionAPI.MODID,"server","api");

    Target(String namespace,String path,String type){
        this((FunctionAPIIdentifier) new net.minecraft.util.Identifier(namespace,path),type);
    }

    private FunctionAPIIdentifier ID;
    private final String type;

    public Target(FunctionAPIIdentifier identifier, String type) {
        ID = identifier;
        this.type = type;
    }

    @Override
    public FunctionAPIIdentifier getEventID() {
        return ID;
    }

    @Override
    public String getEventType() {
        return type;
    }

}
