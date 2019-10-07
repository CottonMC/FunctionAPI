package io.github.cottonmc.functionapi.events.runners.service;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.lang.annotation.Annotation;

/**
 * This is the interface that the service runner loads as java services.
 */
public interface EventHandler {

    /**
     * called when the event is fired.
     *
     * @param serverCommandSource stores context information about the event
     */
    void fire(ServerCommandSource serverCommandSource);

    default void fire(MinecraftServer server){
        ServerCommandSource commandSource = server.getCommandSource();
        fire(commandSource);
    }

    /**
     * Used when selecting which event the handler is hooked onto.
     * The reason for this structure, is to allow for one handler to hook into multiple events.
     * <p>
     * by default it will use the annotation.
     *
     * @param identifier the ID of the event that this handler might hook into
     * @return true, if this handler does hook into this event.
     */
    default boolean matchesEvent(Identifier identifier) {
        Annotation[] annotations = getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == EventTarget.class) {
                String value = ((EventTarget) annotation).value();
                return new Identifier(value).equals(identifier);
            }
        }
        return false;
    }
}