package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.events.runners.service.EventHandler;
import io.github.cottonmc.functionapi.events.runners.service.EventTarget;
import net.minecraft.server.command.ServerCommandSource;

@EventTarget("minecraft:block/dirt/placed")
public class DirtPlaceService implements EventHandler {
    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        System.out.println("dirt was placed down");
    }
}
