package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.api.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.script.*;
import net.minecraft.server.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.function.*;

/**
 * Handles an event
 */
public class DisabledEventManager extends EventManager {


    public DisabledEventManager(FunctionAPIIdentifier callbackID) {
        super(callbackID);
    }

    @Override
    protected CommandRunner<ServerCommandSource,MinecraftServer> getCommandRunner(FunctionAPIIdentifier identifier) {
        if(identifier instanceof  Identifier)
            return new ServerCommandRunner((Identifier) identifier);
        else{
            return new ServerCommandRunner(new Identifier(identifier.getNamespace(),identifier.getPath()));

        }
    }

    @Override
    public void serverInit(MinecraftServer server) {

    }

    @Override
    protected void runFunction(ServerCommandSource commandContext) {

    }

    @Override
    protected void runFunctionBlocking(ServerCommandSource commandContext) {
        commandRunner.fire(commandContext);
    }
}
