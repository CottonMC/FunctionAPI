package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.CommandSourceExtension;
import io.github.cottonmc.functionapi.api.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStack.class, priority = 0)
public abstract class ItemStackMixin {

    @Shadow
    public abstract Item getItem();

    @Inject(
            at = @At("TAIL"),
            method = "useOnBlock",
            cancellable = true
    )
    private void useOnBlock(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir) {

        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();

        if (!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();

            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer());
            if (itemUsageContext.getHand() == Hand.MAIN_HAND) {
                if (GlobalEventContainer.getInstance().executeEvent(target, "use_on_block", serverCommandSource)) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            } else {
                if (GlobalEventContainer.getInstance().executeEvent(target, "use_on_block_offhand", serverCommandSource)) {
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
    private void useOnBlockBefore(ItemUsageContext itemUsageContext, CallbackInfoReturnable<ActionResult> cir) {


        World world = itemUsageContext.getWorld();
        BlockPos blockPos = itemUsageContext.getBlockPos();

        if (!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();

            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer());
            if (itemUsageContext.getHand() == Hand.MAIN_HAND) {
                if (GlobalEventContainer.getInstance().executeEvent(target, "before/use_on_block", serverCommandSource)) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            } else {
                if (GlobalEventContainer.getInstance().executeEvent(target, "before/use_on_block_offhand", serverCommandSource)) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }

        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "useOnEntity",
            cancellable = true
    )
    private void useOnEntityBefore(PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir) {

        World world = livingEntity_1.world;

        if (!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1);
            if (hand_1 == Hand.MAIN_HAND) {
                GlobalEventContainer.getInstance().executeEventBlocking(target, "before/use_on_entity", serverCommandSource);
                if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                    cir.setReturnValue(false);
                }
            } else {
                GlobalEventContainer.getInstance().executeEventBlocking(target, "before/use_on_entity_offhand", serverCommandSource);
                if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                    cir.setReturnValue(false);
                }
            }

        }
    }


    @Inject(
            at = @At("TAIL"),
            method = "useOnEntity"
    )
    private void useOnEntity(PlayerEntity playerEntity, LivingEntity livingEntity_1, Hand hand_1, CallbackInfoReturnable<Boolean> cir) {

        World world = livingEntity_1.world;

        if (!world.isClient()) {
            ScriptedObject target = (ScriptedObject) this.getItem();
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1);
            if (hand_1 == Hand.MAIN_HAND) {
                if(GlobalEventContainer.getInstance().executeEvent(target, "use_on_entity", serverCommandSource)){
                    cir.setReturnValue(true);
                }
            } else {
                if(GlobalEventContainer.getInstance().executeEvent(target, "use_on_entity_offhand", serverCommandSource)){
                    cir.setReturnValue(true);
                }
            }

        }


    }
}
