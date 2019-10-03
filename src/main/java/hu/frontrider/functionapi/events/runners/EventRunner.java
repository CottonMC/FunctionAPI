package hu.frontrider.functionapi.events.runners;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

/**
 * Defines a way to handle events.
 * has an instance inside every handled event.
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

    /**
     * can be used to manually reload the manager. Should run at the beginning of the "fire" method as well.
     * */
    void reload(MinecraftServer server);

    /**
    * @return false, if the handler is empty. used for things like item usage animations.
    * */
    boolean hasEvents();

}
