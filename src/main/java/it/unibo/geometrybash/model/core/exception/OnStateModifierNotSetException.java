package it.unibo.geometrybash.model.core.exception;

/**
 * An Exception thrown if an on state modifier is null.
 */
public class OnStateModifierNotSetException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Error during on state modifier activation";
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown if an on state modifier is null.
     *
     * <p>
     * Add a string to the default message.
     *
     * @param message The message to add to the default message.
     */
    public OnStateModifierNotSetException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception for an error thrown if an on state modifier is null
     *
     * <p>
     * Uses the default message.
     */
    public OnStateModifierNotSetException() {
        super(DEFAULT_MESSAGE);
    }

}
