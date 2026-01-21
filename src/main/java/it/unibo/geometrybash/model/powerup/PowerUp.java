package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Shape;

/**
 * Represents a collectible power-up in the game.
 *
 * <p>Power-ups can be permanent (coins) or temporary
 *
 * @param <S> the type of shape used for collision detection
 */
public interface PowerUp<S extends Shape> extends GameObject<S> {

    /**
     * Returns the type of this power-up.
     *
     * @return the power-uo type
     */
    PowerUpType getPowerUpType();

}
