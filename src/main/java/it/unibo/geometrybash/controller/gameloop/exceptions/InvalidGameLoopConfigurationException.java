package it.unibo.geometrybash.controller.gameloop.exceptions;

/**
 * An Exception thrown if a client tries to execute the 
 * {@link it.unibo.geometrybash.controller.gameloop.GameLoop} without a correct initialization.
 */
public class InvalidGameLoopConfigurationException extends Exception {

    private static final String DEFAULT_MESSAGE = "Trying to execute the gameloop without a correct initialization";
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown if a client tries to execute the gameloop without a correct initialization.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public InvalidGameLoopConfigurationException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception for an error thrown if a client tries to execute the gameloop without a correct initialization.
     * 
     * <p>Uses the default message.
     */
    public InvalidGameLoopConfigurationException() {
        super(DEFAULT_MESSAGE);
    }
}
