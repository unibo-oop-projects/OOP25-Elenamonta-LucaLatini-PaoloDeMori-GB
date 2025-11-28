package it.unibo.geometrybash.model.exceptions;

/**
 * An Exception for a generic error during a method call in the gameloop.
 */
public class InvalidGameLoopStatusException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for a generic error during a method call in the gameloop.
     */
    public InvalidGameLoopStatusException() {
        super();
    }
}
