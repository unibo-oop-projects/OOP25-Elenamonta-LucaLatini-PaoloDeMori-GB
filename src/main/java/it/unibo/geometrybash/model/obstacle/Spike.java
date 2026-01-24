package it.unibo.geometrybash.model.obstacle;

import java.util.List;

import it.unibo.geometrybash.model.collision.Collidable;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;

/**
 * A triangular spike that kills the player on contact.
 *
 * <p>
 * The spike is modeled as a triangular hitbox and is considered a deadly
 * obstacle.
 */
public final class Spike extends AbstractObstacle implements Collidable {

    /**
     * Default size for the spike obstacle in the game, 1.0f represents 1 meter in
     * Jbox2D.
     */
    public static final float SIZE = 1.0f;

    /**
     * Creates a spike at the given position.
     *
     * @param position the bottom left corner of the spike
     */
    protected Spike(final Vector2 position) {
        super(position, createHitBox(), ObstacleType.SPIKE);
    }

    /**
     * Creates and returns the hitbox representing the triangular shape of the
     * spike.
     *
     * @return a triangular {@link HitBox} representing the spike
     */
    public static HitBox createHitBox() {
        return new HitBox(List.of(new Vector2(0, 0), new Vector2(SIZE, 0), new Vector2(SIZE / 2, SIZE)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Spike copy() {
        final Spike copy = new Spike(this.position);
        copy.setActive(this.active);
        return copy;
    }

    /**
     * Handles the collision with the player.
     *
     * <p>
     * If the player has an active shield, the shield is consumed and the spike
     * is deactivated to allow the player to pass safely.
     * Otherwise, the player dies.
     *
     * @param player the player that collided with this spike
     */
    @Override
    public void onCollision(final Player<?> player) {
        player.onSpikeCollision(this);
    }
}
