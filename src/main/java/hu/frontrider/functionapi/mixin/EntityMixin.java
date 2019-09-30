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
        if (tick == null)
            tick = new EventManager((ScriptedObject) this, "tick");


        fireEventOnServer(tick);

    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {

        if (swimStart == null)
            swimStart = new EventManager((ScriptedObject) this, "swim_start");

        fireEventOnServer(swimStart);

    }

    @Inject(at = @At("HEAD"), method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        if (damage == null)
            damage = new EventManager((ScriptedObject) this, "damage");

        fireEventOnServer(damage);

    }

    @Inject(at = @At("HEAD"), method = "onKilledOther")
    private void killedOther(LivingEntity livingEntity_1, CallbackInfo ci) {
        if (killedOther == null)
            killedOther = new EventManager((ScriptedObject) this, "killed_other");


        fireEventOnServer(killedOther);

    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        if (struckByLightning == null)
            struckByLightning = new EventManager((ScriptedObject) this, "struck_by_lightning");

        fireEventOnServer(struckByLightning);
    }

    private void fireEventOnServer(EventManager eventManager) {

        if (!world.isClient()) {
            MinecraftServer server = getServer();
            ServerWorld world = server.getWorld(dimension);
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(server, world, (Entity) (Object) this);
            eventManager.fire(serverCommandSource);
        }
    }

    public Identifier api_scripted$getID() {
        return eventTypeID;
    }

    public String api_scripted$getType() {
        return "entity";
    }

}
