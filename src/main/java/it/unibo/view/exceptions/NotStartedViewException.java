package it.unibo.view.exceptions;

/**
 * Exception thrown if a client tries to call methods on the view without a correct initialization. 
 */
public class NotStartedViewException extends Exception {
    private static final String DEFAULT_MESSAGE = "Trying to call methods on the view without a correct initialization ";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor to create an exception with an added string to the default message.
     * 
     * @param message the string to add to the default message.
     */
    public NotStartedViewException(final String message) {
        super(DEFAULT_MESSAGE + message);
    }

    /**
     * Constructor to create an exception with the default message.
     */
    public NotStartedViewException() {
        super(DEFAULT_MESSAGE);
    }

}
