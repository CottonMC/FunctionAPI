package io.github.cottonmc.functionapi.events.internal;

import io.github.cottonmc.functionapi.FunctionAPI;

/**
 * Used by the internal events of the function api.
 * */
public class ServerTarget extends DummyTarget {
    public ServerTarget() {
        super(FunctionAPI.MODID,"server","api");
    }
}
