package it.unibo.geometrybash.controller.gameloop.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.unibo.geometrybash.controller.gameloop.GameLoop;

/**
 * An Exception for a generic error during a method call in the {@link GameLoop}.
 */
public class InvalidGameLoopStatusException extends Exception {

    private static final String DEFAULT_MESSAGE = "Error during a method call in the gameloop";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidGameLoopStatusException.class);

    /**
     * Constructs a new Exception for a generic error during a method call in the {@link GameLoop}.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the Default Message.
     */
    public InvalidGameLoopStatusException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
        LOGGER.debug("An InvalidGameLoopStatusException was Created");
    }

    /**
     * Constructs a new Exception for a generic error during a method call in the {@link GameLoop}.
     * 
     * <p>Uses the default message.
     */
    public InvalidGameLoopStatusException() {
        super(DEFAULT_MESSAGE);
        LOGGER.debug("An InvalidGameLoopStatusException was Created");
    }
}
