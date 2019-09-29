package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.FunctionAPI;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import hu.frontrider.functionapi.events.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
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

/**
 * generic entity events.
 */
@Mixin(Entity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class EntityMixin {

    private EventManager tick;
    private EventManager swimStart;
    private EventManager damage;
    private EventManager killedOther;
    private EventManager struckByLightning;

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public World world;

    @Shadow
    public abstract World getEntityWorld();

    private Identifier eventTypeID = new Identifier(FunctionAPI.MODID, "entity");

    @Inject(
            at = @At("HEAD"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        if (tick == null)
            tick = new EventManager((ScriptedObject) this, "tick");

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), (Entity) (Object) this);
            tick.fire(serverCommandSource);
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {

        if (swimStart == null)
            swimStart = new EventManager((ScriptedObject) this, "swim_start");

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), (Entity) (Object) this);
            swimStart.fire(serverCommandSource);
        }
    }

    @Inject(at = @At("HEAD"), method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {

        if (damage == null)
            damage = new EventManager((ScriptedObject) this, "damage");

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), (Entity) (Object) this);
            damage.fire(serverCommandSource);
        }
    }

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void killedOther(LivingEntity livingEntity_1, CallbackInfo ci) {
        if (killedOther == null)
            killedOther = new EventManager((ScriptedObject) this, "killed_other");

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), (Entity) (Object) this);
            killedOther.fire(serverCommandSource);
        }
    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        if (struckByLightning == null)
            struckByLightning = new EventManager((ScriptedObject) this, "struck_by_lightning");

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), (Entity) (Object) this);
            struckByLightning.fire(serverCommandSource);
        }
    }

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier api_scripted$getID() {
        return eventTypeID;
    }

    public String api_scripted$getType() {
        return "entity";
    }

}
