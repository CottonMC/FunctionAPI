package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class LivingEntityMixin extends Entity {

    private static EventManager attackingEvent;
    private static EventManager deathEvent;
    private static EventManager damage;
    private static EventManager shieldHit;


    private Identifier eventTypeID = new Identifier(FunctionAPI.MODID, "entity");

    public LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }


    @Inject(at = @At("HEAD"), method = "onAttacking")
    private void onAttacking(Entity entity_1, CallbackInfo ci) {
        attackingEvent = EventManager.execute(attackingEvent, (ScriptedObject) this,"attacking",world,()-> ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));
    }

    /**
     * Because the entity is gone when we do the call,
     */
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        deathEvent = EventManager.execute(deathEvent, (ScriptedObject) this,"death",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));

    }


    @Inject(at = @At("HEAD"), method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        damage = EventManager.execute(damage, (ScriptedObject) this,"damage",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));

    }

    @Inject(at = @At("HEAD"), method = "damageShield")
    private void damageShield(float float_1, CallbackInfo ci){
        shieldHit = EventManager.execute(shieldHit, (ScriptedObject) this,"shield_hit",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, this));
    }

    public Identifier api_scripted$getID() {
        return eventTypeID;
    }

    public String api_scripted$getType() {
        return "entity";
    }


}
