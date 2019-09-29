package hu.frontrider.functionapi.events.internal;

import hu.frontrider.functionapi.FunctionAPI;

/**
 * Used by the internal events of the function api.
 * */
public class InternalTarget extends DummyTarget {
    public InternalTarget() {
        super(FunctionAPI.MODID,"internal","api");
    }
}
