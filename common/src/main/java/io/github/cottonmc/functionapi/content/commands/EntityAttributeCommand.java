package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;

public class EntityAttributeCommand {

    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {

        LiteralArgumentBuilder<ContentRegistrationContext> addentityAttribute = LiteralArgumentBuilder.literal("entityattribute");



    }

}
