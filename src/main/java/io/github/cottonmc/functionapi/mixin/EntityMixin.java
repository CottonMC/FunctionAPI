package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.marker.Marked;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
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

import java.util.LinkedList;
import java.util.List;


/**
 * generic entity events.
 */
@Mixin(value = Entity.class, priority = 0)
@Implements({
@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"),
@Interface(iface = Marked.class, prefix = "api_marker$")
})
public abstract class EntityMixin{
    private CompoundTag oldTag = null;

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public World world;

    @Shadow
    public abstract EntityType<?> getType();

    private Identifier thisId = null;

    @Inject(
    at = @At("HEAD"),
    method = "baseTick"
    )
    private void tick(CallbackInfo ci){
        if(world.isClient())
            return;

        if(world instanceof ServerWorld){
            ScriptedObject entity = (ScriptedObject)this;
            GlobalEventContainer.getInstance().executeEvent(entity, "tick", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)world, (Entity)(Object)this));
        }
    }

    @Inject(
    at = @At("HEAD"),
    method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci){

        if(world instanceof ServerWorld){
            ScriptedObject entity = (ScriptedObject)this;
            GlobalEventContainer.getInstance().executeEvent(entity, "swim_start", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)world, (Entity)(Object)this));
        }
    }

    @Inject(at = @At("HEAD"), method = "damage",
    cancellable = true)
    private void damagedBEFORE(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir){

        ScriptedObject entity = (ScriptedObject)this;

        if(world instanceof ServerWorld){
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(entity, "before/damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)world, (Entity)(Object)this));

            if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                cir.cancel();
            }
            GlobalEventContainer.getInstance().executeEvent(entity, "damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)world, (Entity)(Object)this));
        }
    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci){

        if(world instanceof ServerWorld){
            ScriptedObject entity = (ScriptedObject)this;
            GlobalEventContainer.getInstance().executeEvent(entity, "struck_by_lightning", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld)world, (Entity)(Object)this));
        }
    }


    /*
     * The block for the "marker" api.
     * These are dynamic tags that can be put on one instance of an entity, itemstack or a location.
     * */

    private List<Identifier> markers = new LinkedList<>();

    @Inject(at = @At("RETURN"), method = "toTag")
    private void toTag(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir){
        ListTag markerNBT = new ListTag();

        for(Identifier marker : markers){
            markerNBT.add(StringTag.of(marker.toString()));
        }
        compoundTag.put("markers", markerNBT);
        if(oldTag == null){
            oldTag = compoundTag;
            return;
        }

    }

    @Inject(at = @At("HEAD"), method = "fromTag")
    private void fromTag(CompoundTag compoundTag, CallbackInfo cir){
        for(Tag tag : compoundTag.getList("markers", 8)){
            String id = tag.asString();
            markers.add(new Identifier(id));
        }
    }

    public boolean api_marker$hasMarker(Identifier identifier){
        return markers.contains(identifier);
    }

    public List<Identifier> api_marker$getMarkers(){
        return markers;
    }

    /*
     * marker end
     * */


    public FunctionAPIIdentifier api_scripted$getEventID(){
        if(thisId == null){
            if((Entity)(Object)this instanceof PlayerEntity){
                thisId = new Identifier("player");
            }else
                thisId = Registry.ENTITY_TYPE.getId(this.getType());
        }
        return (FunctionAPIIdentifier)thisId;
    }

    public String api_scripted$getEventType(){
        return "entity";
    }
}
