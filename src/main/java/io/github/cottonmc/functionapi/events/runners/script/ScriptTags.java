package io.github.cottonmc.functionapi.events.runners.script;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.events.runners.script.api.ExecutableScript;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;

import javax.script.Invocable;
import java.util.Collection;

public class ScriptTags {
    private static TagContainer<ExecutableScript> container = new TagContainer<>(ScriptLoader::getScriptKey, "tags/scripts", true, "script");
    private static int latestVersion;

    public static final Tag<ExecutableScript> LOAD = register(new Identifier(FunctionAPI.MODID, "load"));
    public static final Tag<ExecutableScript> TICK = register(new Identifier(FunctionAPI.MODID, "tick"));
    public static final Tag<ExecutableScript> LISTEN = register(new Identifier(FunctionAPI.MODID, "listen"));

    public static void setContainer(TagContainer<ExecutableScript> id) {
        container = id;
        ++latestVersion;
    }

    public static TagContainer<ExecutableScript> getContainer() {
        return container;
    }

    public static Tag<ExecutableScript> register(Identifier id) {
        return new CachingTag(id);
    }

    static class CachingTag extends Tag<ExecutableScript> {
        private int version = -1;
        private Tag<ExecutableScript> delegate;

        public CachingTag(Identifier id) {
            super(id);
        }

        public boolean containsTag(ExecutableScript id) {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.contains(id);
        }

        public Collection<ExecutableScript> values() {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.values();
        }

        public Collection<Entry<ExecutableScript>> entries() {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.entries();
        }
    }

}