package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.ScriptedObject;
import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all of the entity related events.
 * Used, because tne entity registry does not contain the actual entity instances, and the events themselves are bound to entity types.
 */
public class EntityEventManager {

    private static EntityEventManager INSTANCE = new EntityEventManager();

    /**
     * Stores all of the required entity functions.
     * Indexed by the entity type's identifier, the value is a map of manager that belongs to the requested type, indexed by their respective identifiers for quick access.
     */
    private Map<Identifier, Map<Identifier, EventManager>> events = new HashMap<>();

    public static EntityEventManager getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Fires off entity events, if the event manager is missing, than it is created.
     */
    public void fire(Identifier type, Identifier eventID, ServerCommandSource commandContext) {
        Map<Identifier, EventManager> managerMap = events.computeIfAbsent(eventID, k -> new HashMap<>());

        EventManager eventManager = managerMap.get(eventID);
        if (eventManager == null) {
            eventManager = new EventManager((ScriptedObject) commandContext.getEntity(), eventID);
            managerMap.put(type, eventManager);
        }
        eventManager.fire(commandContext);
    }

    public void fire(Entity target, Identifier eventID) {

        if (!target.world.isClient()) {
            ServerCommandSource serverCommandSource = ServerCommandSourceFactory.INSTANCE.create(target.world.getServer(), (ServerWorld) target.getEntityWorld(), target);
            EntityEventManager.getINSTANCE().fire(((ScriptedObject) target).getID(), eventID, serverCommandSource);
        }
    }

    /**
     * to mark it for reload, we simply clear off everything.
     */
    public void markDirty() {
        events.clear();
    }
}
