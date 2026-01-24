package it.unibo.geometrybash.model.obstacle;

import java.util.List;

import it.unibo.geometrybash.model.collision.Collidable;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;

/**
 * A square block that allowed player to land on it.
 *
 * <p>
 * The block is modeled as a square hitbox and is not considered a deadly
 * obstacle.
 */
public final class Block extends AbstractObstacle implements Collidable {

    /**
     * Default size for the block obstacle in the game, 1.0f represents 1 meter in Jbox2D.
     */
    public static final float SIZE = 1.0f;

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
        return new HitBox(
                List.of(new Vector2(0, 0), new Vector2(SIZE, 0), new Vector2(SIZE, SIZE), new Vector2(0, SIZE)));
    }

    @Override
    public Block copy() {
        final Block copy = new Block(this.position);
        copy.setActive(this.active);
        return copy;
    }

    /**
     * Handles the collision with the player.
     *
     * <p>
     * Since the block is a solid, non-deadly obstacle, its physical
     * presence is handled by the physics engine. No additional logic
     * is required here.
     *
     * @param player the player that collided with this block
     */
    @Override
    public void onCollision(final Player player) {
        // No action required from stati block.
    }

}
