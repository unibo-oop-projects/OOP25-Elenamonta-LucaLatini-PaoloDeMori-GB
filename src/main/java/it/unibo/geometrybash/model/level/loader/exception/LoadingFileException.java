package it.unibo.geometrybash.model.level.loader.exception;

/**
 * An Exception thrown if an object of the model isnt correctly initialise.
 */
public class LoadingFileException extends Exception {
    private static final String DEFAULT_MESSAGE = "Error during level loading";

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown if the level file isn't
     * correctly formatted.
     * 
     * <p>
     * Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     * @param e       the parent exception
     */
    public LoadingFileException(final String message, final Exception e) {
        super(DEFAULT_MESSAGE + " " + message, e);
    }

    /**
     * Constructs a new Exception for an error thrown if the level file isn't
     * correctly formatted.
     * 
     * <p>
     * Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public LoadingFileException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception for an error thrown if the level file isn't
     * correctly formatted.
     * 
     * <p>
     * Uses the default message.
     */
    public LoadingFileException() {
        super(DEFAULT_MESSAGE);
    }
}
