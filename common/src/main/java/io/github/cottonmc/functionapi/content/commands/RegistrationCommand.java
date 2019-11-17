package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.api.script.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.content.commands.arguments.FunctionAPIIdentifierArgumentType;


public class RegistrationCommand {
    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {

        commandDispatcher.register(LiteralArgumentBuilder.<ContentRegistrationContext>literal("register")
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("addpostfix")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("postfix", StringArgumentType.word())
                                .executes(context -> {
                                    String postfix = StringArgumentType.getString(context, "postfix");
                                    context.getSource().addPostfix(postfix);
                                    return 1;
                                })
                        ))
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("block")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, FunctionAPIIdentifier>argument("id", FunctionAPIIdentifierArgumentType.identifier())
                                .executes(context -> {
                                    FunctionAPIIdentifier functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier.class);
                                    context.getSource().createElement(ContentRegistrationContext.ContentType.BLOCK, functionAPIIdentifier);
                                    return 1;
                                })
                        ))
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("item")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, FunctionAPIIdentifier>argument("id", FunctionAPIIdentifierArgumentType.identifier())
                                .executes(context -> {
                                    FunctionAPIIdentifier functionAPIIdentifier = context.getArgument("id", FunctionAPIIdentifier.class);
                                    context.getSource().createElement(ContentRegistrationContext.ContentType.ITEM, functionAPIIdentifier);
                                    return 1;
                                })
                        ))
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("variant")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("variant_index", IntegerArgumentType.integer())
                                .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("variant_name", StringArgumentType.word())
                                        .executes(context -> {
                                            String postfix = StringArgumentType.getString(context, "variant_name");
                                            int variant_index = IntegerArgumentType.getInteger(context, "variant_index");
                                            context.getSource().addVariant(postfix,variant_index);
                                            return 1;
                                        }))
                        )))
        ;
    }
}
