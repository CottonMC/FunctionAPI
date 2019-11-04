package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;

public class PrintCommand {

    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<ContentRegistrationContext>literal("print")
                .then(RequiredArgumentBuilder.<ContentRegistrationContext,String>argument("message", StringArgumentType.string())
                        .executes(context -> {
                            String message = StringArgumentType.getString(context, "message");
                            System.out.println(message);
                            return 1;
                        })
                ));
    }
}
