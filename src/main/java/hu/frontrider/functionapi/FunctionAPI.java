package hu.frontrider.functionapi;

import hu.frontrider.functionapi.commands.EventCommand;
import hu.frontrider.functionapi.events.EventManager;
import hu.frontrider.functionapi.events.GlobalEventContainer;
import hu.frontrider.functionapi.events.internal.InternalTarget;
import hu.frontrider.functionapi.events.internal.ServerTarget;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";

    @Override
    public void onInitialize() {
        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        GlobalEventContainer.getInstance().addManager(new EventManager(new ServerTarget(), "creation"));
    }
}
