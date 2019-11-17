package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.content.ContentCommandExecutor;
import io.github.cottonmc.functionapi.content.ContentRegistrationContextImpl;
import io.github.cottonmc.functionapi.content.Registration;
import io.github.cottonmc.functionapi.content.StaticCommandFileSource;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.Target;
import io.github.cottonmc.functionapi.events.commands.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";
    public static final ContentCommandExecutor CONTENT_REGISTRATION_CONTEXT_COMMAND_EXECUTOR;


    static {
        CONTENT_REGISTRATION_CONTEXT_COMMAND_EXECUTOR = new ContentCommandExecutor.Builder().setContextFactory(ContentRegistrationContextImpl::new).setFileSource(new StaticCommandFileSource("content")).build();
        ContentCommandExecutor.INSTANCE = CONTENT_REGISTRATION_CONTEXT_COMMAND_EXECUTOR;
    }


    @Override
    public void onInitialize() {


        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        CommandRegistry.INSTANCE.register(false, InventoryCommand::register);
        CommandRegistry.INSTANCE.register(false, MoveEntityCommand::register);
        CommandRegistry.INSTANCE.register(false, ScheduleBlockTickCommand::register);
        CommandRegistry.INSTANCE.register(false, BlockStateCommand::register);


        CONTENT_REGISTRATION_CONTEXT_COMMAND_EXECUTOR.execute(new Registration());

        GlobalEventContainer.getInstance().addManager(new EventManager(Target.SERVER_TARGET, "creation"));
    }
}
