package it.unibo.geometrybash.model.powerup;

import java.util.List;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * A {@link Coin} is a collective {@link PowerUp} which increases its in-game
 * monetary balance.
 *
 * <p>
 * Coins are permanent power-ups, they have no duration, and provide a fixed
 * numerical value when picked up.
 */

public final class Coin extends AbstractPowerUp {

    /**
     * The side lenght of square hitbox of the coin.
     */
    public static final int SIZE = 24;

    /**
     * The default values awarded.
     */
    public static final int DEFAULT_VALUE = 1;

    private final int value;

    /**
     * Creates new coin at the given position.
     *
     * @param position the initial coin's position
     */
    public Coin(final Vector2 position) {
        super(position, createHitBox(), PowerUpType.COIN, 0);
        this.value = DEFAULT_VALUE;
    }

    /**
     * Creates the square hitbox used for collision detection of the coin.
     *
     * @return a square {@link HitBox} with side length {@link #SIZE}
     */
    private static HitBox createHitBox() {
        return new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(SIZE, 0),
                new Vector2(SIZE, SIZE),
                new Vector2(0, SIZE)));
    }

    /**
     * Returns the value awarded by this coin when collected.
     *
     * @return the coin value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The copied coin preserves the same position and active state of the original.
     */
    @Override
    public AbstractPowerUp copy() {
        final Coin copy = new Coin(this.position);
        copy.setActive(this.active);
        return copy;
    }

}
