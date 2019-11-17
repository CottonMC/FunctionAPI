package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.ExtendedBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StairsBlock.class)
public abstract class StairBlockMixin {

    @Inject(at = @At("HEAD"),method = "isStairs(Lnet/minecraft/block/BlockState;)Z",cancellable = true)
    private static void isStairs(BlockState blockState_1, CallbackInfoReturnable<Boolean> cir){
        Block block = blockState_1.getBlock();
        if(block instanceof ExtendedBlockProperties){
            if(((ExtendedBlockProperties) block).isBlockStairs()){
                cir.setReturnValue(true);
            }
        }

    }
}
