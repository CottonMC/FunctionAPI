package hu.frontrider.functionapi.events.runners.script;

import hu.frontrider.functionapi.FunctionAPI;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.io.IOUtils;

import javax.script.Invocable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class ScriptManager implements SimpleResourceReloadListener {


    private static ScriptManager INSTANCE = new ScriptManager();

    private CompletableFuture<Map<Identifier, Tag.Builder<Invocable>>> tagFuture;
    private Map<Identifier, Tag.Builder<Invocable>> scriptBuilder;

    public static ScriptManager getINSTANCE() {
        return INSTANCE;
    }

    private static Identifier ID = new Identifier(FunctionAPI.MODID, "script_loader");
    private Map<Identifier, Invocable> SCRIPTS = new HashMap<>();

    public Optional<Invocable> getScript(Identifier identifier) {
        return Optional.of(SCRIPTS.get(identifier));
    }

    public Optional<Identifier> getScriptID(Invocable invocable) {
        for (Map.Entry<Identifier, Invocable> identifierInvocableEntry : SCRIPTS.entrySet()) {
            if (identifierInvocableEntry.getValue() == invocable)
                return Optional.of(identifierInvocableEntry.getKey());
        }
        return Optional.empty();
    }

    private final List<ScriptParser> parsers;

    public ScriptManager() {
        ServiceLoader<ScriptParser> scriptParsers = ServiceLoader.load(ScriptParser.class);
        parsers = new LinkedList<>();
        scriptParsers.iterator().forEachRemaining(scriptParser -> {
            parsers.add(scriptParser);
        });
    }

    @Override
    public CompletableFuture load(ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            SCRIPTS.clear();
            Collection<Identifier> resources = resourceManager.findResources("function_api/scripts", (name) -> true);
            for (Identifier fileId : resources) {
                try {
                    Resource res = resourceManager.getResource(fileId);
                    String script = IOUtils.toString(res.getInputStream());
                    int localPath = fileId.getPath().indexOf('/') + 9;
                    Identifier scriptId = new Identifier(fileId.getNamespace(), fileId.getPath().substring(localPath));

                    parsers.forEach(scriptParser -> {

                        if (scriptParser.correctFor(scriptId)) {
                            Optional<Invocable> optionalInvocable = scriptParser.parse(script);
                            optionalInvocable.ifPresent(invocable -> SCRIPTS.put(scriptId, invocable));
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tagFuture = ScriptTags.getContainer().prepareReload(resourceManager, executor);
            return SCRIPTS;
        });
    }

    @Override
    public CompletableFuture<Void> apply(Object o, ResourceManager resourceManager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            try {

                scriptBuilder = tagFuture.get();
                ScriptTags.getContainer().applyReload(scriptBuilder);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }
}
