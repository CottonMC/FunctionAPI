package io.github.cottonmc.functionapi.content.commands.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.IncludeCommandRunner;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.content.commands.arguments.FunctionAPIIdentifierArgumentType;


public class IncludeCommand {

    public static void register(CommandDispatcher<IncludeCommandRunner> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<IncludeCommandRunner>literal("include")
                .then(RequiredArgumentBuilder.<IncludeCommandRunner, FunctionAPIIdentifier>argument("id", FunctionAPIIdentifierArgumentType.identifier())
                        .executes(context -> {
                            FunctionAPIIdentifier functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier.class);
                            context.getSource().runCommand(functionAPIIdentifier);
                            return 1;
                        })
                ));
    }

}
