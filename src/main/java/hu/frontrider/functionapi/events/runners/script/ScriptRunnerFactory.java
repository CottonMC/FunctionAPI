package hu.frontrider.functionapi.events.runners.script;

import hu.frontrider.functionapi.events.runners.EventRunner;
import hu.frontrider.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.util.Identifier;

public class ScriptRunnerFactory implements EventRunnerFactory {
    @Override
    public EventRunner newEvent(Identifier id) {
        return new ScriptEventRunner(id);
    }

}
