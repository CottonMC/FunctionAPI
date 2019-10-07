package io.github.cottonmc.functionapi.events.runners.service;

import io.github.cottonmc.functionapi.events.runners.EventRunner;
import io.github.cottonmc.functionapi.events.runners.EventRunnerFactory;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.ServiceLoader.load;

public class ServiceRunnerFactory implements EventRunnerFactory {

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