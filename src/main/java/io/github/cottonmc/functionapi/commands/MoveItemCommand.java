package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.*;
import com.mojang.brigadier.arguments.*;
import io.github.cottonmc.functionapi.api.content.block.enums.*;
import io.github.cottonmc.functionapi.commands.arguments.*;
import net.minecraft.command.argument.*;
import net.minecraft.server.command.*;

public class MoveItemCommand{

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(
        CommandManager.literal("moveitem")
        .then(CommandManager.argument("amount",IntegerArgumentType.integer(1,256))
        .then(CommandManager.argument("sourceblock", BlockPosArgumentType.blockPos())
        .then(CommandManager.argument("sourcedirection", new EnumArgumentType(Direction.class))
        .then(CommandManager.argument("targetblock", BlockPosArgumentType.blockPos())
        .then(CommandManager.argument("targetdirection", new EnumArgumentType(Direction.class))
        .executes(context -> {
            return 1;
        })))))
        .then(CommandManager.argument("sourceblock", BlockPosArgumentType.blockPos())
        .then(CommandManager.argument("sourcedirection", new EnumArgumentType(Direction.class))
        .then(CommandManager.argument("targetentity", EntityArgumentType.entities())
        .executes(context -> {
            return 1;
        }))))
        .then(CommandManager.argument("sourceentity", EntityArgumentType.entity())
        .then(CommandManager.argument("targetentity", EntityArgumentType.entity())
        .executes(context -> {
            return 1;
        })))
        .then(CommandManager.argument("sourceentity", EntityArgumentType.entity())
        .then(CommandManager.argument("targetblock", BlockPosArgumentType.blockPos())
        .then(CommandManager.argument("targetdirection", new EnumArgumentType(Direction.class))
        .executes(context -> {
            return 1;
        }))))
        ));
    }
}
