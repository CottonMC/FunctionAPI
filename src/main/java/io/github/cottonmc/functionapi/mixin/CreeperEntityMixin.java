package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntity {


    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world){
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "explode",
    cancellable = true)
    private void explode(CallbackInfo ci){

        ScriptedObject creeper = (ScriptedObject) this;

        if(world instanceof ServerWorld && isAlive()){
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(creeper, "before/explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                ci.cancel();
            }
            GlobalEventContainer.getInstance().executeEvent(creeper, "explode", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }
}
