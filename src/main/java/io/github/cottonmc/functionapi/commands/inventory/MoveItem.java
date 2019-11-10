package io.github.cottonmc.functionapi.commands.inventory;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.BiDirectionalCommand;
import io.github.cottonmc.functionapi.commands.inventory.util.FloatingItemInventory;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.command.arguments.ItemPredicateArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class MoveItem implements BiDirectionalCommand {

    public static final BiDirectionalCommand UNFILTERED = new MoveItem((context -> itemStack -> itemStack.getCount() > 0 && itemStack.getItem() != Items.AIR));

    public static final BiDirectionalCommand FILTERED = new MoveItem((context -> {
        try {
            return ItemPredicateArgumentType.getItemPredicate(context, "item");
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }));

    private final Function<CommandContext<ServerCommandSource>, Predicate<ItemStack>> predicateProducer;

    private MoveItem(Function<CommandContext<ServerCommandSource>, Predicate<ItemStack>> predicateProducer) {
        this.predicateProducer = predicateProducer;
    }

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction source, Direction target) throws CommandSyntaxException {

        Inventory sourceInventory;
        Inventory targetInventory;

        int amount = IntegerArgumentType.getInteger(context, "amount");

        Predicate<ItemStack> itemPredicate = predicateProducer.apply(context);

        if (source == null) {
            Collection<? extends Entity> sourceEntities = EntityArgumentType.getEntities(context, "sourceEntity");
            if (sourceEntities.size() != 1) {
                context.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.source.multiple"));
                return 0;
            }
            Entity entity = sourceEntities.iterator().next();

            if (entity instanceof Inventory) {
                sourceInventory = (Inventory) entity;
            } else {
                context.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.source.no"));
                return 0;
            }

        } else {
            BlockPos sourceBlock = BlockPosArgumentType.getBlockPos(context, "sourceBlock");
            ServerWorld world = context.getSource().getWorld();

            Inventory inventoryAt = HopperBlockEntity.getInventoryAt(world, sourceBlock);

            if (inventoryAt != null) {
                sourceInventory = inventoryAt;
            } else {
                sourceInventory = new FloatingItemInventory(world, sourceBlock);
            }
        }

        if (target == null) {
            Collection<? extends Entity> sourceEntities = EntityArgumentType.getEntities(context, "targetEntity");
            if (sourceEntities.size() != 1) {
                context.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.target.multiple"));
                return 0;
            }
            Entity entity = sourceEntities.iterator().next();
            if (entity instanceof Inventory) {
                targetInventory = (Inventory) entity;
            } else {
                context.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.target.no"));
                return 0;
            }

        } else {
            BlockPos sourceBlock = BlockPosArgumentType.getBlockPos(context, "targetBlock");
            ServerWorld world = context.getSource().getWorld();

            Inventory inventoryAt = HopperBlockEntity.getInventoryAt(world, sourceBlock);

            if (inventoryAt != null) {
                targetInventory = inventoryAt;
            } else {
                targetInventory = new FloatingItemInventory(world, sourceBlock);
            }
        }

        do {
            ItemStack moved = ItemStack.EMPTY;
            int movedSlot = 0;
            for (int i = 0; i < sourceInventory.getInvSize(); i++) {
                ItemStack invStack = sourceInventory.getInvStack(i);
                if (itemPredicate.test(invStack)) {
                    moved = invStack;
                    movedSlot = i;
                    break;
                }
            }

            if (moved == ItemStack.EMPTY) {
                //no item to move
                return 1;
            }

            if (sourceInventory instanceof SidedInventory) {
                if (!((SidedInventory) sourceInventory).canExtractInvStack(movedSlot, moved, source)) {
                    context.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.source.cant"));
                    return 0;
                } else {
                    amount = moveitem(moved, amount, sourceInventory, targetInventory, target);
                }
            } else {
                amount = moveitem(moved, amount, sourceInventory, targetInventory, target);
            }

        } while (amount != 0);


        return 1;
    }

    int moveitem(ItemStack moved, int amount, Inventory sourceInventory, Inventory targetInventory, Direction target) {
        ItemStack copy = moved.copy();

        int transferred;
        if (amount > 64) {
            transferred = 64;
            amount -= 64;
        } else {
            transferred = amount;
            amount = 0;
        }

        if (moved.getCount() >= transferred) {
            moved.setCount(moved.getCount() - transferred);
            copy.setCount(transferred);
        } else {
            copy.setCount(moved.getCount());
            moved.setCount(0);
        }

        HopperBlockEntity.transfer(sourceInventory, targetInventory, copy, target);
        return amount;
    }
}
