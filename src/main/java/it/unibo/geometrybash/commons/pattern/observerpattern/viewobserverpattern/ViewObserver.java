package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import it.unibo.geometrybash.commons.pattern.observerpattern.Observer;

/**
 * An extension of the generic Observer that can be used by the View to handle specific events.
 * 
 * @see ViewEvent
 */
@FunctionalInterface
public interface ViewObserver extends Observer<ViewEvent> {

}
