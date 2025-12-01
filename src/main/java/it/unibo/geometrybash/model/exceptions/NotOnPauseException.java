package it.unibo.geometrybash.model.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.model.GameLoop;

/**
 * An exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
 */
public class NotOnPauseException extends InvalidGameLoopStatusException {
    private final static String DEFAULT_MESSAGE ="Trying to call the resume method on the gameloop, while it is not paused";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(NotOnPauseException.class);

     /**
     * Constructs a new Exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
     * <p>Add a string to the default message.
     */
    public NotOnPauseException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
        LOGGER.debug("A NotOnPauseException was Created");
    }

    /**
     * Constructs a new Exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
     * <p>Uses the default message.
     */
    public NotOnPauseException() {
        super(DEFAULT_MESSAGE);
        LOGGER.debug("A NotOnPauseException was Created");
    }
}
