package io.github.cottonmc.functionapi.script;


import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class GlobalFunctionManager<T,S> {

    private Map<FunctionAPIIdentifier, FunctionManager<T,S>> functionManagerMap;

    private boolean disabled = false;

    protected GlobalFunctionManager() {
        functionManagerMap = new HashMap<>();
    }
    /**
     * Adds a new event manager to the system.
     */
    public void addManager(FunctionManager<T,S> functionManager) {
        FunctionAPIIdentifier eventManagerID = functionManager.getID();
        functionManagerMap.put(eventManagerID, functionManager);
        functionManager.markDirty();
        if (this.disabled) {
            functionManager.disable();
        }
    }

    public FunctionManager<T,S> createEvent(ScriptedObject target, String name) {
        return addIfMissing(getNewManager(target, name));
    }

    protected abstract FunctionManager<T,S> getNewManager(FunctionAPIIdentifier name);
    protected abstract FunctionManager<T,S> getNewManager(ScriptedObject target, String name);

    public FunctionManager<T,S> getManager(FunctionAPIIdentifier functionAPIIdentifier) {
        return functionManagerMap.get(functionAPIIdentifier);
    }

    public Set<FunctionAPIIdentifier> getEventNames() {
        return functionManagerMap.keySet();
    }

    public boolean enableEvent(FunctionAPIIdentifier functionAPIIdentifier) {
        FunctionManager<T,S> eventManager = functionManagerMap.get(functionAPIIdentifier);
        if (eventManager == null)
            return false;

        eventManager.enable();
        return true;
    }

    public boolean disableEvent(FunctionAPIIdentifier functionAPIIdentifier) {
        FunctionManager<T,S> eventManager = functionManagerMap.get(functionAPIIdentifier);
        if (eventManager == null)
            return false;

        eventManager.disable();
        return true;
    }

    public void disableAll() {
        functionManagerMap.values().parallelStream().forEach(FunctionManager::disable);
        this.disabled = true;
    }

    public void enableAll() {
        functionManagerMap.values().parallelStream().forEach(FunctionManager::enable);
        this.disabled = false;
    }

    public void markDirty() {
        functionManagerMap.values().forEach(FunctionManager::markDirty);
    }

    public void clean() {
        functionManagerMap.clear();
    }

    public boolean containsEvent(FunctionAPIIdentifier eventID) {
        return functionManagerMap.containsKey(eventID);
    }

    public FunctionManager<T,S> addIfMissing(FunctionManager<T,S> eventManager) {
        if (!containsEvent(eventManager.getID())) {
            addManager(eventManager);
            return eventManager;
        }
        return functionManagerMap.get(eventManager.getID());
    }

    public boolean executeEvent(ScriptedObject target, String name, T source) {
        FunctionManager<T,S> event = createEvent(target, name);
        event.fire(source);

        return event.hasEvents();
    }

    public T executeEventBlocking(ScriptedObject target, String name, T source) {
        createEvent(target, name).fireBlocking(source);
        return source;
    }

}
