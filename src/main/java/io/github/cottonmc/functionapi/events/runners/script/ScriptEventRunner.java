package io.github.cottonmc.functionapi.events.runners.script;

import io.github.cottonmc.functionapi.events.runners.EventRunner;
import io.github.cottonmc.functionapi.events.runners.script.api.ExecutableScript;
import io.github.cottonmc.functionapi.events.runners.script.api.ServerCommandSourceWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ScriptEventRunner implements EventRunner {

    private final Identifier id;
    private boolean dirty = true;
    private List<ExecutableScript> functions = new LinkedList<>();

    ScriptEventRunner(Identifier id) {
        this.id = id;
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        reload(null);
        ServerCommandSourceWrapper serverCommandSourceWrapper = new ServerCommandSourceWrapper(serverCommandSource);
        for (ExecutableScript script : functions) {
            try {
                script.runMain(serverCommandSourceWrapper);
            } catch (RuntimeException e) {
                //in case of errors, we remove the script so it can no longer occur.
                e.printStackTrace();

                functions.remove(script);
                //if(scriptID.isPresent()){
                //    serverCommandSource.sendError(new TranslatableText("hu.frontrider.functionapi.script_error_in",scriptID.get()));
                //}else{
                    serverCommandSource.sendError(new TranslatableText("hu.frontrider.functionapi.script_error"));
                //}
            }
        }
    }

    /**
     * checks if this manager is dirty, and if it is, reloads the functions.
     */
    @Override
    public void reload(MinecraftServer server) {
        if (dirty) {
            Collection<ExecutableScript> values = ScriptTags.getContainer().getOrCreate(id).values();
            Collection<ExecutableScript> invocables = values;
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
