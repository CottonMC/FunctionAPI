package io.github.cottonmc.functionapi.script;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.api.content.CommandFileSource;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StaticCommandExecutor<S,T> {

    private final Function<CommandDispatcher<S>, S> contextFactory;

    public CommandFileSource<T> getCommandFileSource() {
        return commandFileSource;
    }

    private final CommandFileSource<T> commandFileSource;
    private CommandDispatcher<S> commandDispatcher;

    public StaticCommandExecutor(CommandFileSource<T> commandFileSource, Function<CommandDispatcher<S>, S> contextFactory) {
        this.commandFileSource = commandFileSource;
        this.contextFactory = contextFactory;
        commandDispatcher = new CommandDispatcher<>();
    }

    public void register(Consumer<CommandDispatcher<S>> command) {
        command.accept(commandDispatcher);
    }

    public void execute(BiConsumer<CommandFileSource<T>,CommandDispatcher<S>> registrationHandler) {
        System.out.println("Function API starting content registration");
        registrationHandler.accept(commandFileSource,commandDispatcher);
        System.out.println("Function API finished content registration");
    }

    public ParseResults<S>parseCommand(String command,S source){
        return commandDispatcher.parse(command.trim(), source);
    }

    public List<ParseResults<S>> parseFile(List<String> commands, S source){
        return commands.stream().map(command->parseCommand(command,source)).collect(Collectors.toList());
    }

    public void execute(List<ParseResults<S>> commands,S context) throws CommandSyntaxException {
        for (ParseResults<S> command : commands) {
            commandDispatcher.execute(new ParseResults<S>(command.getContext().withSource(context), command.getReader(), command.getExceptions()));
        }
    }

    public CommandDispatcher<S> getCommandDispatcher() {
        return commandDispatcher;
    }
}
