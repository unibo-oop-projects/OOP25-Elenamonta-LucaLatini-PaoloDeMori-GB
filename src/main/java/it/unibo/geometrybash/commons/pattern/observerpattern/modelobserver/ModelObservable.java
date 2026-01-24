package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver;

import it.unibo.geometrybash.commons.pattern.observerpattern.Observable;

/**
 * An extension of the generic Observer that can be implemented by the Model to 
 * be observed by other classes and launch specific events.
 * This class is made to work with instances of {@link ModelObserver}. 
 * 
 * @see ModelEvent
 * @see ModelObserver
 */
public interface ModelObservable extends Observable<ModelEvent> {
}
