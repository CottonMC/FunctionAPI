package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.commands.BiDirectionalCommand;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import io.github.cottonmc.functionapi.events.commands.inventory.*;
import net.minecraft.command.arguments.*;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

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
public class InventoryCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("inventory")
                        .then(CommandManager.argument("amount", IntegerArgumentType.integer())
                                .then(CommandManager.literal("move")
                                        .then(buildSourceBlockMoveArgument(MoveItem.UNFILTERED))
                                        .then(buildSourceEntityMoveArgument(MoveItem.UNFILTERED))
                                        .then(CommandManager.argument("item", ItemPredicateArgumentType.itemPredicate())
                                                .then(buildSourceBlockMoveArgument(MoveItem.FILTERED))
                                                .then(buildSourceEntityMoveArgument(MoveItem.FILTERED))
                                        )
                                )
                                .then(CommandManager.literal("consume")
                                        .then(CommandManager.argument("item", ItemPredicateArgumentType.itemPredicate())
                                                .then(buildSourceBlockUseArgument( ConsumeItemBlock.INSTANCE))
                                                .then(buildSourceEntityUseArgument(ConsumeItemEntity.INSTANCE))
                                        )
                                )
                                .then(CommandManager.literal("damage")
                                        .then(CommandManager.argument("item", ItemPredicateArgumentType.itemPredicate())
                                                .then(buildSourceBlockUseArgument(DamageItemBlock.INSTANCE))
                                                .then(buildSourceEntityUseArgument(DamageItemEntity.INSTANCE))
                                        )
                                )
                        )
        );
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> buildTargetBlockMoveArgument(BiDirectionalCommand command, Direction sourceDirection) {
        RequiredArgumentBuilder<ServerCommandSource, PosArgument> targetBlock = CommandManager.argument("targetBlock", BlockPosArgumentType.blockPos());

        for (Direction value : Direction.values()) {
            targetBlock = targetBlock.then(CommandManager.literal(value.asString().toLowerCase())
                    .executes(context -> command.execute(context, sourceDirection, value))
            );
        }

        return CommandManager
                .literal("block")
                .then(targetBlock);
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> buildSourceBlockMoveArgument(BiDirectionalCommand command) {
        RequiredArgumentBuilder<ServerCommandSource, PosArgument> sourceBlock = CommandManager.argument("sourceBlock", BlockPosArgumentType.blockPos());


        for (Direction value : Direction.values()) {
            sourceBlock = sourceBlock.then(CommandManager.literal(value.asString().toLowerCase())
                    .then(buildTargetBlockMoveArgument(command, value))
                    .then(CommandManager.literal("entity")
                            .then(CommandManager.argument("targetEntity", EntityArgumentType.entities())
                                    .executes(context -> command.execute(context, value, null))
                            )
                    )
            );

        }

        return CommandManager
                .literal("block").then(sourceBlock);
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> buildSourceBlockUseArgument(CommandWithArgument<ServerCommandSource,Direction> command) {
        RequiredArgumentBuilder<ServerCommandSource, PosArgument> sourceBlock = CommandManager.argument("sourceBlock", BlockPosArgumentType.blockPos());

        for (Direction value : Direction.values()) {
            sourceBlock = sourceBlock.then(CommandManager.literal(value.asString().toLowerCase())
                    .then(CommandManager.argument("eventName", new IdentifierArgumentType())
                            .suggests(EventCommand.SUGGESTION_PROVIDER)
                            .executes(context -> command.execute(context, value)))
                    .executes(context -> command.execute(context, value))
            );
        }

        return CommandManager
                .literal("block")
                .then(sourceBlock);
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> buildSourceEntityMoveArgument(BiDirectionalCommand<ServerCommandSource,Direction> command) {
        return CommandManager.literal("entity")
                .then(CommandManager.argument("sourceEntity", EntityArgumentType.entities())
                        .then(CommandManager.literal("entity")
                                .then(CommandManager.argument("targetEntity", EntityArgumentType.entities())
                                        .executes(context -> command.execute(context, null, null))
                                )));
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> buildSourceEntityUseArgument(Command<ServerCommandSource> command) {
        return CommandManager.literal("entity")
                .then(CommandManager.argument("sourceEntity", EntityArgumentType.entities())
                        .then(CommandManager.argument("eventName", new IdentifierArgumentType())
                                .suggests(EventCommand.SUGGESTION_PROVIDER)
                                .executes(command))
                        .executes(command));
    }


}
