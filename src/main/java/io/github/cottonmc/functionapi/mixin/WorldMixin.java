package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.server.*;
import net.minecraft.server.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(World.class)
public abstract class WorldMixin{

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public abstract BlockState getBlockState(BlockPos blockPos_1);

    @Final
    @Shadow
    public boolean isClient;

    @Inject(
    at = @At("HEAD"),
    method = "breakBlock"
    )
    private void broken(BlockPos blockPos, boolean bl, Entity entity, int i, CallbackInfoReturnable<Boolean> cir){
        if(!this.isClient){
            Block block = getBlockState(blockPos).getBlock();
            ServerWorld world = (ServerWorld)(Object)this;

            GlobalEventContainer.getInstance().executeEvent((ScriptedObject)block, "broken", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, block, blockPos));
        }
    }

    @Inject(
    at = @At("RETURN"),
    method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"
    )
    private void setBlockState(BlockPos blockPos_1, BlockState blockState, int int_1, CallbackInfoReturnable<Boolean> cir){
        if(!this.isClient){
            if(int_1 == 2 && cir.getReturnValue()){
                ServerWorld world = (ServerWorld)(Object)this;
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)blockState.getBlock(), "set", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, blockState.getBlock(), blockPos_1));
            }
            if(int_1 == 67 && cir.getReturnValue()){
                ServerWorld world = (ServerWorld)(Object)this;
                GlobalEventContainer.getInstance().executeEvent((ScriptedObject)blockState.getBlock(), "piston_move", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), world, blockState.getBlock(), blockPos_1));
            }

        }
    }

}
