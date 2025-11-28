package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver;

/**
 * Enum that represents the kind of Events that can be notified to a {@link ModelObserver}.
 * 
 * @see ModelObserver
 * @see ModelEvent
 */
public enum ModelEventType {
    /**
     * Represents an update in the model.
     */
    UPDATE("Update"),
    /**
     * Represents a gameover in the model.
     */
    GAMEOVER("GameOver");

    private final String name; //the name of the type

    ModelEventType(final String name) {
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
