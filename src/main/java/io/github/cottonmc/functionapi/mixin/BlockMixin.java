package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * adds scripting functionality to the block class.
 */
@Mixin(Block.class)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class BlockMixin {

    private Identifier thisId = null;
    private EventManager place;
    private EventManager brake;
    private EventManager exploded;
    private EventManager steppedOn;
    private EventManager entityLanded;

    @Inject(
            at = @At("TAIL"),
            method = "onPlaced"
    )
    private void place(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        place = EventManager.execute(place, (ScriptedObject) this, "placed", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onBroken"
    )
    private void broken(IWorld world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {
        brake = EventManager.execute(brake, (ScriptedObject) this, "broken", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
    }

    @Inject(
            at = @At("TAIL"),
            method = "onDestroyedByExplosion"
    )
    private void exploded(World world_1, BlockPos blockPos_1, Explosion explosion_1, CallbackInfo ci) {
        exploded = EventManager.execute(exploded, (ScriptedObject) this, "exploded", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
    }


    @Inject(at = @At("TAIL"), method = "onSteppedOn")
    private void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        steppedOn = EventManager.execute(steppedOn, (ScriptedObject) this, "stepped_on", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
    }


    @Inject(
            at = @At("TAIL"),
            method = "onLandedUpon"
    )
    private void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {
        entityLanded = EventManager.execute(entityLanded, (ScriptedObject) this, "entity_landed", world_1, () -> ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
    }

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier api_scripted$getID() {
        if (thisId == null) {
            thisId = Registry.BLOCK.getId((Block) (Object) this);
        }
        return thisId;
    }

    public String api_scripted$getType() {
        return "block";
    }
}
