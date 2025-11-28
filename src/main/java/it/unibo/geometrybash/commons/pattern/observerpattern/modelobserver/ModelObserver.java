package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver;

import it.unibo.geometrybash.commons.pattern.observerpattern.Observer;

/**
 * An extension of the generic Observer that can be used by the Model to represents its updates, or handle specific events.
 * 
 * @see ModelEvent
 */
@FunctionalInterface
public interface ModelObserver extends Observer<ModelEvent> {
}
