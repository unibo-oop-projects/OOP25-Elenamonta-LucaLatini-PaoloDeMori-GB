package it.unibo.geometrybash.model.level.map.exceptions;

/**
 * Exception thrown when an invalid operation is performed on the game map.
 */
public class GameMapOperationException extends Exception {

    private static final String DEFAULT_MESSAGE = "Error during an operation using a game map ";

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception with a custom message appended to the default one.
     *
     * @param message the detail message.
     */
    public GameMapOperationException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new exception with the default message.
     *
     * <p>Uses the default message.
     */
    public GameMapOperationException() {
        super(DEFAULT_MESSAGE);
    }
}
