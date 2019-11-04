package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.content.ContentRegistrationContext;

public class ItemSettingsCommand {

    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {
        commandDispatcher.register(LiteralArgumentBuilder.<ContentRegistrationContext>literal("itemsettings")
                .then(LiteralArgumentBuilder.<ContentRegistrationContext>literal("stacksize")
                        .then(RequiredArgumentBuilder.<ContentRegistrationContext, Integer>argument("amount", IntegerArgumentType.integer())
                                .executes(context -> {
                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                    context.getSource().getItemTemplate().setMaxCount(amount);
                                    return 1;
                                })))
        );
    }


}
