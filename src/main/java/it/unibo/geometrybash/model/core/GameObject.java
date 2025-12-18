package it.unibo.geometrybash.model.core;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Represents a generic game object.
 *
 * <p>
 * This interface defines the minimal behavior required for all game objects.
 * Each object must implement its own update logic in {@link #update(float)}.
 * </p>
 *
 * <p>
 * Implementations must provide:
 * <ul>
 *   <li>Current position via {@link #getPosition()}</li>
 *   <li>Collision boundaries via {@link #getHitBox()}</li>
 *   <li>Active state management via {@link #isActive()} and {@link #setActive(boolean)}</li>
 *   <li>Per-frame game logic updates via {@link #update(float)}</li>
 *   <li>Type information via {@link #getType()}</li>
 * </ul>
 * </p>
 */
public interface GameObject {

    /**
     * Returns the current position of the object in the game world.
     *
     * @return a {@link Vector2} object representing the object's coordinates
     */
    Vector2 getPosition();

    /**
     * Returns the hitbox of the object, used for collision detection.
     *
     * @return a {@link HitBox} defining the object's physical boundaries
     */
    HitBox getHitBox();

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
    GameObject copy();

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

    /**
     * Returns the runtime class of the object.
     *
     * @return the {@link Class} object corresponding to this object's type
     */
    Class<? extends GameObject> getType();

    /**
     * Updates the object-specific logic for the current frame.
     *
     * <p>
     * This method advances the internal state of the object based on the
     * elapsed time. The actual behavior depends on the concrete type.
     * </p>
     *
     * @param dt the time elapsed since the last update, in seconds
     */
    void update(float dt);
}
