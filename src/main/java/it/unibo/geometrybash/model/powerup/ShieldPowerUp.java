package it.unibo.geometrybash.model.powerup;

import java.util.List;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Represent a {@link PowerUp} that grants a shield to the player.
 *
 * <p>
 * The shield protects the player from one deadly collision.
 */
public final class ShieldPowerUp extends AbstractPowerUp {

    /**
     * Size of the square hitbox of the power-up.
     */
    public static final int SIZE = 28;

    /**
     * Creates a new {@code ShieldPowerUp} at the given position.
     *
     * @param position the initial position of the power-up in the game world
     */
    public ShieldPowerUp(final Vector2 position) {
        super(position, createHitBox(), PowerUpType.SHIELD, 0);
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

}
