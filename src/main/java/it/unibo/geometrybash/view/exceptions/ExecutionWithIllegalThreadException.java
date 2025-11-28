package it.unibo.geometrybash.view.exceptions;

/**
 * Exception thrown if a client tries to execute the view on a Thread not dedicated to the view.
 */
public class ExecutionWithIllegalThreadException extends Exception {
    private static final String DEFAULT_MESSAGE = "Trying to execute the view on a Thread not dedicated to the view ";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor to create an exception with an added string to the default message.
     * 
     * @param message the string to add to the default message.
     */
    public ExecutionWithIllegalThreadException(final String message) {
        super(DEFAULT_MESSAGE + message);
    }

    /**
     * Constructor to create an exception with the default message.
     */
    public ExecutionWithIllegalThreadException() {
        super(DEFAULT_MESSAGE);
    }

}
