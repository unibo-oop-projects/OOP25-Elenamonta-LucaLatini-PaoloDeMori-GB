package it.unibo.geometrybash.controller.gameloop.exceptions;

import it.unibo.geometrybash.controller.gameloop.GameLoop;

/**
 * An exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
 */
public class NotOnPauseException extends InvalidGameLoopStatusException {
    private static final String DEFAULT_MESSAGE = "Trying to call the resume method on the gameloop, while it is not paused";
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the Default message.
     */
    public NotOnPauseException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
     * 
     * <p>Uses the default message.
     */
    public NotOnPauseException() {
        super(DEFAULT_MESSAGE);
    }
}
