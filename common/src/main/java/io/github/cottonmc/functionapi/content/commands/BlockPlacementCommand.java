package io.github.cottonmc.functionapi.content.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import io.github.cottonmc.functionapi.api.content.ContentRegistrationContext;
import io.github.cottonmc.functionapi.api.content.enums.PlacementDirection;
import io.github.cottonmc.functionapi.api.content.enums.PlacementPosition;
import io.github.cottonmc.functionapi.content.PlacementMapping;


public class BlockPlacementCommand {

    public static void register(CommandDispatcher<ContentRegistrationContext> commandDispatcher) {
        LiteralArgumentBuilder<ContentRegistrationContext> blockplacement = LiteralArgumentBuilder.literal("blockplacement");

        for (PlacementDirection direction : PlacementDirection.values()) {
            LiteralArgumentBuilder<ContentRegistrationContext> sideBuilder = LiteralArgumentBuilder.literal(direction.name().toLowerCase());

            for (PlacementDirection facingDirection : PlacementDirection.values()) {

                LiteralArgumentBuilder<ContentRegistrationContext> placementDirection = LiteralArgumentBuilder.literal(facingDirection.name().toLowerCase());

                for (PlacementPosition placementPosition : PlacementPosition.values()) {
                    placementDirection = placementDirection.then(LiteralArgumentBuilder.<ContentRegistrationContext>literal(placementPosition.name().toLowerCase())
                            .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("name", StringArgumentType.string())
                                    .then(RequiredArgumentBuilder.<ContentRegistrationContext, String>argument("value", StringArgumentType.string())
                                            .executes(context -> {
                                                String name = StringArgumentType.getString(context, "name");

                                                ContentRegistrationContext source = context.getSource();
                                                String stateValue = StringArgumentType.getString(context, "value");

                                                source.getBlockTemplate().addPlacementState(new PlacementMapping(name,stateValue,direction,placementPosition,facingDirection));

                                                return 1;
                                            })
                                    )));
                }
                sideBuilder = sideBuilder.then(placementDirection);
            }
            blockplacement = blockplacement.then(sideBuilder);
        }
        commandDispatcher.register(blockplacement);
    }
}
