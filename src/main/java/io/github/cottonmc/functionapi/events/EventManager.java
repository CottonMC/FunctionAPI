package io.github.cottonmc.functionapi.events;

import io.github.cottonmc.functionapi.api.ScriptedObject;
import io.github.cottonmc.functionapi.events.runners.EventRunner;
import io.github.cottonmc.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerTask;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * Handles an event
 */
public class EventManager {

    /**
     * The different runners allow us to hook into the event with different techniques.
     * Runners are build by factories, that are loaded as java services.
     */
    private static final LinkedList<EventRunnerFactory> eventRunnerFactories = new LinkedList<>();

    static {
        ServiceLoader<EventRunnerFactory> runnerFactories = ServiceLoader.load(EventRunnerFactory.class);
        runnerFactories.iterator().forEachRemaining(eventRunnerFactory -> {
            if (eventRunnerFactory.enabled())
                eventRunnerFactories.add(eventRunnerFactory);
        });
    }

    //the object that the event targets
    private final ScriptedObject target;
    /*the id of the event*/
    private final Identifier identifier;
    /**
     * If the handler is a singleton,
     */
    private final boolean isSingleton;

    private boolean enabled = true;

    private boolean initialized = false;

    private List<EventRunner> eventRunners;

    public EventManager(ScriptedObject target, String eventName, boolean isSingleton) {
        this(target, createID(target, eventName), isSingleton);
    }

    public EventManager(ScriptedObject target, String eventName) {
        this(target, createID(target, eventName), false);
    }

    public EventManager(ScriptedObject target, Identifier identifier) {
        this(target, identifier, false);
    }

    public EventManager(ScriptedObject target, Identifier identifier, boolean isSingleton) {
        this.target = target;
        this.identifier = identifier;
        this.isSingleton = isSingleton;
        GlobalEventContainer.getInstance().addIfMissing(this);

    }

    public static Identifier createID(ScriptedObject target, String eventName) {
        return new Identifier(target.getID().getNamespace(), target.getType() + "/" + target.getID().getPath() + "/" + eventName);
    }


    public void serverInit(MinecraftServer server) {
        if (!initialized) {
            GlobalEventContainer.getInstance().initCallback(identifier, server);
            initialized = true;
        }
    }


    /**
     * call it with the correct context when the event should run.
     */
    public void fire(ServerCommandSource commandContext) {
        if (enabled) {
            GlobalEventContainer.getInstance().addIfMissing(this);

            if (isSingleton) {
                commandContext.getMinecraftServer().send(new ServerTask(1, () -> {
                    getRunners().get(0).fire(commandContext);
                }));
            } else {
                for (EventRunner eventRunner : getRunners()) {
                    commandContext.getMinecraftServer().send(new ServerTask(1, () -> {
                        eventRunner.fire(commandContext);
                    }));
                }
            }
        }
    }

    /**
     * call it with the correct context when the event should run.
     */
    public void fireBlocking(ServerCommandSource commandContext) {
        if (enabled) {
            GlobalEventContainer.getInstance().addIfMissing(this);

            if (isSingleton) {
                commandContext.getMinecraftServer().send(new ServerTask(1, () -> {
                    getRunners().get(0).fire(commandContext);
                }));
            } else {
                for (EventRunner eventRunner : getRunners()) {
                    eventRunner.fire(commandContext);
                }
            }
        }
    }

    public void markDirty() {
        for (EventRunner eventRunner : getRunners()) {
            eventRunner.markDirty();
        }
    }

    /**
     * Returns a list of the available runners.
     * this is done at the first time the event has run, it allows for enough dynamism to make it work with functions.
     */
    private List<EventRunner> getRunners() {
        if (eventRunners == null) {
            eventRunners = new LinkedList<>();
            eventRunnerFactories
                    .iterator()
                    .forEachRemaining(eventRunnerFactory -> {
                        eventRunners.add(eventRunnerFactory.newEvent(identifier));
                    });
        }
        return eventRunners;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean hasEvents() {
        //if it's disabled, then we say that we have no events.
        if (!enabled) {
            return false;
        }
        //safety check.
        if (eventRunners != null) {
            //if any runner has events, return true.
            for (EventRunner eventRunner : eventRunners) {
                if (eventRunner.hasEvents())
                    return true;
            }
        }
        //if we have no runners with events, return false.
        return false;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    public Identifier getID() {
        return identifier;
    }

    public boolean hasEvents(MinecraftServer server) {
        eventRunners.forEach(eventRunner -> eventRunner.reload(server));
        return hasEvents();
    }

    public static EventManager execute(EventManager manager, ScriptedObject thiz, String name, IWorld world_1, Supplier<ServerCommandSource> serverCommandSourceSupplier) {
        if (world_1 instanceof ServerWorld) {
            if (manager == null) {
                manager = new EventManager(thiz, name);
                manager.serverInit(((ServerWorld) world_1).getServer());
            }
            manager.fire(serverCommandSourceSupplier.get());
            return manager;
        }
        return null;
    }
    public static EventManager executeBlocking(EventManager manager, ScriptedObject thiz, String name, IWorld world_1, Supplier<ServerCommandSource> serverCommandSourceSupplier) {
        if (world_1 instanceof ServerWorld) {
            if (manager == null) {
                manager = new EventManager(thiz, name);
                manager.serverInit(((ServerWorld) world_1).getServer());
            }
            manager.fireBlocking(serverCommandSourceSupplier.get());
            return manager;
        }
        return null;
    }

}
