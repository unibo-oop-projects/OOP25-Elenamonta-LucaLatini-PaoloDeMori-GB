package it.unibo.geometrybash.view;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;

/**
 * Processes key-related view events.
 *
 * <p>Implementations translate a `ViewEvent` representing a key action into
 * application-specific handling.
 *
 * @see ViewEvent
 */
@FunctionalInterface
public interface KeyProcessor {

    /**
     * Called when the view reports a key-related event.
     *
     * @param event the view event describing the key action.
     */
    void keyPressionReceived(ViewEvent event);
}
