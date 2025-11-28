package it.unibo.geometrybash.model.exceptions;

import it.unibo.geometrybash.model.GameLoop;

/**
 * An exception thrown if the {@link GameLoop#resume()} method is called while the gameloop is not Paused.
 */
public class NotOnPauseException extends InvalidGameLoopStatusException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception called if the gameloop try to resume if it's not paused.
     */
    public NotOnPauseException() {
        super();
    }
}
