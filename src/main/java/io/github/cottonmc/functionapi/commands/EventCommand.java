package io.github.cottonmc.functionapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import io.github.cottonmc.functionapi.api.CommandSourceExtension;
import io.github.cottonmc.functionapi.events.EventManager;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import io.github.cottonmc.functionapi.events.Target;
import net.minecraft.command.arguments.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class EventCommand {

    public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (commandContext_1, suggestionsBuilder_1) -> {
        for (Identifier identifier : GlobalEventContainer.getInstance().getEventNames()) {
            String s = identifier.toString();
            suggestionsBuilder_1 = suggestionsBuilder_1.suggest(s);
        }
        return suggestionsBuilder_1.buildFuture();
    };

    //allows us to run events on their own, out of context
    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher_1) {
        commandDispatcher_1.register(
                CommandManager.literal("event")
                        .requires((serverCommandSource_1) -> serverCommandSource_1.hasPermissionLevel(2))
                        .then(CommandManager.literal("cancel")
                        .executes(context -> {
                            ((CommandSourceExtension)context.getSource()).cancel();
                            return 1;
                        }))
                        .then(CommandManager.literal("clear")
                                .executes(commandContext -> {
                                    GlobalEventContainer.getInstance().clean();
                                    commandContext.getSource()
                                            .sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.cleared"), true);
                                    return 1;
                                }))
                        .then(CommandManager.literal("run")
                                .then(CommandManager.argument("name", new IdentifierArgumentType())
                                        .suggests(SUGGESTION_PROVIDER)
                                        .executes((commandContext) -> {
                                            try {
                                                ServerCommandSource commandSource = commandContext.getSource();
                                                EventManager manager = GlobalEventContainer.getInstance().getManager(commandContext.getArgument("name", Identifier.class));

                                                manager.fire(commandSource);
                                            } catch (NullPointerException e) {
                                                commandContext.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.no_event_found"));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                return -1;
                                            }
                                            return 1;
                                        })))
                        .then(CommandManager.literal("list")
                                .executes((commandContext) -> {

                                    for (Identifier identifier : GlobalEventContainer.getInstance().getEventNames()) {
                                        commandContext.getSource()
                                                .sendFeedback(new LiteralText(identifier.toString()), true);
                                    }

                                    return 1;
                                }))
                        .then(CommandManager.literal("enable")
                                .executes(commandContext -> {
                                    GlobalEventContainer.getInstance().enableAll();
                                    commandContext.getSource().sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.all_events_enabled"), true);

                                    return 1;
                                })
                                .then(CommandManager.argument("name", new IdentifierArgumentType())
                                        .suggests(SUGGESTION_PROVIDER)
                                        .executes((commandContext) -> {
                                            Identifier name = commandContext.getArgument("name", Identifier.class);
                                            if (GlobalEventContainer.getInstance().enableEvent(name)) {
                                                commandContext.getSource()
                                                        .sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.event_enabled"), true);
                                            } else {
                                                commandContext.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.no_event_found"));
                                            }
                                            return 1;
                                        })))
                        .then(CommandManager.literal("disable")
                                .executes(commandContext -> {
                                    GlobalEventContainer.getInstance().disableAll();
                                    commandContext.getSource().sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.all_events_disabled"), true);

                                    return 1;
                                })
                                .then(CommandManager.argument("name", new IdentifierArgumentType())
                                        .suggests(SUGGESTION_PROVIDER)
                                        .executes((commandContext) -> {
                                            Identifier name = commandContext.getArgument("name", Identifier.class);
                                            if (GlobalEventContainer.getInstance().disableEvent(name)) {
                                                commandContext.getSource()
                                                        .sendFeedback(new TranslatableText("hu.frontrider.functionapi.command.event_disabled"), true);

                                            } else {
                                                commandContext.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.event_exists"));
                                            }
                                            return 1;
                                        })))
                        .then(CommandManager.literal("create")
                                .then(CommandManager.argument("target name", new IdentifierArgumentType())
                                        .then(CommandManager.argument("eventName", StringArgumentType.word())
                                                .executes((commandContext_1) -> {
                                                    Identifier group = commandContext_1.getArgument("target name", Identifier.class);
                                                    String eventName = commandContext_1.getArgument("eventName", String.class);

                                                    Target dummyTarget = new Target(group, "api");
                                                    EventManager eventManager = new EventManager(dummyTarget, eventName);

                                                    EventManager manager = GlobalEventContainer.getInstance().getManager(eventManager.getID());
                                                    //if it exists, then we send a message.
                                                    if (manager != null) {
                                                        commandContext_1.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.event_exists"));
                                                        return 1;
                                                    }
                                                    //if it does not, the we add it.
                                                    GlobalEventContainer.getInstance().addManager(eventManager);
                                                    eventManager.serverInit(commandContext_1.getSource().getMinecraftServer());

                                                    return 1;
                                                }).then(CommandManager.argument("type", StringArgumentType.word())
                                                        .executes(commandContext_1 -> {
                                                            Identifier group = commandContext_1.getArgument("target name", Identifier.class);
                                                            String eventName = commandContext_1.getArgument("eventName", String.class);
                                                            String type = commandContext_1.getArgument("type", String.class);


                                                            Target dummyTarget = new Target(group, type);

                                                            EventManager eventManager = new EventManager(dummyTarget, eventName);

                                                            EventManager manager = GlobalEventContainer.getInstance().getManager(eventManager.getID());
                                                            //if it exists, then we send a message.
                                                            if (manager != null) {
                                                                commandContext_1.getSource().sendError(new TranslatableText("hu.frontrider.functionapi.command.event_exists"));
                                                                return 1;
                                                            }
                                                            //if it does not, the we add it.
                                                            GlobalEventContainer.getInstance().addManager(eventManager);
                                                            eventManager.serverInit(commandContext_1.getSource().getMinecraftServer());

                                                            return 1;
                                                        }))
                                        ))
                        )
        );
    }
}
