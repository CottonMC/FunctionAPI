package io.github.cottonmc.functionapi.events.runners.service;

import io.github.cottonmc.functionapi.events.runners.EventRunner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;


/**
 * Runs java services as event handlers.
 * This does not support dynamic reloading.
 */
public class ServiceRunner implements EventRunner {
    private List<EventHandler> handlers;

    ServiceRunner(List<EventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        for (EventHandler handler : handlers) {
            handler.fire(serverCommandSource);
        }
    }

    @Override
    public void markDirty() {
    }

    @Override
    public void reload(MinecraftServer server) {

    }

    @Override
    public boolean hasEvents() {
        return !handlers.isEmpty();
    }

}
