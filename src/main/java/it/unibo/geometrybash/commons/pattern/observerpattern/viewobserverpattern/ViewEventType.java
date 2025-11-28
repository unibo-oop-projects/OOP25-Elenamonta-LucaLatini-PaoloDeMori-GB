package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

/**
 * Enum that represents the kind of Events that can be notified to a {@link ViewObserver}.
 * 
 * @see ViewObserver
 * @see ViewEvent
 */
public enum ViewEventType {
    /**
     * Event that represents the resumption of the game form the menu.
     */
    RESUME("Resume");

    private final String name; //the name of the type

    ViewEventType(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the type of the event.
     * 
     * @return a String representation of the type.
     */
    public String getName() {
        return this.name;
    }

}
