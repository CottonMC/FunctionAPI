package hu.frontrider.api.api;

/**
 * Wraps a specific way to handle events.
 *
 * */
public interface EventRunner {

    /**
     * called when the event is fired.
     * */
    void fire(Event serverCommandSource);

    /**
     * marks the runner for reload, fired on datapack reload
     * */
    void markDirty();
}
