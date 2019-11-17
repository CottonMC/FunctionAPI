package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * generic entity events.
 */
@Mixin(value = Entity.class, priority = 0)
@Implements(@Interface(iface = ScriptedObject.class,prefix = "scripted$"))
public abstract class EntityMixin{

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public World world;

    private Identifier thisId = null;

    @Shadow public abstract EntityType<?> getType();

    @Inject(
            at = @At("HEAD"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)this, "tick", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)this, "swim_start", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }

    @Inject(at = @At("HEAD"), method = "damage",
            cancellable = true)
    private void damagedBEFORE(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject)this, "before/damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                cir.cancel();
            }
        }
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject)this, "damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)this, "struck_by_lightning", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }



    public io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier scripted$getID() {
        if(thisId == null){
            if((Entity)(Object)this instanceof PlayerEntity){
                thisId = new Identifier("player");
            }else
            thisId= Registry.ENTITY_TYPE.getId(this.getType());
        }
        return (io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier) thisId;
    }

    public String scripted$getType() {
        return "entity";
    }
}
