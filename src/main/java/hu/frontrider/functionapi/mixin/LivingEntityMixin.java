package hu.frontrider.functionapi.mixin;

import hu.frontrider.functionapi.events.EntityEventManager;
import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class LivingEntityMixin {

    @Shadow protected boolean dead;
    private Identifier thisId = null;

    private Identifier onAttackingID = getID("on_attacking");
    private Identifier deathID = getID("on_death");

    @Inject(at = @At("TAIL"),method = "onAttacking")
    private void onAttacking(Entity entity_1, CallbackInfo ci){
        Entity thiz = (Entity) (Object) this;
        EntityEventManager.getINSTANCE().fire(thiz,onAttackingID);
    }

    /**
     * Because the entity is gone when we do the call,
     * */
    @Inject(at = @At("TAIL"),method = "onDeath")
    private void onDeath(DamageSource damageSource_1, CallbackInfo ci){
        Entity entity = (Entity) (Object) this;
        World world = entity.world;
        if(world instanceof ServerWorld && !dead){
            BlockPos blockPos = entity.getBlockPos();

            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(entity.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos);
            EntityEventManager.getINSTANCE().fire(this.api_scripted$getID(),deathID,serverCommandSource);
        }

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
    }}
