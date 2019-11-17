package io.github.cottonmc.functionapi.api.commands;

import com.mojang.brigadier.context.CommandContext;

@FunctionalInterface
public interface CommandWithArgument<T,A> {
    int execute(CommandContext<T> context, A argument);
}
