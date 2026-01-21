package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.collision.Collidable;
import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;

/**
 * Represent a {@link PowerUp} that grants a shield to the player.
 *
 * <p>
 * The shield protects the player from one deadly collision.
 */
public final class ShieldPowerUp extends AbstractPowerUp<CircleHitBox> implements Collidable {

    /**
     * Size of the square hitbox of the power-up.
     */
    public static final float RADIUS = 0.45f;

    /**
     * Creates a new {@code ShieldPowerUp} at the given position.
     *
     * @param position the initial position of the power-up in the game world
     */
    public ShieldPowerUp(final Vector2 position) {
        super(position, new CircleHitBox(RADIUS), PowerUpType.SHIELD, 0);
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
    public ShieldPowerUp copy() {
        final ShieldPowerUp copyShieldPowerUp = new ShieldPowerUp(this.position);
        copyShieldPowerUp.setActive(this.active);
        return copyShieldPowerUp;
    }

    /**
     * Handles the collection of the shield power-up.
     *
     * <p>
     * Activates the one-time protection shield via the player's
     * PowerUpManager and deactivates this collectible object.
     *
     * @param player the player that collected the shield
     */
    @Override
    public void onCollision(final Player player) {
        player.onShieldCollected();
        this.setActive(false);
    }

}
