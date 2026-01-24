package it.unibo.geometrybash.commons.pattern.observerpattern;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link Observable} with a Set.
 * 
 * @param <T> the type of Event that this class notify.
 * 
 * @see Observable
 * @see Observer
 * @see Event
 */
public abstract class AbstractObservableWithSet<T extends Event> implements Observable<T> {
    private final Set<Observer<? super T>> observerSet = new HashSet<>();

    /**
     * Constructs a new Observable that uses a Set.
     */
    public AbstractObservableWithSet() {
        super();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addObserver(final Observer<? super T> obs) {
        observerSet.add(obs);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void notifyObservers(final T event) {
        observerSet.stream().forEach(i -> i.update(event));
    }
}
