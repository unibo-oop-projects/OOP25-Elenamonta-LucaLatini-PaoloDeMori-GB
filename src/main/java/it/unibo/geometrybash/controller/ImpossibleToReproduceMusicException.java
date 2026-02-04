package it.unibo.geometrybash.controller;

/**
 * An Exception thrown if an error occured while reproducing sounds in the
 * controller.
 */
public class ImpossibleToReproduceMusicException extends Exception {
    private static final String DEFAULT_MESSAGE = "an error occured while reproducing sounds";

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown while reproducing sounds in
     * the controller.
     * 
     * <p>
     * Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     * @param e       the parent exception
     */
    public ImpossibleToReproduceMusicException(final String message, final Exception e) {
        super(DEFAULT_MESSAGE + " " + message, e);
    }

    /**
     * Constructs a new Exception for an error thrown while reproducing sounds in
     * the controller.
     * 
     * <p>
     * Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public ImpossibleToReproduceMusicException(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception for an error thrown while reproducing sounds in
     * the controller.
     * 
     * <p>
     * Uses the default message.
     */
    public ImpossibleToReproduceMusicException() {
        super(DEFAULT_MESSAGE);
    }
}
