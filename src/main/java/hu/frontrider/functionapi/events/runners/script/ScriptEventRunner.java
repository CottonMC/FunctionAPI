package hu.frontrider.functionapi.events.runners.script;

import hu.frontrider.functionapi.events.GlobalEventContainer;
import hu.frontrider.functionapi.events.runners.EventRunner;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ScriptEventRunner implements EventRunner {

    private final Identifier id;
    private boolean dirty = true;
    private List<Invocable> functions = new LinkedList<>();

    ScriptEventRunner(Identifier id) {
        this.id = id;
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        reload(null);
        ServerCommandSourceWrapper serverCommandSourceWrapper = new ServerCommandSourceWrapper(serverCommandSource);
        for (Invocable commandFunction : functions) {
            try {
                commandFunction.invokeFunction("main",serverCommandSourceWrapper);
            } catch (RuntimeException | ScriptException | NoSuchMethodException e) {
                //in case of errors, we remove the script so it can no longer occur.
                e.printStackTrace();
                functions.remove(commandFunction);
                Optional<Identifier> scriptID = ScriptManager.getINSTANCE().getScriptID(commandFunction);
                if(scriptID.isPresent()){
                    serverCommandSource.sendError(new TranslatableText("hu.frontrider.functionapi.script_error_in",scriptID.get()));
                }else{
                    serverCommandSource.sendError(new TranslatableText("hu.frontrider.functionapi.script_error"));
                }
            }
        }
    }

    /**
     * checks if this manager is dirty, and if it is, reloads the functions.
     */
    @Override
    public void reload(MinecraftServer server) {
        if (dirty) {
            Collection<Invocable> values = ScriptTags.getContainer().getOrCreate(id).values();
            Collection<Invocable> invocables = values;
            this.functions.addAll(invocables);

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
