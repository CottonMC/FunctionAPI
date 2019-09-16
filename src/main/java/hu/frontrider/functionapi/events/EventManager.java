package hu.frontrider.functionapi.events;

import hu.frontrider.functionapi.ScriptedObject;
import hu.frontrider.functionapi.events.runners.EventRunner;
import hu.frontrider.functionapi.events.runners.EventRunnerFactory;
import hu.frontrider.functionapi.events.runners.ServerCommandRunner;
import hu.frontrider.functionapi.events.runners.ServiceRunner;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;


/**
 * Handles an event
 */
public class EventManager {

    /**
     * The different runners allow us to hook into the event with different techniques.
     * Runners are build by factories, that are loaded as java services.
     * */
    private static LinkedList<EventRunnerFactory> eventRunnerFactories = new LinkedList<>();

    static {
        ServiceLoader<EventRunnerFactory> runnerFactories = ServiceLoader.load(EventRunnerFactory.class);
        runnerFactories.iterator().forEachRemaining(eventRunnerFactory -> eventRunnerFactories.add(eventRunnerFactory));
    }

    private final ScriptedObject target;
    private String eventName;
    private Identifier identifier = null;

    private List<EventRunner> eventRunners;

    public EventManager(ScriptedObject target, String eventName) {
        this.target = target;
        this.eventName = eventName;

    }

    public EventManager(ScriptedObject target, Identifier identifier) {
        this.target = target;
        this.identifier = identifier;
    }

    /**
     * call it with the correct context when the event should run.
     */
    public void fire(ServerCommandSource commandContext) {
        for (EventRunner eventRunner : getRunners()) {
            eventRunner.fire(commandContext);
        }
    }

    public void markDirty() {
        for (EventRunner eventRunner : getRunners()) {
            eventRunner.markDirty();
        }
    }

    public Identifier getID() {
        if (identifier == null) {
            Identifier targetID = target.getID();
            this.identifier = new Identifier("api", target.getType() + "-" + eventName + "-" + targetID.getNamespace() + "-" + targetID.getPath());
        }
        return identifier;
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
                        eventRunners.add(eventRunnerFactory.newEvent(getID()));
                    });
        }
        return eventRunners;
    }

}
