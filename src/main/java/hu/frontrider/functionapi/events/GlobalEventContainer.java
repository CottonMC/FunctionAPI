package hu.frontrider.functionapi.events;

import hu.frontrider.functionapi.events.internal.ServerTarget;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.*;

/**
 * Stores a reference to every event handler, so we can never duplicate them.
 */
public class GlobalEventContainer {

    private static GlobalEventContainer INSTANCE;

    private Map<Identifier, EventManager> eventManagerMap;

    private boolean disabled = false;

    private GlobalEventContainer() {
        eventManagerMap = new HashMap<>();
    }

    public static GlobalEventContainer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GlobalEventContainer();

        return INSTANCE;
    }

    /**
     * Adds a new event manager to the system.
     * */
    public void addManager(EventManager eventManager) {
        Identifier eventManagerID = eventManager.getID();
        eventManagerMap.put(eventManagerID, eventManager);
        eventManager.markDirty();
        if(this.disabled){
            eventManager.disable();
        }
    }

    /**
     * Runs the initializtion callback for the event.
     * */
    public void initCallback(Identifier eventManagerID, MinecraftServer server){
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
        this.disabled = true;
    }

    public void enableAll() {
        eventManagerMap.values().parallelStream().forEach(EventManager::enable);
        this.disabled = false;
    }

    public void markDirty() {
        eventManagerMap.values().forEach(EventManager::markDirty);
    }

    public void clean(){
        eventManagerMap.clear();
    }
    public boolean containsEvent(Identifier eventID){
        return eventManagerMap.containsKey(eventID);
    }

    public EventManager addIfMissing(EventManager eventManager) {
        if(!containsEvent(eventManager.getID())){
            addManager(eventManager);
            return eventManager;
        }
        return eventManagerMap.get(eventManager.getID());
    }
}
