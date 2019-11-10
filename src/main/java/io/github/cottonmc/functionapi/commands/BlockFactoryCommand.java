package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.arguments.BlockPosArgumentType;
import net.minecraft.command.arguments.PosArgument;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Direction;

public class BlockFactoryCommand {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher_1) {
        commandDispatcher_1.register(
                CommandManager.literal("functionapi_debug")
                        .requires((serverCommandSource_1) -> serverCommandSource_1.hasPermissionLevel(3))
                        .then(CommandManager.literal("printtype")
                                .executes(commandContext -> {
                                    ServerCommandSource source = commandContext.getSource();
                                    ServerPlayerEntity player = source.getPlayer();
                                    if (player != null) {
                                        Class<? extends Item> aClass = player.inventory.main.get(0).getItem().getClass();
                                        source
                                                .sendFeedback(new LiteralText(aClass.getCanonicalName()), true);
                                    }
                                    return 1;
                                }))

        );
    }


}
