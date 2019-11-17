package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.api.content.enums.ItemType;

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
                .then(typeNode()));

    }

    private static LiteralArgumentBuilder<ContentRegistrationContext> typeNode() {
        LiteralArgumentBuilder<ContentRegistrationContext> type = LiteralArgumentBuilder.literal("type");

        for (ItemType value : ItemType.values()) {
            type.then(LiteralArgumentBuilder.<ContentRegistrationContext>literal(value.name().toLowerCase())
                    .executes(context -> {
                         context.getSource().getItemTemplate().setType(value);
                         return 1;
                    }));
        }

        return type;

    }
}
