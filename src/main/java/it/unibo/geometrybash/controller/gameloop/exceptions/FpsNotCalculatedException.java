package it.unibo.geometrybash.controller.gameloop.exceptions;

import it.unibo.geometrybash.controller.gameloop.GameLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Exception thrown if a client tries to get the number of FPS of the {@link GameLoop}, 
 * but it has not been calculated once yet.
 */
public class FpsNotCalculatedException extends Exception {
    private static final String DEFAULT_MESSAGE = "Trying to get the number of FPS , but it has not been calculated once yet";
    private static final Logger LOGGER = LoggerFactory.getLogger(FpsNotCalculatedException.class);

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown if a client tries to get the number of FPS of the gameloop,
     * but it has not been calculated once yet.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public FpsNotCalculatedException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
        LOGGER.debug("An FPSNoTCalculatedException was Created");
    }

    /**
     * Constructs a new Exception for an error thrown if a client tries to get the number of FPS of the gameloop,
     * but it has not been calculated once yet.
     * 
     * <p>Uses the default message.
     */
    public FpsNotCalculatedException() {
        super(DEFAULT_MESSAGE);
        LOGGER.debug("An FPSNoTCalculatedException was Created");
    }
}
