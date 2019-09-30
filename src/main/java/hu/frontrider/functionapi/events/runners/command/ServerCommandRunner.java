package hu.frontrider.functionapi.events.runners.command;

import hu.frontrider.functionapi.events.runners.EventRunner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ServerCommandRunner implements EventRunner {
    private Identifier eventID;
    private boolean dirty = true;
    private List<CommandFunction> functions = new LinkedList<>();

    ServerCommandRunner(Identifier identifier){
        eventID = identifier;
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
     * @param server*/
    private void reload(MinecraftServer server) {
        if (dirty) {
            CommandFunctionManager commandFunctionManager = server.getCommandFunctionManager();
            Collection<CommandFunction> functions = commandFunctionManager.getTags().getOrCreate(eventID).values();
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
    public boolean hasEvents() {
        return !functions.isEmpty();
    }

}
