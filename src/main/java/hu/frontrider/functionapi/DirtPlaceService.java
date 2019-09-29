package hu.frontrider.functionapi;

import hu.frontrider.functionapi.events.runners.service.EventHandler;
import hu.frontrider.functionapi.events.runners.service.EventTarget;
import net.minecraft.server.command.ServerCommandSource;

@EventTarget("minecraft:function_api/block/dirt/placed")
public class DirtPlaceService implements EventHandler {
    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        System.out.println("dirt was placed down");
    }
}
