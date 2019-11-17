package io.github.cottonmc.functionapi.script;

import io.github.cottonmc.functionapi.api.script.CommandRunner;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;

/**
 * Handles an event
 */
public abstract class FunctionManager<T,S> {

    /*the id of the event*/
    protected final FunctionAPIIdentifier functionAPIIdentifier;

    protected final CommandRunner<T,S> commandRunner;
    /**
     * If the handler is a singleton,
     */
    private final boolean isSingleton;

    private boolean enabled = true;

    protected boolean initialized = false;

    public FunctionManager(ScriptedObject target, String eventName, boolean isSingleton) {
        this(createID(target, eventName), isSingleton);
    }

    public FunctionManager(ScriptedObject target, String eventName) {
        this(createID(target, eventName), false);
    }

    public FunctionManager(FunctionAPIIdentifier functionAPIIdentifier) {
        this(functionAPIIdentifier, false);
    }

    public FunctionManager(FunctionAPIIdentifier functionAPIIdentifier, boolean isSingleton) {
        this.functionAPIIdentifier = functionAPIIdentifier;
        this.isSingleton = isSingleton;
        commandRunner = getCommandRunner(functionAPIIdentifier);
    }

    protected abstract CommandRunner<T,S> getCommandRunner(FunctionAPIIdentifier functionAPIIdentifier);

    public static FunctionAPIIdentifier createID(ScriptedObject target, String eventName) {
        return new FunctionAPIIdentifierImpl(target.getID().getNamespace(), target.getType() + "/" + target.getID().getPath() + "/" + eventName);
    }


    public void serverInit(S server) {
        if (!initialized) {
            initialized = true;
        }
    }


    /**
     * call it with the correct context when the event should run.
     */
    public void fire(T commandContext) {
        if (enabled) {
            runFunction(commandContext);
        }
    }

    protected abstract void runFunction(T commandContext);
    protected abstract void runFunctionBlocking(T commandContext);


    /**
     * call it with the correct context when the event should run.
     */
    public void fireBlocking(T commandContext) {
        if (enabled) {
           runFunctionBlocking(commandContext);
        }
    }

    public void markDirty() {
        commandRunner.markDirty();
    }


    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean hasEvents() {
        //if it's disabled, then we say that we have no events.
        if (!enabled) {
            return false;
        }
        return commandRunner.hasScripts();
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public FunctionAPIIdentifier getID() {
        return functionAPIIdentifier;
    }

    public boolean hasEvents(S server) {
        commandRunner.reload(server);
        return commandRunner.hasScripts();
    }

}
