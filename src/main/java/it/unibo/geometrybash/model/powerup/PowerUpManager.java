package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.core.Updatable;

/**
 * Manages active power-up effects on the player.
 *
 * <p>
 * Handles shield state and timed effects like speed boost.
 * This manager should be updated every frame via {@link #update(float)}
 * by the player implementation.
 */
public class PowerUpManager implements Updatable {
    /**
     * Normal multiplier when no speed boost is active.
     */
    private static final float NORMAL_SPEED_MULTIPLIER = 1.0f;

    private boolean shielded;
    private float speedMultiplier;
    private float speedBoostTimer;

    /**
     * Creates a new PowerUpManager with no active effects.
     */
    public PowerUpManager() {
        this.shielded = false;
        this.speedMultiplier = NORMAL_SPEED_MULTIPLIER;
        this.speedBoostTimer = 0f;
    }

    /**
     * Activates the shield (one-time protection).
     */
    public void activateShield() {
        this.shielded = true;
    }

    /**
     * Consumes the shield after blocking a collision.
     */
    public void consumeShield() {
        this.shielded = false;
    }

    /**
     * Checks if the shield is currently active.
     *
     * @return true if protected by shield
     */
    public boolean isShielded() {
        return this.shielded;
    }

    /**
     * Applies a speed modifier for a specific duration.
     *
     * @param multiplier the factor to scale the base speed
     * @param duration   time in seconds the effect lasts
     */
    public void applySpeedModifier(final float multiplier, final float duration) {
        this.speedMultiplier = multiplier;
        this.speedBoostTimer = duration;
    }

    /**
     * Returns the current speed multiplier to be applied to the physics engine.
     *
     * @return 1.0 if normal, or the boost value if active
     */
    public float getSpeedMultiplier() {
        return this.speedMultiplier;
    }

    /**
     * Updates effect timers. Must be called in the player's update loop.
     *
     * @param dt delta time in seconds
     */
    @Override
    public void update(final float dt) {
        if (this.speedBoostTimer > 0) {
            this.speedBoostTimer -= dt;
            if (this.speedBoostTimer <= 0) {
                this.speedBoostTimer = 0;
                this.speedMultiplier = NORMAL_SPEED_MULTIPLIER;
            }
        }
    }

    /**
     * Resets all power-up effects (e.g., on player respawn).
     */
    public void reset() {
        this.shielded = false;
        this.speedMultiplier = NORMAL_SPEED_MULTIPLIER;
        this.speedBoostTimer = 0f;
    }

}
