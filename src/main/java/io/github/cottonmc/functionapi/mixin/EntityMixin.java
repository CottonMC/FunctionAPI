package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * generic entity events.
 */
@Mixin(Entity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class EntityMixin {

    private static EventManager tick;
    private static EventManager swimStart;
    private static EventManager damage;
    private static EventManager killedOther;
    private static EventManager struckByLightning;

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public World world;


    @Shadow
    public DimensionType dimension;
    private Identifier eventTypeID = new Identifier(FunctionAPI.MODID, "entity");

    @Inject(
            at = @At("HEAD"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        tick = EventManager.execute(tick, (ScriptedObject) this,"tick",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {
        swimStart = EventManager.execute(swimStart, (ScriptedObject) this,"swim_start",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        damage = EventManager.execute(damage, (ScriptedObject) this,"damage",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void killedOther(LivingEntity livingEntity_1, CallbackInfo ci) {
        killedOther = EventManager.execute(killedOther, (ScriptedObject) this,"killed_other",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        struckByLightning = EventManager.execute(struckByLightning, (ScriptedObject) this,"struck_by_lightning",world,()->ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    public Identifier api_scripted$getID() {
        return eventTypeID;
    }

    public String api_scripted$getType() {
        return "entity";
    }

}
