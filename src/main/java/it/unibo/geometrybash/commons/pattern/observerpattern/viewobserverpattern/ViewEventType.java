package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import it.unibo.geometrybash.commons.input.StandardViewEventType;

/**
 * Interface representing the type of a view event.
 * Can be either a standard event or a generic terminal command.
 *
 * <p>Use factory methods to create instances:
 *
 * @see StandardViewEventType
 */
public interface ViewEventType {

    /**
     * Checks if this event is a generic terminal command.
     *
     * @return true if this is a generic command, false if it is standard
     */
    boolean isGeneric();

    /**
     * Checks if this event represents user input like the keyboard action JUMP and MENU.
     *
     * @return true if this is a user input event, false otherwise
     */
    boolean isFromUserInput();

    /**
     * Returns the standard type of this event.
     * Returns {@link StandardViewEventType#GENERIC} for generic terminal commands.
     *
     * @return the standard event type (never null)
     */
    StandardViewEventType getStandard();

    /**
     * Returns the command string.
     * For standard events: returns the command name
     * For generic events: returns the custom command entered by the user.
     *
     * @return the command string
     */
    String getCommand();
}
