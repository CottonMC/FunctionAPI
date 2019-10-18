package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Shadow public abstract Item getItem();

    @Inject(
            at = @At("TAIL"),
            method = "useOnBlock",
            cancellable = true
    )
    private void useOnBlock(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir){

        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();

        if(!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();
            EventManager useOnBlock = EventManager.execute(null,target,"use_on_block",world,()->ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()));

            if(useOnBlock.hasEvents()){
                cir.setReturnValue(ActionResult.SUCCESS);
            };
        }
    }
    @Inject(
            at = @At("TAIL"),
            method = "useOnEntity",
            cancellable = true
    )
    private void useOnEntity(PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir){

        World world = livingEntity_1.world;

        if(!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();
            EventManager use = EventManager.execute(null,target,"use_on_entity",world,()->ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1));
            if(use.hasEvents()){
                cir.setReturnValue(true);
            }
        }




    }
}
