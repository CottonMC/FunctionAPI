package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
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

import java.util.Random;

@Mixin(value={net.minecraft.block.BlockState.class},priority = 0)
public abstract class BlockStateMixin {

    @Shadow
    public abstract Block getBlock();

    @Inject(
            at = @At("RETURN"),
            method = "activate",
            cancellable = true
    )
    private void activate(World world_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir) {
        if (!world_1.isClient()) {

            if (hand_1 == Hand.MAIN_HAND) {
                if (playerEntity_1.getMainHandStack().isEmpty())
                    if (GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "activate", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), playerEntity_1))) {
                        cir.setReturnValue(true);
                    }
            } else {
                if (playerEntity_1.getOffHandStack().isEmpty())
                    if (GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "activate_offhand", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), playerEntity_1))) {
                        cir.setReturnValue(true);
                    }
            }
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "activate",
            cancellable = true
    )
    private void activateBefore(World world_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir) {
        if (!world_1.isClient()) {

            ServerCommandSource serverCommandSource = null;
            if (hand_1 == Hand.MAIN_HAND) {
                if (playerEntity_1.getMainHandStack().isEmpty())
                    serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject) this.getBlock(), "before/activate", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), playerEntity_1));
            } else {
                if (playerEntity_1.getOffHandStack().isEmpty())
                    serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking((ScriptedObject) this.getBlock(), "before/activate_offhand", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), playerEntity_1));
            }
            if (serverCommandSource != null &&((CommandSourceExtension) serverCommandSource).isCancelled()) {
                cir.setReturnValue(false);
            }
        }
    }


    @Inject(at = @At("TAIL"), method = "neighborUpdate")
    private void neighborUpdate(World world_1, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1, CallbackInfo ci) {
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "neighbour_update", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1));
    }

    @Inject(at = @At("TAIL"), method = "onEntityCollision")
    private void onEntityCollision(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "entity_collided", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1, entity_1));
    }

    @Inject(at = @At("TAIL"), method = "onProjectileHit")
    private void onProjectileHit(World world_1, BlockState blockState_1, BlockHitResult blockHitResult_1, Entity entity_1, CallbackInfo ci) {
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "projectile_hit", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), entity_1));
    }

    @Inject(at=@At("TAIL"),method = "scheduledTick")
    private void onScheduledTick(World world_1, BlockPos blockPos_1, Random random_1, CallbackInfo ci){
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "tick", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1))/* Fired on every scheduled tick.*/;
    }

}
