package it.unibo.geometrybash.controller.gameloop.exceptions;

import it.unibo.geometrybash.controller.gameloop.GameLoop;

/**
 * An exception thrown if the start method is called while the {@link GameLoop} hasn't been started yet.
 */
public class NotStartedException extends InvalidGameLoopStatusException {
    private static final String DEFAULT_MESSAGE = "Trying to call a method on the gameloop, while it is not being started";
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception thrown if the start method is called while the {@link GameLoop} hasn't been started yet.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public NotStartedException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception thrown if the start method is called while the {@link GameLoop} hasn't been started yet.
     * 
     * <p>Uses the default message.
     */
    public NotStartedException() {
        super(DEFAULT_MESSAGE);
    }
}
