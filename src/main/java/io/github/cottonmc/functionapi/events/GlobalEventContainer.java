package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.script.FunctionAPIIdentifierImpl;
import io.github.cottonmc.functionapi.script.FunctionManager;
import io.github.cottonmc.functionapi.script.GlobalFunctionManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;


/**
 * Stores a reference to every event handler, so we can never duplicate them.
 */
public class GlobalEventContainer extends GlobalFunctionManager<ServerCommandSource,MinecraftServer> {

    private static GlobalEventContainer INSTANCE;

    private GlobalEventContainer() {
        super();
    }


    @Override
    protected FunctionManager<ServerCommandSource,MinecraftServer> getNewManager(FunctionAPIIdentifier name) {
        return new EventManager((Identifier) name);
    }

    @Override
    protected FunctionManager<ServerCommandSource, MinecraftServer> getNewManager(ScriptedObject target, String name) {
        return new EventManager(target,name);
    }



    public static GlobalEventContainer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GlobalEventContainer();

        return INSTANCE;
    }


    /**
     * Runs the initializtion callback for the event.
     */
    public void initCallback(FunctionAPIIdentifier eventManagerID, MinecraftServer server) {
        FunctionAPIIdentifier callbackID = new FunctionAPIIdentifierImpl(eventManagerID.getNamespace(), eventManagerID.getPath() + "_create_callback");
        EventManager managerCallback = new EventManager((Identifier) callbackID);
        managerCallback.fire(server.getCommandSource());
    }



}
