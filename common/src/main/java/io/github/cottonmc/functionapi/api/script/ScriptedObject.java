package io.github.cottonmc.functionapi.api.script;

/**
 *  Contains information that is required for scripting.
 * */
public interface ScriptedObject {

    /**
     * Get the functionAPIIdentifier of this object (eg: minecraft:dirt)
     * */
    FunctionAPIIdentifier getID();

    /**
     * Get the type of this object, eg "block".
     * */
    String getType();
}
