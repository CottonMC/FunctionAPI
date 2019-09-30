package hu.frontrider.functionapi.events;

import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.ServerCommandSourceFactory;
import hu.frontrider.functionapi.events.internal.ServerTarget;
import hu.frontrider.functionapi.events.runners.service.EventHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Stores a reference to every event handler, so we can never duplicate them.
 */
public class GlobalEventContainer {

    private static GlobalEventContainer INSTANCE;

    private Map<Identifier, EventManager> eventManagerMap;

    private GlobalEventContainer() {
        eventManagerMap = new HashMap<>();
    }

    public static GlobalEventContainer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GlobalEventContainer();

        return INSTANCE;
    }

    public void addManager(EventManager eventManager) {
        Identifier eventManagerID = eventManager.getID();
        eventManagerMap.put(eventManagerID, eventManager);
        eventManager.markDirty();
    }

    public void callbackInit(Identifier eventManagerID,MinecraftServer server){
        Identifier callbackID = new Identifier(eventManagerID.getNamespace(), eventManagerID.getPath()+"_create_callback");
        EventManager managerCallback = new EventManager(new ServerTarget(), callbackID);
        managerCallback.fire(server.getCommandSource());
    }

    public EventManager getManager(Identifier identifier) {
        return eventManagerMap.get(identifier);
    }

    public Set<Identifier> getEventNames() {
        return eventManagerMap.keySet();
    }

    public boolean enableEvent(Identifier identifier) {
        EventManager eventManager = eventManagerMap.get(identifier);
        if (eventManager == null)
            return false;

        eventManager.enable();
        return true;
    }

    public boolean disableEvent(Identifier identifier) {
        EventManager eventManager = eventManagerMap.get(identifier);
        if (eventManager == null)
            return false;

        eventManager.disable();
        return true;
    }

    public void disableAll() {
        eventManagerMap.values().parallelStream().forEach(EventManager::disable);
    }

    public void enableAll() {
        eventManagerMap.values().parallelStream().forEach(EventManager::enable);
    }

    public void markDirty() {
        eventManagerMap.values().forEach(EventManager::markDirty);
    }

    public void runEvent(ScriptedObject target, String event, ServerCommandSource commandContext) {
        EventManager eventManager = new EventManager(target, event);
        EventManager eventManager1 = eventManagerMap.get(eventManager.getID());
        if(eventManager1 == null){
            addManager(eventManager);
            eventManager.serverInit(commandContext.getMinecraftServer());
            eventManager.fire(commandContext);
        }else{
            eventManager1.fire(commandContext);
        }
    }
}
