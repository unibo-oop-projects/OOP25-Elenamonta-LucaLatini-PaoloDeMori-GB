package it.unibo.geometrybash.controller.input;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Implementation of {@link ViewEventType} for generic terminal commands.
 * Package-private: use {@link ViewEventTypeFactory} to create instances.
 */
final class GenericViewEvent implements ViewEventType {

    private final String command;

    /**
     * Creates a generic command event.
     * 
     * @param command the terminal command
     */
    GenericViewEvent(final String command) {
        if (command == null || command.isBlank()) {
            throw new InvalidViewEventCreationArgumentsException("Command cannot be blank or null");
        }
        this.command = command;
    }

    /**
     * Checks if this event is generic.
     * 
     * @return true if the event is generic, false otherwise
     */
    @Override
    public boolean isGeneric() {
        return true;
    }

    /**
     * Returns the standard view event type.
     * 
     * @return the standard view event type
     */
    @Override
    public StandardViewEventType getStandard() {
        return StandardViewEventType.GENERIC;
    }

    /**
     * Returns the terminal command associated with this event.
     * 
     * @return the terminal command
     */
    @Override
    public String getCommand() {
        return command;
    }

}
