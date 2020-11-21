package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.*;
import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

/**
 * adds scripting functionality to the block class.
 */
@Mixin(value = Block.class, priority = 0)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$", remap = Interface.Remap.NONE))
public abstract class BlockMixin implements ScriptedObject{

    private Identifier thisId = null;

    @Inject(
            at = @At("TAIL"),
            method = "onPlaced"
    )
    private void place(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEventBlocking(this, "after/placed", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1));
            GlobalEventContainer.getInstance().executeEvent(this, "placed", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1));
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onPlaced",
            cancellable = true
    )
    private void placeBefore(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(this, "before/placed", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, livingEntity_1));
            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onBroken",
            cancellable = true
    )
    private void brokenBefore(WorldAccess world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(this, "before/broken", ServerCommandSourceFactory.INSTANCE.create((ServerWorld) world_1, (Block) (Object) this, blockPos_1));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onBroken"
    )
    private void broken(WorldAccess world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent(this, "broken", ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onDestroyedByExplosion"
    )
    private void exploded(World world_1, BlockPos blockPos_1, Explosion explosion_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent(this, "exploded", ServerCommandSourceFactory.INSTANCE.create(((ServerWorld) world_1).getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1));
        }
    }


    @Inject(at = @At("TAIL"), method = "onSteppedOn")
    private void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent(this, "stepped_on", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
        }
    }

    //hook for the cosmere mod.
    @Inject(at = @At("HEAD"), method = "afterBreak")
    private void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack, CallbackInfo ci){
        if (world instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)stack.getItem(), "after_break", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, (Block) (Object) this, pos, player));
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onLandedUpon", cancellable = true
    )
    private void onLandedUponBefore(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(this, "before/entity_landed",  ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                ci.cancel();
            }
        }
    }

    @Inject(
            at = @At("TAIL"),
            method = "onLandedUpon"
    )
    private void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            GlobalEventContainer.getInstance().executeEvent(this, "entity_landed", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, (Block) (Object) this, blockPos_1, entity_1));
        }
    }

    /**
     * if the block is tagged with function_api:ticks_randomly, then we enable random ticking
     *
    @Inject(
    at = @At("TAIL"),
    method = "hasRandomTicks",
    cancellable = true
    )
    private void hasRandomTicks(BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
        Collection<Identifier> tagsFor = BlockTags.getTagGroup().getTagsFor((Block)(Object)this);
        if(tagsFor.contains(FunctionAPI.hasRandoMTicks)){
            cir.setReturnValue(true);
        }
    }*/

    /**
     * Dynamically gets the id of this block instance.
     */
    public FunctionAPIIdentifier api_scripted$getEventID() {
        if (thisId == null) {
            thisId = Registry.BLOCK.getId((Block) (Object) this);
        }
        return (FunctionAPIIdentifier)thisId;
    }

    public String api_scripted$getEventType() {
        return "block";
    }

}
