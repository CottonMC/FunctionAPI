package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.Target;
import io.github.cottonmc.functionapi.events.commands.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";

    static {
    }


    @Override
    public void onInitialize() {
        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        CommandRegistry.INSTANCE.register(false, dispatcher -> new InventoryCommand().register(dispatcher));
        CommandRegistry.INSTANCE.register(false, MoveEntityCommand::register);
        CommandRegistry.INSTANCE.register(false, ScheduleBlockTickCommand::register);
        CommandRegistry.INSTANCE.register(false,dispatcher -> new ExecuteExtension().register(dispatcher));
        CommandRegistry.INSTANCE.register(false, commandDispatcher_1 -> new BlockStateCommand().register(commandDispatcher_1));

        GlobalEventContainer.getInstance().addManager(new EventManager(Target.SERVER_TARGET, "creation"));
    }
}
