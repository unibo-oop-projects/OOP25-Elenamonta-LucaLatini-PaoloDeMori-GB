package it.unibo.geometrybash.model.exceptions;

import it.unibo.geometrybash.model.GameLoop;

/**
 * An exception thrown if an invalid method is called using {@link GameLoop}.
 */
public class NotStartedException extends InvalidGameLoopStatusException {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an exception called if an invalid method is called while the gameloop didn't start the first time.
     * 
     * @see GameLoop
     */
    public NotStartedException() {
        super();
    }
}
