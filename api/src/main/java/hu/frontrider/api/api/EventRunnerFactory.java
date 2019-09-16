package hu.frontrider.api.api;


/**
 * Used when loading new event handlers, this is a singleton service that builds new runners.
 * This adds the required amount of dynamism that we need to load handlers from datapacks.
 * */
public interface EventRunnerFactory {
    EventRunner newEvent(Identifier id);
}
