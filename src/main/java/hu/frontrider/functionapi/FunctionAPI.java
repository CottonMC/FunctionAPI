package hu.frontrider.functionapi;

import hu.frontrider.functionapi.commands.EventCommand;
import hu.frontrider.functionapi.events.EventManager;
import hu.frontrider.functionapi.events.GlobalEventContainer;
import hu.frontrider.functionapi.events.internal.InternalTarget;
import hu.frontrider.functionapi.events.internal.ServerTarget;
import hu.frontrider.functionapi.events.runners.script.ScriptManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.resource.ResourceType;

public class FunctionAPI implements ModInitializer {

    public static final String MODID = "functionapi";


    @Override
    public void onInitialize() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(ScriptManager.getINSTANCE());

        CommandRegistry.INSTANCE.register(false, EventCommand::register);
        GlobalEventContainer.getInstance().addManager(new EventManager(new ServerTarget(), "creation"));
    }

}
