package io.github.cottonmc.functionapi.content;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.cottonmc.functionapi.FunctionAPI;
import io.github.cottonmc.staticdata.StaticData;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StaticCommandExecutor<S> {

    private final String dirname;
    private final BiFunction<Map<Identifier, String[]>, CommandDispatcher<S>, S> contextFactory;
    private final Consumer<S> registrationHandler;
    private CommandDispatcher<S> commandDispatcher;

    public StaticCommandExecutor(String dirname, BiFunction<Map<Identifier, String[]>, CommandDispatcher<S>, S> contextFactory, Consumer<S> registrationHandler) {
        this.dirname = dirname;
        this.contextFactory = contextFactory;
        this.registrationHandler = registrationHandler;
        commandDispatcher = new CommandDispatcher<>();
    }

    public void register(Consumer<CommandDispatcher<S>> command) {
        command.accept(commandDispatcher);
    }

    public void execute() {

        Map<Identifier, String[]> commands = new FileTemplateHelper<>(
                (file) -> new BufferedReader(new FileReader(file)).lines().collect(Collectors.toList()).toArray(new String[]{}),
                DirectoryManager.getINSTANCE().getContentFolder(),
                "mccontent"
        ).getTemplates();

        StaticData
                .getAllInDirectory(FunctionAPI.MODID + "/" + dirname)
                .stream()
                .map(staticDataItem -> {
                    try {
                        String[] lines = staticDataItem.getAsString().split("\n+");
                        for (int i = 0; i < lines.length; i++) {
                            lines[i] = lines[i].trim();
                        }
                        Identifier identifier = staticDataItem.getIdentifier();

                        return new ImmutablePair<>(new Identifier(identifier.getNamespace(), identifier.getPath().replaceAll("^" + FunctionAPI.MODID + "/" + dirname + "/", "").replaceAll("\\.mccontent$", "")), lines);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .forEach(identifierImmutablePair -> commands.put(identifierImmutablePair.getLeft(), identifierImmutablePair.right));

        System.out.println("Function API starting content registration");
        commands.forEach((identifier, strings) -> {
            S context = contextFactory.apply(commands, commandDispatcher);
            if (identifier.getPath().split("/").length == 1) {
                executeFile(commandDispatcher,strings,identifier,context);
                registrationHandler.accept(context);
            }

        });
        System.out.println("Function API finished content registration");
    }

    public static void executeFile(CommandDispatcher commandDispatcher,String[] strings, Identifier identifier,Object context){

        int lineNumber =1;
        try {

                //running each line
                System.out.println("executing file: "+identifier);
                for (String line : strings) {
                    lineNumber +=1;
                    System.out.println("executing command: "+line);
                    System.out.println("at line "+lineNumber);
                    if (!line.startsWith("#") && !line.isEmpty()) {
                        commandDispatcher.execute(line, context);
                    }
                }
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
    };
}
