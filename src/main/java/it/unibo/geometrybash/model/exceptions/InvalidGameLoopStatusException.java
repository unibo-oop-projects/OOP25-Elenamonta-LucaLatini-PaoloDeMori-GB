package it.unibo.geometrybash.model.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Exception for a generic error during a method call in the {@link it.unibo.model.Gameloop}.
 */
public class InvalidGameLoopStatusException extends Exception {

    private final static String DEFAULT_MESSAGE ="Error during a method call in the gameloop";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidGameLoopStatusException.class);

     /**
     * Constructs a new Exception for a generic error during a method call in the {@link it.unibo.model.Gameloop}.
     * <p>Add a string to the default message.
     */
    public InvalidGameLoopStatusException(String message) {
        super(DEFAULT_MESSAGE + " " + message);
        LOGGER.debug("An InvalidGameLoopStatusException was Created");
    }

    /**
     * Constructs a new Exception for a generic error during a method call in the {@link it.unibo.model.Gameloop}.
     * <p>Uses the default message.
     */
    public InvalidGameLoopStatusException() {
        super(DEFAULT_MESSAGE);
        LOGGER.debug("An InvalidGameLoopStatusException was Created");
    }
}
