package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.Item;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

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
