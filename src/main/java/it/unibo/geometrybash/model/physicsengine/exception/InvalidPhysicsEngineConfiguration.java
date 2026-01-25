package it.unibo.geometrybash.model.physicsengine.exception;

/**
 * An Exception thrown if an object of the physics engine isnt correctly initialise.
 */
public class InvalidPhysicsEngineConfiguration extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Error during initialization in model";

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new Exception for an error thrown if an object of the physics engine isnt correctly initialise.
     * 
     * <p>Add a string to the default message.
     * 
     * @param message The message to add to the default message.
     */
    public InvalidPhysicsEngineConfiguration(final String message) {
        super(DEFAULT_MESSAGE + " " + message);
    }

    /**
     * Constructs a new Exception for an error thrown if an object of the physics engine isnt correctly initialise.
     * 
     * <p>Uses the default message.
     */
    public InvalidPhysicsEngineConfiguration() {
        super(DEFAULT_MESSAGE);
    }
}
