package it.unibo.geometrybash.commons.pattern.observerpattern;

/**
 * Interface that represents a generic event for the Observer Pattern.
 * 
 * @see Observer
 * @see Observable
 */
@FunctionalInterface
public interface Event {
    /**
     * This method return the name of the Event.
     * 
     * @return the name of the event
     */
    String getEventName();
}
