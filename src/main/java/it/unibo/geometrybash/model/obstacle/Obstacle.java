package it.unibo.geometrybash.model.obstacle;

/**
 * Represent an obstacle in the game, it can be a spike or a block.
 *
 * <p>Spike obstacle can be deadly while block isn't
 */
public interface Obstacle {

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
