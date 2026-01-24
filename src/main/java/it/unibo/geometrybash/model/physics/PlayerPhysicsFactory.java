package it.unibo.geometrybash.model.physics;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Factory interface responsible for creating {@link PlayerPhysics} instances.
 *
 * <p>
 * This factory abstracts the creation of the physics component associated with a player,
 * decoupling game logic from the underlying physics engine implementation.
 * </p>
 */
@FunctionalInterface
public interface PlayerPhysicsFactory {

    /**
     * Creates a new {@link PlayerPhysics} instance initialized at the given position.
     *
     * @param position the initial position of the player in the game world
     * @return a newly created {@link PlayerPhysics} instance
     */
    PlayerPhysics createPhysics(Vector2 position);
}
