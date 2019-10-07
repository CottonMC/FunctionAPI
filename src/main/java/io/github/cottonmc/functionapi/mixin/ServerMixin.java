package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.server.MinecraftServer;
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
        //we mark all registered handlers as dirty.
        GlobalEventContainer.getInstance().markDirty();
    }

}
