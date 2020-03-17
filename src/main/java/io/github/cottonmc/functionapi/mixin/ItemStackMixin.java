package io.github.cottonmc.functionapi.mixin;

import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.marker.Marked;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;
import java.util.List;

@Mixin(value = ItemStack.class, priority = 0)
@Implements({
        @Interface(iface = Marked.class, prefix = "api_marker$")
})
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
            ScriptedObject item = (ScriptedObject) this.getItem();

            if (itemUsageContext.getHand() == Hand.MAIN_HAND) {
                if (GlobalEventContainer.getInstance().executeEvent(item, "use_on_block", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            } else {
                if (GlobalEventContainer.getInstance().executeEvent(item, "use_on_block_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))) {
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
            ScriptedObject item = (ScriptedObject) this.getItem();

            if (itemUsageContext.getHand() == Hand.MAIN_HAND) {
                if (GlobalEventContainer.getInstance().executeEvent(item, "before/use_on_block", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))) {
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            } else {
                if (GlobalEventContainer.getInstance().executeEvent(item, "before/use_on_block_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, world.getBlockState(blockPos).getBlock(), blockPos, itemUsageContext.getPlayer()))) {
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
            ScriptedObject item = (ScriptedObject) this.getItem();

            if (hand_1 == Hand.MAIN_HAND) {
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(item, "before/use_on_entity", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1));
                if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                    cir.setReturnValue(false);
                }
            } else {
                ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(item, "before/use_on_entity_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1));
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
            ScriptedObject item = (ScriptedObject) this.getItem();
            if (hand_1 == Hand.MAIN_HAND) {
                if (GlobalEventContainer.getInstance().executeEvent(item, "use_on_entity", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1))) {
                    cir.setReturnValue(true);
                }
            } else {
                if (GlobalEventContainer.getInstance().executeEvent(item, "use_on_entity_offhand", ServerCommandSourceFactory.INSTANCE.create(world.getServer(), (ServerWorld) world, livingEntity_1))) {
                    cir.setReturnValue(true);
                }
            }

        }
    }

    private List<Identifier> markers = new LinkedList<>();

    @Inject(at = @At("RETURN"), method = "toTag")
    private void toTag(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> cir) {
        ListTag markerNBT = new ListTag();

        for (Identifier marker : markers) {
            markerNBT.add(StringTag.of(marker.toString()));
        }
        compoundTag.put("markers", markerNBT);
    }

    @Inject(at = @At("RETURN"), method = "fromTag",cancellable = true)
    private static void fromTag(CompoundTag compoundTag, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack stack = cir.getReturnValue();
        for (Tag tag : compoundTag.getList("markers", 8)) {
            String id = tag.asString();
            ((Marked)(Object)stack).getMarkers().add(new Identifier(id));
        }
    }

    public boolean api_marker$hasMarker(Identifier identifier) {
        return markers.contains(identifier);
    }

    public List<Identifier> api_marker$getMarkers() {
        return markers;
    }
}
