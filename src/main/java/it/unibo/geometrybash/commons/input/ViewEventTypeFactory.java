package it.unibo.geometrybash.commons.input;

import java.util.Objects;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Factory for creating {@link ViewEventType} instances.
 *
 * <p>Usage:
 * ViewEventType start = ViewEventTypeFactory.of(StandardViewEventType.START);
 * ViewEventType cmd = ViewEventTypeFactory.generic("sudo start");
 */
public final class ViewEventTypeFactory {

    private ViewEventTypeFactory() { }

    /**
     * Creates a standard view event.
     *
     * @param type the standard event type (cannot be GENERIC)
     * @return a new ViewEventType instance
     */
    public static ViewEventType standard(final StandardViewEventType type) {
        Objects.requireNonNull(type, "Type cannot be null");
        if (type == StandardViewEventType.GENERIC) {
            throw new IllegalArgumentException(
                "Use generic(String) for GENERIC events"
            );
        }
        return new StandardViewEvent(type);
    }

    /**
     * Creates a generic terminal command event.
     *
     * @param command the terminal command string
     * @return a new ViewEventType instance
     */
    public static ViewEventType generic(final String command) {
        return new GenericViewEvent(command);
    }
}
