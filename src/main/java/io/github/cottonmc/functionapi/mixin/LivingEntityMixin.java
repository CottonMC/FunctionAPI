package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 0)
public abstract class LivingEntityMixin extends Entity{


    @Shadow public abstract boolean isBlocking();

    public LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }


    @Inject(at = @At("HEAD"), method = "onAttacking")
    private void onAttacking(Entity entity_1, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            ScriptedObject entity = (ScriptedObject) this;
            GlobalEventContainer.getInstance().executeEvent(entity, "attacking", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));
        }
    }

    /**
     * Because the entity is gone when we do the call,
     */
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            ScriptedObject entity = (ScriptedObject) this;
            GlobalEventContainer.getInstance().executeEvent(entity, "death", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));
        }

    }

    @Inject(at = @At("TAIL"), method = "damage", cancellable = true)
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld) {
            if (isBlocking()) {
                ScriptedObject entity = (ScriptedObject) this;
                GlobalEventContainer.getInstance().executeEvent(entity, "shield_hit", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));
            }
        }
    }

}
