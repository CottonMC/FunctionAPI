package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.BiFunction;

@Mixin(World.class)
public abstract class WorldMixin{

    @Shadow public abstract MinecraftServer getServer();

    @Shadow public abstract BlockState getBlockState(BlockPos blockPos_1);

    @Final @Shadow public boolean isClient;

    @Inject(
            at = @At("HEAD"),
            method = "breakBlock"
    )
    private void broken(BlockPos blockPos, boolean bl, Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(!this.isClient  && !this.getServer().isLoading()){
            Block block = getBlockState(blockPos).getBlock();
            ServerWorld world = (ServerWorld)(Object) this;

            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) block, "broken",ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, block, blockPos));
        }
    }

    @Inject(
            at = @At("RETURN"),
            method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
    )
    private void setBlockState(BlockPos blockPos_1, BlockState blockState, int int_1, CallbackInfoReturnable<Boolean> cir){
        if(!this.isClient && !this.getServer().isLoading()) {
            if (int_1 == 2 && cir.getReturnValue()) {
                ServerWorld world = (ServerWorld) (Object) this;
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject) blockState.getBlock(), "set", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, blockState.getBlock(), blockPos_1));
            }
            if (int_1 == 67 && cir.getReturnValue()) {
                ServerWorld world = (ServerWorld) (Object) this;
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject) blockState.getBlock(), "piston_move", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, blockState.getBlock(), blockPos_1));
            }

        }
    }

}
