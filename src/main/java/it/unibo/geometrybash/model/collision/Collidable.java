package it.unibo.geometrybash.model.collision;

import it.unibo.geometrybash.model.player.Player;

/**
 * Represents a game object that can react to an instantaneous collision
 * with the player.
 *
 * <p>
 * Implementing classes define the behavior that is triggered when the player
 * comes into contact with the object.
 * </p>
 *
 * <p>
 * The collision detection and dispatching are handled externally by the
 * collision system. Implementations of this interface are responsible only
 * for defining the collision response logic.
 * </p>
 */
@FunctionalInterface
public interface Collidable {

    /**
     * Handles the reaction to a collision with the player.
     *
     * @param player the player involved in the collision
     */
    void onCollision(Player player);
}
