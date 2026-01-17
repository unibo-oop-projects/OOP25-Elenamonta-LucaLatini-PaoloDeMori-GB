package it.unibo.geometrybash.model.obstacle;

import it.unibo.geometrybash.model.core.GameObject;

/**
 * Represent an obstacle in the game, it can be a spike or a block.
 *
 * <p>Spike obstacle can be deadly while block isn't
 */
public interface Obstacle extends GameObject {

    /**
     * Return the type of this obstalce.
     *
     * @return the obstacle type
     */
    ObstacleType getObstacleType();

    /**
     * Check if this obstacle kills the player on contact.
     *
     * @return true if deadly, false otherwise
     */
    boolean isDeadly();
}
