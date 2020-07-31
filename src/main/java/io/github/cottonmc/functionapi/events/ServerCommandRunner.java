package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.api.script.CommandRunner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.*;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ServerCommandRunner implements CommandRunner<ServerCommandSource,MinecraftServer> {
    private Identifier eventID;
    private boolean dirty = true;
    private List<CommandFunction> functions = new LinkedList<>();

    ServerCommandRunner(Identifier identifier) {
        eventID = new Identifier(identifier.getNamespace(), "function_api/" + identifier.getPath());
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        MinecraftServer minecraftServer = serverCommandSource.getMinecraftServer();
        CommandFunctionManager commandFunctionManager = minecraftServer.getCommandFunctionManager();
        reload(minecraftServer);

        for (CommandFunction commandFunction : functions) {
            commandFunctionManager.execute(commandFunction, serverCommandSource);
        }
    }

    /**
     * checks if this manager is dirty, and if it is, reloads the functions.
     *
     * @param server
     */
    @Override
    public void reload(MinecraftServer server) {
        if (dirty) {
            CommandFunctionManager commandFunctionManager = server.getCommandFunctionManager();

            Collection<CommandFunction> functions = commandFunctionManager.method_29462(eventID).values();
            this.functions.addAll(functions);

            dirty = false;
        }
    }

    @Override
    public void markDirty() {
        dirty = true;
        functions.clear();
    }

    @Override
    public boolean hasScripts() {
        return !functions.isEmpty();
    }

}
