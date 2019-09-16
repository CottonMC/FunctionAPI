package hu.frontrider.functionapi.events.runners;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

/**
 * Wraps a specific way to handle events.
 *
 * */
public interface EventRunner {

    /**
     * called when the event is fired.
     * */
    void fire(ServerCommandSource serverCommandSource);

    /**
     * Marks the runner for reload, fired when things are changed.
     * Currently only on datapack reloads. The actual reloading should happen the next time the event is called, to avoid loading data/code during load. Some mods like cotton resources can trigger an early datapack reload.
     * */
    void markDirty();
}
