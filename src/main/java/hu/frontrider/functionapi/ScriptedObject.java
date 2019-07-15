package hu.frontrider.functionapi;

import net.minecraft.util.Identifier;

/**
 * Every scriptable object implements this.
 * */
public interface ScriptedObject {

    /**
     * The object is marked for a reload.
     * */
    void markDirty();

    /**
     * get the id of this object
     * */
    Identifier getID();

    /**
     * the type of the event is defined here.
     * */
    String getType();
}
