package it.unibo.geometrybash.commons.pattern.observerpattern;

/**
 * This interface represents a class that listen to a specific event to be triggered.
 * 
 * @param <T>  the type of event that trigger this class
 * 
 * @see Observable
 * @see Event
 */

@FunctionalInterface
public interface Observer<T extends Event> {
    /**
     * This method is called by the observable .
     * 
     * @param event the representation of the event that triggered the update
     */
    void update(T event);
}
