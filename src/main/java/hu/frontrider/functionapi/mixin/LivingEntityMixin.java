package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.FunctionAPI;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import hu.frontrider.functionapi.events.EventManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
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

@Mixin(LivingEntity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class LivingEntityMixin extends Entity {

    @Shadow
    protected boolean dead;

    private EventManager attackingEvent;
    private EventManager deathEvent;

    private Identifier eventTypeID = new Identifier(FunctionAPI.MODID, "livingentity");

    public LivingEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }


    @Inject(at = @At("HEAD"), method = "onAttacking")
    private void onAttacking(Entity entity_1, CallbackInfo ci) {
        if (attackingEvent == null)
            attackingEvent = new EventManager((ScriptedObject) this, "attacking");

        if (world instanceof ServerWorld && !dead) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) getEntityWorld(), this);
            attackingEvent.fire(serverCommandSource);
        }
    }

    /**
     * Because the entity is gone when we do the call,
     */
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource_1, CallbackInfo ci) {
        if (deathEvent == null)
            deathEvent = new EventManager((ScriptedObject) this, "death");

        ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), this.getPos(), getRotationClient(), (ServerWorld) getEntityWorld(), 2, getEntityName(), getName());
        deathEvent.fire(serverCommandSource);
    }

    public Identifier api_scripted$getID() {
        return eventTypeID;
    }

    public String api_scripted$getType() {
        return "livingentity";
    }

    private Identifier getID(String eventName) {
        return new Identifier(FunctionAPI.MODID, "function_api/livingentity/livingentity/" + eventName);
    }
}
