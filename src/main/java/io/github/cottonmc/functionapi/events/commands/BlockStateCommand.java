package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import io.github.cottonmc.functionapi.content.templates.state.StringBlockStateValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.AbstractProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Collection;

public class BlockStateCommand {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher_1) {
        commandDispatcher_1.register(
                CommandManager.literal("setstate")
                        .requires((serverCommandSource_1) -> serverCommandSource_1.hasPermissionLevel(2))
                        .then(CommandManager.argument("target", BlockPosArgumentType.blockPos())
                                .then(CommandManager.argument("name", StringArgumentType.string())
                                                .then(directionArgument((context, direction) -> {
                                                    try {
                                                        BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
                                                        String name = StringArgumentType.getString(context, "name");

                                                        ServerWorld world = context.getSource().getWorld();
                                                        BlockState blockState = world.getBlockState(target);
                                                        Collection<Property<?>> properties = blockState.getProperties();
                                                        for (Property<?> property : properties) {
                                                            if (property.getValueType() == Direction.class) {
                                                                if (property.getName().equals(name)) {
                                                                    if (property.getValues().contains(direction)) {
                                                                        world.setBlockState(target, blockState.with(((AbstractProperty<Direction>) property), direction));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (CommandSyntaxException e) {
                                                        e.printStackTrace();
                                                        return 0;
                                                    }
                                                    return 1;
                                                }))
                                        .then(CommandManager.literal("slab")
                                                .then(slabArgument((context, argument) -> {
                                                    try {
                                                        BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
                                                        String name = StringArgumentType.getString(context, "name");

                                                        ServerWorld world = context.getSource().getWorld();
                                                        BlockState blockState = world.getBlockState(target);
                                                        Collection<Property<?>> properties = blockState.getProperties();
                                                        for (Property<?> property : properties) {
                                                            if (property.getValueType() == SlabType.class) {
                                                                if (property.getName().equals(name)) {
                                                                    if (property.getValues().contains(argument)) {
                                                                        world.setBlockState(target, blockState.with(((AbstractProperty<SlabType>) property), argument));
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (CommandSyntaxException e) {
                                                        e.printStackTrace();
                                                        return 0;
                                                    }
                                                    return 1;
                                                }))
                                        )
                                        .then(CommandManager.literal("range")
                                                .then(CommandManager.argument("number", IntegerArgumentType.integer())
                                                        .executes(context -> {
                                                            try {
                                                                BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
                                                                String name = StringArgumentType.getString(context, "name");
                                                                int number = IntegerArgumentType.getInteger(context, "number");
                                                                ServerWorld world = context.getSource().getWorld();
                                                                BlockState blockState = world.getBlockState(target);
                                                                Collection<Property<?>> properties = blockState.getProperties();
                                                                for (Property<?> property : properties) {
                                                                    if (property.getValueType() == Integer.class) {
                                                                        if (property.getName().equals(name)) {
                                                                            if (property.getValues().contains(number)) {
                                                                                world.setBlockState(target, blockState.with(((AbstractProperty<Integer>) property), number));
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } catch (CommandSyntaxException e) {
                                                                e.printStackTrace();
                                                                return 0;
                                                            }
                                                            return 1;
                                                        })
                                                )
                                        )
                                        .then(CommandManager.literal("custom")
                                                .then(CommandManager.argument("value", StringArgumentType.string())
                                                        .executes(context -> {
                                                            try {
                                                                BlockPos target = BlockPosArgumentType.getBlockPos(context, "target");
                                                                String name = StringArgumentType.getString(context, "name");
                                                                String value = StringArgumentType.getString(context, "value");
                                                                ServerWorld world = context.getSource().getWorld();
                                                                BlockState blockState = world.getBlockState(target);
                                                                Collection<Property<?>> properties = blockState.getProperties();
                                                                for (Property<?> property : properties) {
                                                                    if (property.getValueType() == StringBlockStateValue.class) {
                                                                        if (property.getName().equals(name)) {
                                                                            property.getValue(value).ifPresent(val -> world.setBlockState(target, blockState.with(((AbstractProperty<StringBlockStateValue>) property), (StringBlockStateValue) val)));
                                                                        }
                                                                    }
                                                                }
                                                            } catch (CommandSyntaxException e) {
                                                                e.printStackTrace();
                                                                return 0;
                                                            }
                                                            return 1;
                                                        })
                                                )
                                        ))));
    }


    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> directionArgument(CommandWithArgument<ServerCommandSource,Direction> command) {

        LiteralArgumentBuilder<ServerCommandSource> direction = CommandManager
                .literal("direction");
        for (Direction value : Direction.values()) {
            direction = direction.then(CommandManager.literal(value.asString().toLowerCase())
                    .executes(context -> command.execute(context, value))
            );
        }

        return direction;
    }

    private static ArgumentBuilder<ServerCommandSource, LiteralArgumentBuilder<ServerCommandSource>> slabArgument(CommandWithArgument<ServerCommandSource,SlabType> command) {

        LiteralArgumentBuilder<ServerCommandSource> direction = CommandManager
                .literal("slab");
        for (SlabType value : SlabType.values()) {
            direction = direction.then(CommandManager.literal(value.asString().toLowerCase())
                    .executes(context -> command.execute(context, value))
            );
        }

        return direction;
    }
}
