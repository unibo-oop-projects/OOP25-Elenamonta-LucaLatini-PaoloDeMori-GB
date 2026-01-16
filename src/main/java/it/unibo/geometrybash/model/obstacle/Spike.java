package it.unibo.geometrybash.model.obstacle;

import java.util.List;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * A triangular spike that kills the player on contact.
 *
 * <p>
 * The spike is modeled as a triangular hitbox and is considered a deadly
 * obstacle.
 */
public final class Spike extends AbstractObstacle {

    public static final int SIZE = 32;

    /**
     * Creates a spike at the given position.
     *
     * @param position the bottom left corner of the spike
     */
    protected Spike(final Vector2 position) {
        super(position, createHitBox(), ObstacleType.SPIKE);
    }

    /**
     * Creates and returns the hitbox representing the triangular shape of the spike.
     *
     * @return a triangular {@link HitBox} representing the spike
     */
    public static HitBox createHitBox() {
        return new HitBox(List.of(new Vector2(0, 0), new Vector2(SIZE, 0), new Vector2(SIZE / 2, SIZE)));
    }

    @Override
    public Spike copy() {
        final Spike copy = new Spike(this.position);
        copy.setActive(this.active);
        return copy;
    }

}
