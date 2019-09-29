package hu.frontrider.functionapi;

import net.minecraft.util.Identifier;

/**
 *  Contains information that is required for scripting.
 * */
public interface ScriptedObject {

    /**
     * Get the identifier of this object (eg: minecraft:dirt)
     * */
    Identifier getID();

    /**
     * Get the type of this object, eg "block".
     * */
    String getType();
}
