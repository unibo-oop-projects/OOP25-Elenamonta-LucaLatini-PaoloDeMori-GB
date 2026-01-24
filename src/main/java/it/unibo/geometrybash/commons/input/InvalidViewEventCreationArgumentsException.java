package it.unibo.geometrybash.commons.input;

/**
 * Exception thrown if a client tries to create an view event with invalid arguments.
 */
public class InvalidViewEventCreationArgumentsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Trying to create an view event with invalid arguments";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor to create an exception with an added string to the default message.
     *
     * @param message the string to add to the default message.
     */
    public InvalidViewEventCreationArgumentsException(final String message) {
        super(DEFAULT_MESSAGE + message);
    }

    /**
     * Constructor to create an exception with the default message.
     */
    public InvalidViewEventCreationArgumentsException() {
        super(DEFAULT_MESSAGE);
    }

}
