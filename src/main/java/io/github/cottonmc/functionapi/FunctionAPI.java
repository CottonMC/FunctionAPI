package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.commands.EventCommand;
import io.github.cottonmc.functionapi.commands.InventoryCommand;
import io.github.cottonmc.functionapi.commands.MoveEntityCommand;
import io.github.cottonmc.functionapi.commands.ScheduleBlockTickCommand;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.content.commands.*;
import io.github.cottonmc.functionapi.content.Registration;
import io.github.cottonmc.functionapi.content.StaticCommandExecutor;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.internal.ServerTarget;
import io.github.cottonmc.functionapi.events.runners.script.ScriptLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";
    public static final StaticCommandExecutor<ContentRegistrationContext> CONTENT_REGISTRATION_COMMAND_EXECTUOR = new StaticCommandExecutor<>(
            "content",
            ContentRegistrationContextImpl::new,
            Registration::accept);

    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ScriptLoader());

        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        CommandRegistry.INSTANCE.register(false, InventoryCommand::register);
        CommandRegistry.INSTANCE.register(false, MoveEntityCommand::register);
        CommandRegistry.INSTANCE.register(false, ScheduleBlockTickCommand::register);

        CONTENT_REGISTRATION_COMMAND_EXECTUOR.register(PrintCommand::register);
        CONTENT_REGISTRATION_COMMAND_EXECTUOR.register(IncludeCommand::register);
        CONTENT_REGISTRATION_COMMAND_EXECTUOR.register(RegistrationCommand::register);
        CONTENT_REGISTRATION_COMMAND_EXECTUOR.register(BlockSettingsCommand::register);
        CONTENT_REGISTRATION_COMMAND_EXECTUOR.register(ItemSettingsCommand::register);


        CONTENT_REGISTRATION_COMMAND_EXECTUOR.execute();

        GlobalEventContainer.getInstance().addManager(new EventManager(new ServerTarget(), "creation"));
    }

}
