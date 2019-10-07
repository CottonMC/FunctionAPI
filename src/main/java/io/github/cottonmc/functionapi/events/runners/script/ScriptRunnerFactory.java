package io.github.cottonmc.functionapi.events.runners.script;

import io.github.cottonmc.functionapi.events.runners.EventRunner;
import io.github.cottonmc.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.util.Identifier;

public class ScriptRunnerFactory implements EventRunnerFactory {
    @Override
    public EventRunner newEvent(Identifier id) {
        return new ScriptEventRunner(id);
    }

}
