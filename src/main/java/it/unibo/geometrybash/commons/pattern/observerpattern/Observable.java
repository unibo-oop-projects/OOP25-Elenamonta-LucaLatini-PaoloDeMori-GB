package it.unibo.geometrybash.commons.pattern.observerpattern;

/**
 * This interface represents a class that generates events and that can be observed.
 * 
 * @param <T> the type of event that this class notify.
 * 
 * @see Event
 * @see Observer
 */
public interface Observable<T extends Event> {

    /**
     * This method add an observer to a data structure that contains all the observers.
     * 
     * @param obs the observer to add
     */
    void addObserver(Observer<? super T> obs);

    /**
     * This method notify all the observers inside the datastructure.
     * 
     * @param event The event that represents the reason why the observable is notifying the observers
     */
    void notifyObservers(T event);
}
