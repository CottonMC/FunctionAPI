package hu.frontrider.functionapi.events.runners;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ServerCommandRunner implements EventRunner {
    private Identifier eventID;
    private boolean dirty = true;
    private List<CommandFunction> functions = new LinkedList<>();

    private ServerCommandRunner(Identifier identifier){
        eventID = identifier;
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        CommandFunctionManager commandFunctionManager = serverCommandSource.getMinecraftServer().getCommandFunctionManager();
        reload(commandFunctionManager);

        for (CommandFunction commandFunction : functions) {
            commandFunctionManager.execute(commandFunction, serverCommandSource);
        }
    }

    /**
     * checks if this manager is dirty, and if it is, reloads the functions.
     * */
    private void reload(CommandFunctionManager commandFunctionManager) {
        if (dirty) {
            TagContainer<CommandFunction> functionTagContainer = commandFunctionManager.getTags();

            commandFunctionManager.getFunctions().forEach((identifier, commandFunction) -> {
                Collection<Identifier> tagsFor = functionTagContainer.getTagsFor(commandFunction);

                boolean anyMatch = tagsFor.stream().anyMatch(id -> eventID.getNamespace().equals(id.getNamespace()) && eventID.getPath().equals(id.getPath()));
                if (anyMatch) {
                    functions.add(commandFunction);
                }
            });
            dirty = false;
        }
    }

    @Override
    public void markDirty() {
        dirty = true;
        functions.clear();
    }


    public static class ServerCommmandRunnerFactory implements EventRunnerFactory{
        @Override
        public EventRunner newEvent(Identifier id) {
            return new ServerCommandRunner(id);
        }
    }
}
