package io.github.cottonmc.functionapi.api;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

@FunctionalInterface
public interface CommandWithArgument<T> {
    int execute(CommandContext<ServerCommandSource> context, T argument);
}
