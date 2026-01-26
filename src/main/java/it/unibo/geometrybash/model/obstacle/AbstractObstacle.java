package it.unibo.geometrybash.model.obstacle;

import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Base impletantion for all obstacles.
 *
 * <p>
 * An obstacle is a passive game object that can participate in collision
 * detection but does not actively react to collisions. The collision logic
 * is handled by the player
 */
public abstract class AbstractObstacle extends AbstractGameObject<HitBox> implements Obstacle {

    private final ObstacleType obstacleType;

    /**
     * Creates a new obstacle.
     *
     * @param position the position of the obstacle
     * @param type     the obstacle type
     */
    protected AbstractObstacle(final Vector2 position, final ObstacleType type) {
        super(position);
        this.obstacleType = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    /**
     * Creates and returns a deep copy of this obstacle.
     *
     * @return a new {@link AbstractObstacle} with the same state as this one
     */
    @Override
    public abstract AbstractObstacle copy();
}
