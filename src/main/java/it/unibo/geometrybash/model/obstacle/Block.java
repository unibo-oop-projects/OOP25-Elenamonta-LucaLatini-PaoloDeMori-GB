package it.unibo.geometrybash.model.obstacle;

import java.util.List;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * A square block that allowed player to land on it.
 *
 * <p>
 * The block is modeled as a square hitbox and is not considered a deadly
 * obstacle.
 */
public final class Block extends AbstractObstacle {

     public static final int SIZE = 32;

    /**
     * Creates a block at the given position.
     *
     * @param position the bottom left corner of the spike
     */
    protected Block(final Vector2 position) {
        super(position, createHitBox(), ObstacleType.BLOCK);
    }

    /**
     * Creates and returns the hitbox representing the square shape of the block.
     *
     * @return a square {@link HitBox} representing the block
     */
    public static HitBox createHitBox() {
        return new HitBox(List.of(new Vector2(0, 0), new Vector2(SIZE, 0), new Vector2(SIZE, SIZE), new Vector2(0, SIZE)));
    }

    @Override
    public Block copy() {
        final Block copy = new Block(this.position);
        copy.setActive(this.active);
        return copy;
    }
}
