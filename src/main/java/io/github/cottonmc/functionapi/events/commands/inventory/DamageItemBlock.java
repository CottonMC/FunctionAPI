package io.github.cottonmc.functionapi.events.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import io.github.cottonmc.functionapi.api.content.enums.Direction;
import net.minecraft.server.command.ServerCommandSource;

public class DamageItemBlock implements CommandWithArgument<ServerCommandSource, io.github.cottonmc.functionapi.api.content.enums.Direction> {

    public static final CommandWithArgument INSTANCE = new DamageItemBlock();

    private DamageItemBlock(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction direction) {
        return 1;
    }
}
