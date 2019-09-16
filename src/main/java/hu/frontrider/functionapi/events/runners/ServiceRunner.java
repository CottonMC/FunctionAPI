package hu.frontrider.functionapi.events.runners;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.ServiceLoader.load;

/**
 * Runs java services as event handlers.
 * This does not support dynamic reloading.
 */
public class ServiceRunner implements EventRunner {
    private List<EventHandler> handlers;

    private ServiceRunner(List<EventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void fire(ServerCommandSource serverCommandSource) {
        for (EventHandler handler : handlers) {
            handler.fire(serverCommandSource);
        }

    }

    @Override
    public void markDirty() {
    }

    /**
     * This is the interface that this runner loads as java services.
     */
    public interface EventHandler {

        /**
         * called when the event is fired.
         *
         * @param serverCommandSource stores context information about the event
         */
        void fire(ServerCommandSource serverCommandSource);

        /**
         * Used when selecting which event the handler is hooked onto.
         * The reason for this structure, is to allow for one handler to hook into multiple events.
         *
         * @param identifier the ID of the event that this handler might hook into
         * @return true, if this handler does hook into this event.
         */
        boolean matchesEvent(Identifier identifier);
    }

    public static class ServiceRunnerFactory implements EventRunnerFactory {

        private LinkedList<EventHandler> allHandlers = new LinkedList<>();

        public ServiceRunnerFactory() {
            ServiceLoader<EventHandler> services = load(EventHandler.class);
            services.reload();
            services.iterator().forEachRemaining(eventHandler -> {
                allHandlers.add(eventHandler);
            });
        }

        @Override
        public EventRunner newEvent(Identifier id) {
            List<EventHandler> collect = allHandlers.stream().filter(eventHandler -> eventHandler.matchesEvent(id)).collect(Collectors.toList());
            return new ServiceRunner(collect);
        }
    }
}
