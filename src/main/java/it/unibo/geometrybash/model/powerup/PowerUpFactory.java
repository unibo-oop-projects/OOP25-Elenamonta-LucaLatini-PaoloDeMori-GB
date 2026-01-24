package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Factory for creating {@link PowerUp} instances.
 *
 * <p>
 * Centralizes power-up instantiation logic, useful for
 * spawning collectibles from level data.
 */
public final class PowerUpFactory {

    /**
     * Create new power-up.
     */
    private PowerUpFactory() {
        // Default constructor.
    }

    /**
     * Creates a power-up of the specified type at the given position.
     *
     * @param type     the power-up's type to create
     * @param position the power-up's position in the game world
     * @return a new {@link PowerUp}
     */
    public static PowerUp<?> create(final PowerUpType type, final Vector2 position) {
        return switch (type) {
            case COIN -> new Coin(position);
            case SHIELD -> new ShieldPowerUp(position);
            case SPEED_BOOST -> new SpeedBoostPowerUp(position);
        };
    }
}
