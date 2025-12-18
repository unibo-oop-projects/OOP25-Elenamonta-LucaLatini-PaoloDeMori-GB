package it.unibo.geometrybash.model.collision;

/**
 * Represents the phase of a collision between two game objects.
 * 
 * <p>
 * Used to distinguish between the beginning and the end of a collision
 * contact within a collision event.
 * </p>
 */
public enum CollisionPhase {

    /**
     * Indicates the beginning of a collision contact.
     */
    BEGIN,

    /**
     * Indicates the end of a collision contact.
     */
    END
}

