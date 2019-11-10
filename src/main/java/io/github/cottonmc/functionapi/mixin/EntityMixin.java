package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.CommandSourceExtension;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.Target;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * generic entity events.
 */
@Mixin(value = Entity.class, priority = 0)
public abstract class EntityMixin {

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public World world;

    @Inject(
            at = @At("HEAD"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        GlobalEventContainer.getInstance().executeEvent(Target.ENTITY_TARGET, "tick", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {
        GlobalEventContainer.getInstance().executeEvent(Target.ENTITY_TARGET, "swim_start", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "damage",
            cancellable = true)
    private void damagedBEFORE(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(Target.ENTITY_TARGET, "before/damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                cir.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        GlobalEventContainer.getInstance().executeEvent(Target.ENTITY_TARGET, "damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void killedOther(LivingEntity livingEntity_1, CallbackInfo ci) {
        GlobalEventContainer.getInstance().executeEvent(Target.ENTITY_TARGET, "killed_other", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        GlobalEventContainer.getInstance().executeEvent(Target.ENTITY_TARGET, "struck_by_lightning", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }


}
