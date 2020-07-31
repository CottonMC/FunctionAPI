package io.github.cottonmc.functionapi;

import io.github.cottonmc.cotton.config.*;
import io.github.cottonmc.functionapi.commands.arguments.FunctionAPIIdentifierArgumentType;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.Target;
import io.github.cottonmc.functionapi.events.commands.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.ArgumentTypes;

import java.io.*;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";
    public static Config config;

    static {
    }


    @Override
    public void onInitialize() {
        config = ConfigManager.loadConfig(Config.class);

        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        //CommandRegistry.INSTANCE.register(false, dispatcher -> new InventoryCommand().register(dispatcher));
        //CommandRegistry.INSTANCE.register(false, MoveEntityCommand::register);
        CommandRegistry.INSTANCE.register(false, ScheduleBlockTickCommand::register);
        //CommandRegistry.INSTANCE.register(false,dispatcher -> new ExecuteExtension().register(dispatcher));
        //CommandRegistry.INSTANCE.register(false, commandDispatcher_1 -> new BlockStateCommand().register(commandDispatcher_1));
    }
}
