package it.unibo.geometrybash.model.collision;

import it.unibo.geometrybash.model.core.GameObject;

/**
 * Represents an immutable collision event between two game objects.
 *
 * <p>
 * Encapsulates all relevant collision data, including the involved objects
 * and the collision phase. Created by the collision handling system and
 * propagated to game-level collision handlers.
 * </p>
 */
public final class CollisionEvent {

    private final GameObject objectA;
    private final GameObject objectB;
    private final CollisionPhase phase;

    /**
     * Creates a new collision event between two game objects.
     *
     * @param a     the first object involved in the collision
     * @param b     the second object involved in the collision
     * @param phase the phase of the collision ({@link CollisionPhase#BEGIN} or {@link CollisionPhase#END})
     */
    public CollisionEvent(final GameObject a, final GameObject b, final CollisionPhase phase) {
        this.objectA = a.copy();
        this.objectB = b.copy();
        this.phase = phase;
    }

    /**
     * Returns the first object involved in the collision.
     *
     * @return the first colliding {@link GameObject}
     */
    public GameObject getObjectA() {
        return this.objectA.copy();
    }

    /**
     * Returns the second object involved in the collision.
     *
     * @return the second colliding {@link GameObject}
     */
    public GameObject getObjectB() {
        return this.objectB.copy();
    }

    /**
     * Returns the collision phase.
     *
     * @return the {@link CollisionPhase} of this event
     */
    public CollisionPhase getPhase() {
        return this.phase;
    }

    /**
     * Checks if the collision event represents the beginning of contact.
     *
     * @return {@code true} if the collision has started, {@code false} otherwise
     */
    public boolean isBegin() {
        return this.phase.equals(CollisionPhase.BEGIN);
    }

    /**
     * Checks if the collision event represents the end of contact.
     *
     * @return {@code true} if the collision has ended, {@code false} otherwise
     */
    public boolean isEnd() {
        return this.phase.equals(CollisionPhase.END);
    }
}

