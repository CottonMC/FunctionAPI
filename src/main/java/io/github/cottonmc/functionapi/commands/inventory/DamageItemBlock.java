package io.github.cottonmc.functionapi.commands.inventory;

import com.mojang.brigadier.context.CommandContext;
import io.github.cottonmc.functionapi.api.CommandWithArgument;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

public class DamageItemBlock implements CommandWithArgument<Direction> {

    public static final CommandWithArgument INSTANCE = new DamageItemBlock();

    private DamageItemBlock(){}

    @Override
    public int execute(CommandContext<ServerCommandSource> context, Direction direction) {
        return 1;
    }
}
