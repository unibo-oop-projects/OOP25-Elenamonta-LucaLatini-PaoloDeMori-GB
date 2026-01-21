package it.unibo.geometrybash.model.core;

import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Represents a generic game object.
 *
 * <p>This interface defines the minimal behavior required for all game objects.
 *
 * @param <S> the type of {@link Shape} used for this object's hitbox,
 *            e.g., {@link CircleHitBox} or {@link HitBox}
 */
public interface GameObject<S extends Shape> {

    /**
     * Returns the current position of the object in the game world.
     *
     * @return a {@link Vector2} object representing the object's coordinates
     */
    Vector2 getPosition();

    /**
     * Returns the hitbox of the game object.
     *
     * @return the {@link Shape} representing this object's hitbox, which
     *         may be a {@link CircleHitBox} or a {@link HitBox}
     */
    S getHitBox();

    /**
     * Returns a defensive copy of this {@link GameObject}.
     *
     * <p>
     * Used to prevent exposing internal mutable state. Implementations
     * must return a new instance with the same state so that modifications
     * to the copy do not affect the original.
     * </p>
     *
     * @return a new {@link GameObject} instance duplicating this object's state
     */
    GameObject<S> copy();

    /**
     * Returns whether the object is currently active.
     *
     * <p>
     * The active state is a general indicator of whether the object
     * is considered "in use" or "enabled" in the game context.
     * </p>
     *
     * @return {@code true} if the object is active, {@code false} otherwise
     */
    boolean isActive();

    /**
     * Sets the active state of the object.
     *
     * <p>
     * This method updates the general status of the object as active
     * or inactive. The specific effects depend on the implementation.
     * </p>
     *
     * @param active {@code true} to mark the object as active,
     *               {@code false} to mark it as inactive
     */
    void setActive(boolean active);
}
