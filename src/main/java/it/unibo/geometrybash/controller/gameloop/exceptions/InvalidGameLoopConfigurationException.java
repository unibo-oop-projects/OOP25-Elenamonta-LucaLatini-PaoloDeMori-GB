package it.unibo.geometrybash.controller.gameloop.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Exception thrown if a client tries to execute the 
 * {@link it.unibo.geometrybash.controller.gameloop.GameLoop} without a correct initialization.
 */
public class InvalidGameLoopConfigurationException extends Exception {

    private static final String DEFAULT_MESSAGE = "Trying to execute the gameloop without a correct initialization";
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(InvalidGameLoopConfigurationException.class);

    /**
     * Constructs a new Exception for an error thrown if a client tries to execute the gameloop without a correct initialization.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public InvalidGameLoopConfigurationException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
        LOGGER.debug("An InvalidGameLoopConfigurationException was Created");
    }

    /**
     * Constructs a new Exception for an error thrown if a client tries to execute the gameloop without a correct initialization.
     * 
     * <p>Uses the default message.
     */
    public InvalidGameLoopConfigurationException() {
        super(DEFAULT_MESSAGE);
        LOGGER.debug("An InvalidGameLoopConfigurationException was Created");
    }
}
