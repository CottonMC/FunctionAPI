package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.commands.*;
import io.github.cottonmc.functionapi.api.script.*;
import io.github.cottonmc.functionapi.events.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.server.command.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(value = ItemStack.class, priority = 0)
public abstract class ItemStackMixin{

    @Shadow
    public abstract Item getItem();

    @Inject(
    at = @At("TAIL"),
    method = "useOnBlock",
    cancellable = true
    )
    private void useOnBlock(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir){

        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();

        if(!world.isClient()){
            ScriptedObject item = (ScriptedObject)this.getItem();

            if(itemUsageContext.getHand() == Hand.MAIN_HAND){
                if(GlobalEventContainer.getInstance().executeEvent(item, "use_on_block", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))){
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }else{
                if(GlobalEventContainer.getInstance().executeEvent(item, "use_on_block_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))){
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }

        }
    }

    @Inject(
    at = @At("HEAD"),
    method = "useOnBlock",
    cancellable = true
    )
    private void useOnBlockBefore(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir){

        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();

        if(!world.isClient()){
            ScriptedObject item = (ScriptedObject)this.getItem();

            if(itemUsageContext.getHand() == Hand.MAIN_HAND){
                ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer());
                GlobalEventContainer.getInstance().executeEvent(item, "before/use_on_block", serverCommandSource);
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    cir.setReturnValue(ActionResult.FAIL);
                }

            }else{
                ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer());
                GlobalEventContainer.getInstance().executeEvent(item, "before/use_on_block_offhand", serverCommandSource);
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    cir.setReturnValue(ActionResult.FAIL);
                }
            }
        }
    }

    @Inject(
    at = @At("RETURN"),
    method = "use",
    cancellable = true)
    private void use(World world, PlayerEntity playerEntity, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(!world.isClient()){
            ScriptedObject item = (ScriptedObject)this.getItem();

            if(hand == Hand.MAIN_HAND){
                if(GlobalEventContainer.getInstance().executeEvent(item, "use", ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, playerEntity))){
                    cir.setReturnValue(TypedActionResult.success((ItemStack)(Object)this));
                }
            }else{
                if(GlobalEventContainer.getInstance().executeEvent(item, "use_offhand",ServerCommandSourceFactory.INSTANCE.create((ServerWorld)world, playerEntity) )){
                    cir.setReturnValue(TypedActionResult.success((ItemStack)(Object)this));
                }
            }
        }
    }

    @Inject(
    at = @At("HEAD"),
    method = "useOnEntity",
    cancellable = true
    )
    private void useOnEntityBefore(PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir){
        World world = livingEntity_1.world;

        if(!world.isClient()){
            ScriptedObject item = (ScriptedObject)this.getItem();

            if(hand_1 == Hand.MAIN_HAND){
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(item, "before/use_on_entity", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity_1));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    cir.setReturnValue(false);
                }
            }else{
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(item, "before/use_on_entity_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity_1));
                if(((CommandSourceExtension)serverCommandSource).isCancelled()){
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(
    at = @At("TAIL"),
    method = "useOnEntity"
    )
    private void useOnEntity(PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir){

        World world = livingEntity_1.world;

        if(!world.isClient()){
            ScriptedObject item = (ScriptedObject)this.getItem();
            if(hand_1 == Hand.MAIN_HAND){
                if(GlobalEventContainer.getInstance().executeEvent(item, "use_on_entity", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity_1))){
                    cir.setReturnValue(true);
                }
            }else{
                if(GlobalEventContainer.getInstance().executeEvent(item, "use_on_entity_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld)world, livingEntity_1))){
                    cir.setReturnValue(true);
                }
            }

        }
    }
}
