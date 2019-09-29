package hu.frontrider.functionapi.events.runners.command;


import hu.frontrider.functionapi.events.runners.EventRunner;
import hu.frontrider.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.util.Identifier;

public class ServerCommmandRunnerFactory implements EventRunnerFactory {
    @Override
    public EventRunner newEvent(Identifier id) {
        return new ServerCommandRunner(id);
    }
}