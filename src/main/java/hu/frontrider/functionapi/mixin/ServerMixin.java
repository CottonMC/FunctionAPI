package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.events.EntityEventManager;
import hu.frontrider.functionapi.ScriptedObject;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * resets every scriptable object, once we're done reloading.
 * */
@Mixin(MinecraftServer.class)
public abstract class ServerMixin {

    /**
     * hooks into the end of the method that runs when datapacks are reloaded.
     * */
    @Inject(at=@At("TAIL"),method = "reloadDataPacks")
    private void reload(LevelProperties levelProperties_1, CallbackInfo ci){
        /*
        * marking registry objects for reload
        * */
        Registry.BLOCK.stream().parallel().filter(obj-> obj instanceof ScriptedObject).forEach(obj -> ((ScriptedObject)obj).markDirty());
        Registry.ITEM.stream().parallel().filter(obj-> obj instanceof ScriptedObject).forEach(obj -> ((ScriptedObject)obj).markDirty());

        /*
         * we mark the entity event manager for reload.
         * */
        EntityEventManager.getINSTANCE().markDirty();
    }


}
