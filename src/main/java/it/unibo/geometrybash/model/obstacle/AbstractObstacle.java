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
public abstract class AbstractObstacle extends AbstractGameObject implements Obstacle {

    private final ObstacleType obstacleType;

    /**
     * Creates a new obstacle.
     *
     * @param position the position of the obstacle
     * @param hitBox   the collision shape
     * @param type     the obstacle type
     */
    protected AbstractObstacle(final Vector2 position, final HitBox hitBox, final ObstacleType type) {
        super(position, hitBox);
        this.obstacleType = type;
    }

    @Override
    public final ObstacleType getObstacleType() {
        return this.obstacleType;
    }

    @Override
    public final boolean isDeadly() {
        return this.obstacleType.isDeadly();
    }

    @Override
    public abstract AbstractObstacle copy();
}
