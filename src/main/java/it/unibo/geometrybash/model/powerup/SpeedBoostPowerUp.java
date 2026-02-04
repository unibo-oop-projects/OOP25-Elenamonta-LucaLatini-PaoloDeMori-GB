package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.core.Collidable;
import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;

/**
 * Represents a {@link PowerUp} which increases the player's speed.
 *
 * <p>
 * When activated, the power-up applies a speed multiplier for a limited
 * duration.
 */
public class SpeedBoostPowerUp extends AbstractPowerUp<CircleHitBox> implements Collidable {

    /**
     * Radius of the circle hitbox of the power-up.
     */
    public static final float RADIUS = 0.45f;

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
    SpeedBoostPowerUp(final Vector2 position) {
        super(position, PowerUpType.SPEED_BOOST, DURATION);
        this.hitBox = new CircleHitBox(RADIUS);
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

    /**
     * Handles the collection of the speed boost power-up.
     *
     * <p>
     * Applies a speed multiplier for a set duration through the
     * player's PowerUpManager and deactivates this collectible object.
     *
     * @param player the player that collected the speed boost
     */
    @Override
    public void onCollision(final Player<?> player) {
        player.onSpeedBoostCollected(this, MULTIPLIER, DURATION);
        this.setActive(false);
    }
}
