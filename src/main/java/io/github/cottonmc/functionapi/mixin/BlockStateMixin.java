package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({net.minecraft.block.BlockState.class})
public abstract class BlockStateMixin {

    @Shadow public abstract Block getBlock();

    @Inject(
            at = @At("TAIL"),
            method = "activate"
    )
    private void activate(World world_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir) {
        if(!world_1.isClient())
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(),"activate",ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), playerEntity_1));
    }

    @Inject(at = @At("TAIL"), method = "neighborUpdate")
    private void neighborUpdate(World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1, CallbackInfo ci) {
        if(!world_1.isClient())
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(),"neighbour_update",ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1));
    }

    @Inject(at = @At("TAIL"), method = "onEntityCollision")

    private void onEntityCollision(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        if(!world_1.isClient())
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(),"entity_collided",ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1,entity_1));
    }

    @Inject(at = @At("TAIL"), method = "onProjectileHit")
    private void onProjectileHit(World world_1, BlockState blockState_1, BlockHitResult blockHitResult_1, Entity entity_1, CallbackInfo ci) {
        if(!world_1.isClient())
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(),"projectile_hit",ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(),entity_1));
    }

}
