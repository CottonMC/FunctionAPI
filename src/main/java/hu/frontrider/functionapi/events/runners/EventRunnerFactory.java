package hu.frontrider.functionapi.events.runners;

import net.minecraft.util.Identifier;

/**
 * Used when loading new event handlers, this is a singleton service that builds new runners.
 * This adds the required amount of dynamism that we need to load handlers from a variety of different sources.
 * */
public interface EventRunnerFactory {
    /**
     * return a new runner for this ID.
     * */
    EventRunner newEvent(Identifier id);
}
