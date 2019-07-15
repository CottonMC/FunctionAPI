package hu.frontrider.functionapi.mixin;

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
public class ServerMixin {

    @Inject(at=@At("TAIL"),method = "reloadDataPacks")
    private void reload(LevelProperties levelProperties_1, CallbackInfo ci){
        Registry.BLOCK.stream().parallel().filter(obj-> obj instanceof ScriptedObject).forEach(obj -> ((ScriptedObject)obj).markDirty());
        Registry.ITEM.stream().parallel().filter(obj-> obj instanceof ScriptedObject).forEach(obj -> ((ScriptedObject)obj).markDirty());
    }


}
