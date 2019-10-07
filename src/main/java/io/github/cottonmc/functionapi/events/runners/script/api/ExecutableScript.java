package io.github.cottonmc.functionapi.events.runners.script.api;

import net.minecraft.server.command.CommandSource;

public interface ExecutableScript {

    /**
     * Runs the script's main method.
     *
     * @return false, if the script has errored.
     * */
    boolean runMain(CommandSource commandSource);
}
