package io.github.cottonmc.functionapi.events.internal;

import io.github.cottonmc.functionapi.FunctionAPI;

/**
 * Used by the internal events of the function api.
 * */
public class InternalTarget extends DummyTarget {
    public InternalTarget() {
        super(FunctionAPI.MODID,"internal","api");
    }
}
