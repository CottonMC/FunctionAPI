package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Shadow public abstract BlockState getBlockState(BlockPos blockPos_1);

    @Shadow @Final public boolean isClient;

    @Inject(
            at = @At("HEAD"),
            method = "breakBlock"
    )
    private void broken(BlockPos blockPos_1, boolean boolean_1, CallbackInfoReturnable<Boolean> cir) {
        if(!this.isClient){
            Block blockState = getBlockState(blockPos_1).getBlock();
            ServerWorld world = (ServerWorld)(Object) this;

            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) blockState, "break",ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, blockState, blockPos_1));
        }

    }
}
