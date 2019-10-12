package io.github.cottonmc.functionapi.events.runners.script;

import com.google.common.collect.ImmutableMap;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.events.runners.script.api.ExecutableScript;
import io.github.cottonmc.functionapi.events.runners.script.api.ScriptParser;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class ScriptLoader implements SimpleResourceReloadListener {
    public static Map<Identifier, ExecutableScript> SCRIPTS = new HashMap<>();
    public static SuggestionProvider<ServerCommandSource> SCRIPT_SUGGESTIONS = SuggestionProviders.register(new Identifier(FunctionAPI.MODID, "scripts"),
            (context, builder) -> CommandSource.suggestIdentifiers(SCRIPTS.keySet(), builder));
    public static SuggestionProvider<ServerCommandSource> SCRIPT_TAG_SUGGESTIONS = SuggestionProviders.register(new Identifier(FunctionAPI.MODID, "script_tags"),
            (context, builder) -> CommandSource.suggestIdentifiers(ScriptTags.getContainer().getKeys(), builder));
    private final TagContainer<ExecutableScript> SCRIPT_TAGS = ScriptTags.getContainer();
    private Map<Identifier, Tag.Builder<ExecutableScript>> scriptBuilder;
    private CompletableFuture<Map<Identifier, Tag.Builder<ExecutableScript>>> tagFuture;


    private static List<ScriptParser> parsers = new LinkedList<>();

    static {
        ServiceLoader<ScriptParser> scriptParsers = ServiceLoader.load(ScriptParser.class);
        scriptParsers.iterator().forEachRemaining(scriptParser -> parsers.add(scriptParser));
    }

    @Override
    public CompletableFuture load(ResourceManager manager, Profiler profiler, Executor executor) {

        Map<Identifier, Tag<ExecutableScript>> entries = SCRIPT_TAGS.getEntries();
        if (entries != null) {
            if (!(entries instanceof ImmutableMap))
                entries.clear();
        }
        return CompletableFuture.supplyAsync(() -> {
            SCRIPTS.clear();
            Collection<Identifier> resources = manager.findResources("scripts", (name) -> true);
            for (Identifier fileId : resources) {
                try {
                    Resource res = manager.getResource(fileId);
                    String script = IOUtils.toString(res.getInputStream());
                    int localPath = fileId.getPath().indexOf('/') + 1;
                    Identifier scriptId = new Identifier(fileId.getNamespace(), fileId.getPath().substring(localPath));
                    for (ScriptParser parser : parsers) {
                        if (parser.canParse(scriptId)) {
                            Optional<ExecutableScript> optionalExecutableScript = parser.parse(script);
                            optionalExecutableScript.ifPresent(executableScript -> SCRIPTS.put(scriptId, executableScript));
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            tagFuture = SCRIPT_TAGS.prepareReload(manager, executor);
            return SCRIPTS;
        });
    }

    @Override
    public CompletableFuture<Void> apply(Object data, ResourceManager manager, Profiler profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> {
            try {
                scriptBuilder = tagFuture.get();
                this.SCRIPT_TAGS.applyReload(scriptBuilder);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(FunctionAPI.MODID, "script_loader");
    }

    public static Optional<ExecutableScript> getScriptKey(Identifier id) {
        if (SCRIPTS.containsKey(id)) return Optional.of(SCRIPTS.get(id));
        else return Optional.empty();
    }
}