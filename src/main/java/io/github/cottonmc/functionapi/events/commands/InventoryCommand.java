package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.events.commands.inventory.*;
import io.github.cottonmc.functionapi.script.commandtemplates.InventoryCommandTemplate;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.arguments.*;
import net.minecraft.server.command.ServerCommandSource;

/**
 * Command that manipulates inventories while respecting their properties (unlike the data command)
 * Syntax:
 * inventory amount(int)
 *         -> move block/entity block/entity selector(source) block/entity block/entity selector(target)
 *         -> item(an item argument)
 *                  -> move block/entity block/entity selector(source) block/entity block/entity selector(target)
 *                  -> damage block/entity block/entity selector(source) eventID
 *                  -> consume block/entity block/entity selector(source) eventID
 *
 *  damage and consume fires off an event, if the target item could be damaged or consumed
 *  move without a filter takes the first available item.
 *  if there is no block with an inventory, then we'll use all of the floating items in that position.
 * */
public class InventoryCommand{}/* extends InventoryCommandTemplate<ServerCommandSource, PosArgument, EntitySelector, ItemPredicateArgumentType.ItemPredicateArgument> {

    public InventoryCommand() {
        super(MoveItem.FILTERED, MoveItem.UNFILTERED, ConsumeItemBlock.INSTANCE, DamageItemBlock.INSTANCE,DamageItemEntity.INSTANCE, ConsumeItemEntity.INSTANCE);
    }

    @Override
    protected ArgumentType<PosArgument> blockPosArgumentType() {
        return BlockPosArgumentType.blockPos();
    }

    @Override
    protected ArgumentType<EntitySelector> getEntityArgumentType() {
        return EntityArgumentType.entities();
    }

    @Override
    protected ArgumentType<ItemPredicateArgumentType.ItemPredicateArgument> getitemPredicateArgumentType() {
        return ItemPredicateArgumentType.itemPredicate();
    }


}
*/