package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;
import net.minecraft.command.arguments.IdentifierArgumentType;
import net.minecraft.util.Identifier;

public class IncludeCommand {

    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<ContentRegistrationContext>literal("include")
                .then(RequiredArgumentBuilder.<ContentRegistrationContext, Identifier>argument("id", IdentifierArgumentType.identifier())
                        .executes(context -> {
                            Identifier identifier = context.getArgument("id", Identifier.class);
                            context.getSource().runCommand(identifier);
                            return 1;
                        })
                ));
    }
}
