package io.github.cottonmc.functionapi.events.commands;

import com.mojang.brigadier.*;
import com.mojang.brigadier.suggestion.*;
import io.github.cottonmc.functionapi.*;
import io.github.cottonmc.functionapi.api.*;
import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.events.*;
import net.fabricmc.loader.api.*;
import net.minecraft.command.argument.*;
import net.minecraft.server.command.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EventCommand{

    public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (commandContext_1, suggestionsBuilder_1) -> {
        for(FunctionAPIIdentifier identifier : GlobalEventContainer.getInstance().getEventNames()){
            String s = identifier.toString();
            suggestionsBuilder_1 = suggestionsBuilder_1.suggest(s);
        }
        return suggestionsBuilder_1.buildFuture();
    };

    private static Map<String, List<String>> eventDocs;

    //allows us to run events on their own, out of context
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher_1){
        GlobalEventContainer globalEventContainer = GlobalEventContainer.getInstance();
        commandDispatcher_1.register(
        CommandManager.literal("event")
        .requires((serverCommandSource_1) -> serverCommandSource_1.hasPermissionLevel(2))
        .then(CommandManager.literal("cancel")
        .executes(context -> {
            ((CommandSourceExtension)context.getSource()).setCancelled(true);
            return 1;
        }))
        .then(CommandManager.literal("pass")
        .executes(context -> {
            ((CommandSourceExtension)context.getSource()).setCancelled(false);
            return 1;
        }))
        .then(CommandManager.literal("clear")
        .executes(commandContext -> {
            globalEventContainer.clean();
            commandContext.getSource()
            .sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.cleared"), true);
            return 1;
        }))
        .then(CommandManager.literal("run")
        .then(CommandManager.argument("name", IdentifierArgumentType.identifier())
        .suggests(SUGGESTION_PROVIDER)
        .executes((commandContext) -> {
            try{
                ServerCommandSource commandSource = commandContext.getSource();

                globalEventContainer.executeEvent((FunctionAPIIdentifier)commandContext.getArgument("name", Identifier.class), commandSource);

            }catch(NullPointerException e){
                commandContext.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.no_event_found"));
            }catch(Exception e){
                e.printStackTrace();
                return -1;
            }
            return 1;
        })))
        .then(CommandManager.literal("list")
        .executes((commandContext) -> {

            for(FunctionAPIIdentifier identifier : globalEventContainer.getEventNames()){
                commandContext.getSource()
                .sendFeedback(new LiteralText(identifier.toString()), true);
            }

            return 1;
        }))
        .then(CommandManager.literal("info")
        .then(CommandManager.argument("name", IdentifierArgumentType.identifier())
        .suggests(SUGGESTION_PROVIDER)
        .executes((context) -> {
            if(eventDocs == null){
                eventDocs = new HashMap<>();
                Path referencePath = FabricLoader.getInstance().getModContainer(FunctionAPI.MODID).get().getPath("/eventReference.csv");
                try{
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(referencePath.toFile()));
                    bufferedReader.lines().forEach(
                    (line) -> {
                        String[] split = line.split(";");
                        List<String> strings = Arrays.asList(split);
                        eventDocs.put(split[0] + "/" + split[1], strings);
                    }
                    );

                }catch(FileNotFoundException e){
                    System.err.println("Can't find event reference file! Documentation will not be available!");
                    e.printStackTrace();
                }
            }
            if(eventDocs.isEmpty()){
                context.getSource().sendError(new LiteralText("No documentation could be retrieved at the time!"));
                return 0;
            }

            FunctionAPIIdentifier identifier =(FunctionAPIIdentifier) context.getArgument("name", Identifier.class);

            //${params[0]}/\\<name\\>/${params[1]}
            String[] split = identifier.getPath().split("/");
            String id = split[0] + "/" + split[2];
            List<String> docs = eventDocs.getOrDefault(id, eventDocs.get(identifier));

            if(docs == null){
                context.getSource().sendError(new LiteralText("No documentation found for '" + identifier + "'!"));
                return 0;
            }

            String targetID = identifier.getNamespace() + ":" + split[1];
            String placecommand;

            if(docs.get(0).equals("block")){
                placecommand = "setblock " + targetID + " ^~2 ^~ ^~";
            }else{
                if(docs.get(0).equals("entity")){
                    placecommand = "summon " + targetID + " ^~2 ^~ ^~";
                }else{
                    placecommand = "give " + targetID;
                }
            }

            //print some fancy documentation
            context.getSource().getMinecraftServer().getCommandManager().execute(context.getSource(),
            "/tellraw @s [\"\",{\"text\":\"FunctionAPI Event reference:\\n\"}," +
            "{\"text\":\" \\u0020 \\u0020Reference for: \",\"color\":\"green\"}," +
            "{\"text\":\"\\\"" + identifier + "\\\"\\n\"}," +
            "{\"text\":\" \\u0020 \\u0020 type: " + docs.get(0) + "\\n \\u0020 \\u0020 name: " + docs.get(1) + "\\n \\u0020 \\u0020 target object: \"}," +
            "{\"text\":\"" + targetID + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + placecommand + "\"}}," +
            "{\"text\":\"\\n \\u0020 \\u0020 the event contains:" + docs.get(2) + " \\n \\u0020 \\u0020 cancallation: " + docs.get(3) + "\"}]"
            );
            return 1;
        })
        ))
        );
    }
}
