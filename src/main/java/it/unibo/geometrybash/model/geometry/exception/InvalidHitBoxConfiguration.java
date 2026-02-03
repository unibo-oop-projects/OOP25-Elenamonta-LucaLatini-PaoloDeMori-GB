package it.unibo.geometrybash.model.geometry.exception;

import it.unibo.geometrybash.model.geometry.HitBox;

/**
 * An Exception thrown to indicate that a {@link HitBox} has been created with
 * an invalid geometric configuration.
 */
public class InvalidHitBoxConfiguration extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid HitBox configuration.";
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new exception indicating that the hitbox has an invalid configuration.
     *
     * @param message a description of the specific validation error
     */
    public InvalidHitBoxConfiguration(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new {@code InvalidHitBoxConfiguration} with a default message.
     */
    public InvalidHitBoxConfiguration() {
        super(DEFAULT_MESSAGE);
    }
}
