package hu.frontrider.functionapi.events.runners.script;

import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainer;
import net.minecraft.util.Identifier;

import javax.script.Invocable;
import java.util.Collection;

public class ScriptTags {

    private static TagContainer<Invocable> container = new TagContainer<Invocable>(
            (identifier_1) -> {
                ScriptManager scriptManager = ScriptManager.getINSTANCE();
                return scriptManager.getScript(identifier_1);
            }
            , "function_api_scripts", true, "function_api_scripts");

    private static int latestVersion;

    public static void setContainer(TagContainer<Invocable> tagContainer_1) {
        container = tagContainer_1;
        ++latestVersion;
    }

    public static TagContainer<Invocable> getContainer() {
        return container;
    }

    private static Tag<Invocable> register(String string_1) {
        return new ScriptTags.CachingTag(new Identifier(string_1));
    }

    static class CachingTag extends Tag<Invocable> {
        private int version = -1;
        private Tag<Invocable> delegate;

        public CachingTag(Identifier identifier_1) {
            super(identifier_1);
        }

        public boolean method_15076(Invocable block_1) {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.contains(block_1);
        }

        public Collection<Invocable> values() {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.values();
        }

        public Collection<Entry<Invocable>> entries() {
            if (this.version != latestVersion) {
                this.delegate = container.getOrCreate(this.getId());
                this.version = latestVersion;
            }

            return this.delegate.entries();
        }
    }
}
