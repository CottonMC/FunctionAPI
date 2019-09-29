package hu.frontrider.functionapi.events.internal;

import hu.frontrider.functionapi.FunctionAPI;

/**
 * Used by the internal events of the function api.
 * */
public class ServerTarget extends DummyTarget {
    public ServerTarget() {
        super(FunctionAPI.MODID,"server","api");
    }
}
