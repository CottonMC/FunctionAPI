package io.github.cottonmc.functionapi;

import io.github.cottonmc.functionapi.commands.EventCommand;
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


    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ScriptLoader());

        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        GlobalEventContainer.getInstance().addManager(new EventManager(new ServerTarget(), "creation"));
    }

}
