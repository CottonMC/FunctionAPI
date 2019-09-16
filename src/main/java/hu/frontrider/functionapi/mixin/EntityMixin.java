package hu.frontrider.functionapi.mixin;

import blue.endless.jankson.annotation.Nullable;
import hu.frontrider.functionapi.events.EntityEventManager;
import hu.frontrider.functionapi.ScriptedObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
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
 * */
@Mixin(Entity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class EntityMixin {

    @Shadow public World world;

    @Shadow @Nullable
    public abstract MinecraftServer getServer();

    @Shadow public abstract BlockPos getBlockPos();

    private Identifier thisId = null;

    private Identifier tickId = getID("tick");
    private Identifier swimStartId = getID("swimstart");
    private Identifier damageID = getID("damage");
    private Identifier killedOtherID = getID("killed_other");
    private Identifier struckByLightningID = getID("struck_by_lightning");

    @Inject(
            at = @At("TAIL"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        EntityEventManager.getINSTANCE().fire((Entity) (Object) this,tickId);
    }

    @Inject(
            at = @At("TAIL"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci){
        EntityEventManager.getINSTANCE().fire((Entity) (Object) this,swimStartId);
    }

    @Inject(at = @At("TAIL"),method = "damage")
    private void damaged(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir){
        EntityEventManager.getINSTANCE().fire((Entity) (Object) this,damageID);
    }

    @Inject(at = @At("TAIL"),method = "onKilledOther")
    private void killedOther(LivingEntity livingEntity_1, CallbackInfo ci){
        EntityEventManager.getINSTANCE().fire((Entity) (Object) this,killedOtherID);
    }

    @Inject(at = @At("TAIL"),method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci){
        EntityEventManager.getINSTANCE().fire((Entity) (Object) this,struckByLightningID);
    }

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.ENTITY_TYPE.getId(((Entity) (Object) this).getType());
        }
        return thisId;
    }

    /**
     * called when the block should be reloaded.
     */
    public void api_scripted$markDirty() {
    }

    public String api_scripted$getType() {
        return "entity";
    }

    protected Identifier getID(String eventName) {
        Identifier targetID = api_scripted$getID();
        return new Identifier("api", api_scripted$getType() + "-" + eventName + "-" + targetID.getNamespace() + "-" + targetID.getPath());
    }
}
