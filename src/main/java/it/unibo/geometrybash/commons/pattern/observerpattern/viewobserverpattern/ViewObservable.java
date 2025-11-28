package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import it.unibo.geometrybash.commons.pattern.observerpattern.Observable;

/**
 * An extension of the generic Observer that can be implemented by the View to 
 * be observed by other classes and launch specific events.
 * This class is made to work with instances of {@link ViewObserver}. 
 * 
 * @see ViewEvent
 * @see ViewObserver
 */
public interface ViewObservable extends Observable<ViewEvent> {

}
