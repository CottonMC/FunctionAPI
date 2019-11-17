package io.github.cottonmc.functionapi.api.script;


public interface CommandRunner<C,S> {

    void fire(C context);
    void reload(S minecraftServer);
    void markDirty();
    boolean hasScripts();

}
