package io.github.cottonmc.functionapi.api.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;


@FunctionalInterface
public interface BiDirectionalCommand<T,A> {
    int execute(CommandContext<T> context, A source, A target) throws CommandSyntaxException;
}
