package it.unibo.geometrybash.commons.input;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Implementation of {@link ViewEventType} for standard events.
 * Package-private: use {@link ViewEventTypeFactory} to create instances.
 */
final class StandardViewEvent implements ViewEventType {

    private final StandardViewEventType type;

    /**
     * Creates a standard view event.
     *
     * @param type the standard event type
     */
    StandardViewEvent(final StandardViewEventType type) {
        if (type == null) {
            throw new InvalidViewEventCreationArgumentsException();
        }
        this.type = type;
    }

    /**
     * {@inheritDoc}
     *
     * <p>returns false because this class never contains a generic command.
     */
    @Override
    public boolean isGeneric() {
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>return true if it is a user input
     */
    @Override
    public boolean isFromUserInput() {
        return type.isUserInput();
    }

    /**
     * {@inheritDoc}
     *
     * <p>returns the type of the  standard event.
     */
    @Override
    public StandardViewEventType getStandard() {
        return type;
    }

    /**
     * {@inheritDoc}
     *
     * <p>returns the name of the standard event, because this can't contain a generic command.
     * differently to {@link it.unibo.geometrybash.controller.input.GenericViewEvent#getCommand()}
     */
    @Override
    public String getCommand() {
        return type.getCommandName();
    }

}
