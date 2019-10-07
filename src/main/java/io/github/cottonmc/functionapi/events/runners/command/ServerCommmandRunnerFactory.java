package io.github.cottonmc.functionapi.events.runners.command;


import io.github.cottonmc.functionapi.events.runners.EventRunner;
import io.github.cottonmc.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.util.Identifier;

public class ServerCommmandRunnerFactory implements EventRunnerFactory {
    @Override
    public EventRunner newEvent(Identifier id) {
        return new ServerCommandRunner(id);
    }
}