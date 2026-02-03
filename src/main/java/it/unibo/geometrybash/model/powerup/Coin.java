package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.core.Collidable;
import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;

/**
 * A {@link Coin} is a collective {@link PowerUp} which increases its in-game
 * monetary balance.
 *
 * <p>
 * Coins are permanent power-ups, they have no duration, and provide a fixed
 * numerical value when picked up.
 */

public final class Coin extends AbstractPowerUp<CircleHitBox> implements Collidable {

    /**
     * The radius lenght of circle hitbox of the coin.
     */
    public static final float RADIUS = 0.4f;

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
        super(position, PowerUpType.COIN, 0);
        this.hitBox = new CircleHitBox(RADIUS);
        this.value = DEFAULT_VALUE;
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
    public Coin copy() {
        final Coin copy = new Coin(this.position);
        copy.setActive(this.active);
        return copy;
    }

    /**
     * Handles the collection of the coin.
     *
     * <p>
     * Adds the coin's value to the player's total and deactivates
     * the coin object so it can no longer be collected or rendered.
     *
     *
     * @param player the player that collected the coin
     */
    @Override
    public void onCollision(final Player<?> player) {
        player.addCoin(this, DEFAULT_VALUE);
        this.setActive(false);

    }

}
