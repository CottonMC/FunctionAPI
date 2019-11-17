package io.github.cottonmc.functionapi.events.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.commands.CommandWithArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

public class ConsumeItemBlock implements CommandWithArgument<ServerCommandSource,Direction> {

    public static final CommandWithArgument INSTANCE = new ConsumeItemBlock();

    private ConsumeItemBlock(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction direction) {
        return 1;
    }
}
