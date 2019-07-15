package hu.frontrider.functionapi;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EventManager implements ScriptedObject {
    private final ScriptedObject target;
    private final String eventName;
    private Identifier identifier = null;

    private List<CommandFunction> functions = new LinkedList<>();

    private boolean dirty = true;

    public EventManager(ScriptedObject target, String eventName) {

        this.target = target;
        this.eventName = eventName;
    }

    public void fire(ServerCommandSource commandContext) {
        CommandFunctionManager commandFunctionManager = commandContext.getMinecraftServer().getCommandFunctionManager();
        reload(commandFunctionManager);

        for (CommandFunction commandFunction : functions) {
            commandFunctionManager.execute(commandFunction, commandContext);
        }
    }

    private void reload(CommandFunctionManager commandFunctionManager) {
        if (dirty) {
            TagContainer<CommandFunction> functionTagContainer = commandFunctionManager.getTags();
            Identifier eventId = getID();

            commandFunctionManager.getFunctions().forEach((identifier, commandFunction) -> {
                Collection<Identifier> tagsFor = functionTagContainer.getTagsFor(commandFunction);

                boolean anyMatch = tagsFor.stream().anyMatch(id -> eventId.getNamespace().equals(id.getNamespace()) && eventId.getPath().equals(id.getPath()));
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

    @Override
    public Identifier getID() {
        if (identifier == null) {
            Identifier targetID = target.getID();
            this.identifier = new Identifier("api", target.getType() + "-" + eventName + "-" + targetID.getNamespace() + "-" + targetID.getPath());
        }
        return identifier;
    }

    @Override
    public String getType() {
        return null;
    }
}
