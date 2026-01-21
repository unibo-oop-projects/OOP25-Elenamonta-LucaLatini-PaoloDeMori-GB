package it.unibo.geometrybash.model.obstacle;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.HitBox;

/**
 * Represent an obstacle in the game, it can be a spike or a block.
 *
 * <p>Spike obstacle can be deadly while block isn't
 */
public interface Obstacle extends GameObject<HitBox> {

    /**
     * Return the type of this obstalce.
     *
     * @return the obstacle type
     */
    ObstacleType getObstacleType();

}
