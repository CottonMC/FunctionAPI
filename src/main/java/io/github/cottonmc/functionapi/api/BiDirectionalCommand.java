package io.github.cottonmc.functionapi.api;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Direction;

@FunctionalInterface
public interface BiDirectionalCommand {
    int execute(CommandContext<ServerCommandSource> context, Direction source, Direction target) throws CommandSyntaxException;
}
