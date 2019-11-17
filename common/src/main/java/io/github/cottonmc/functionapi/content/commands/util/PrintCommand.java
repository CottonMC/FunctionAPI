package io.github.cottonmc.functionapi.content.commands.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class PrintCommand {

    public static void register(CommandDispatcher<Object> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.literal("print")
                .then(RequiredArgumentBuilder.argument("message", StringArgumentType.string())
                        .executes(context -> {
                            String message = StringArgumentType.getString(context, "message");
                            System.out.println(message);
                            return 1;
                        })
                ));
    }
}
