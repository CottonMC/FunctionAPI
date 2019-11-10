package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.api.ScriptedObject;
import net.minecraft.util.Identifier;

public class Target implements ScriptedObject {

    public static Target ENTITY_TARGET = new Target(FunctionAPI.MODID,"entity","entity");
    public static Target SERVER_TARGET = new Target(FunctionAPI.MODID,"server","api");
    public static Target INTERNAL_TARGET = new Target(FunctionAPI.MODID,"internal","api");


    Target(String namespace,String path,String type){
        this(new Identifier(namespace,path),type);
    }

    private Identifier ID;
    private final String type;

    public Target(Identifier identifier, String type) {
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
