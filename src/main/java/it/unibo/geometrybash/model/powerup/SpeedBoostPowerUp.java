package it.unibo.geometrybash.model.powerup;

import java.util.List;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Represents a {@link PowerUp} which increases the player's speed.
 *
 * <p>
 * When activated, the power-up applies a speed multiplier for a limited
 * duration.
 */
public class SpeedBoostPowerUp extends AbstractPowerUp {

    /**
     * The size of the square power-up's hitbox.
     */
    public static final int SIZE = 28;

    /**
     * The duration, in second, of the speed boost effect.
     */
    public static final float DURATION = 3.0f;

    /**
     * Speed multiplier applied while the power-up is active.
     */
    public static final float MULTIPLIER = 1.5f;

    /**
     * Creates a new {@code SpeedBoostPowerUp} at the given position.
     *
     * @param position the initial position of the power-up in the world
     */
    public SpeedBoostPowerUp(final Vector2 position) {
        super(position, createHitBox(), PowerUpType.SPEED_BOOST, DURATION);
    }

    /**
     * Creates the hitbox associated with this power-up.
     *
     * @return a new {@link HitBox} representing the collision area of the power-up
     */
    private static HitBox createHitBox() {
        return new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(SIZE, 0),
                new Vector2(SIZE, SIZE),
                new Vector2(0, SIZE)));
    }

    /**
     * Returns the speed multiplier provided by this power-up.
     *
     * @return the speed boost multiplier
     */
    public float getMultiplier() {
        return MULTIPLIER;
    }

    /**
     * Creates a defense copy of this power-up.
     *
     * <p>
     * The copy preserves the position and the active state.
     *
     * @return a new {@code SpeedBoostPowerUp} with the same state as this instance
     */
    @Override
    public SpeedBoostPowerUp copy() {
        final SpeedBoostPowerUp copySpeedBoostPowerUp = new SpeedBoostPowerUp(this.position);
        copySpeedBoostPowerUp.setActive(this.active);
        return copySpeedBoostPowerUp;
    }

}
