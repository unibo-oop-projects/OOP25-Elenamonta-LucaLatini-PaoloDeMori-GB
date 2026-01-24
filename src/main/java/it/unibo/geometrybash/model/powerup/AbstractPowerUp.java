package it.unibo.geometrybash.model.powerup;

import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Base implementation for all power-ups {@link PowerUp}.
 *
 * <p>
 * A power-up is a game object that provides a temporary or permanent effect
 * to an entity when collected
 *
 * @param <S> the type of shape used for collision detection
 */
public abstract class AbstractPowerUp<S extends Shape> extends AbstractGameObject<S> implements PowerUp<S> {

    private final PowerUpType powerUpType;

    /**
     * Creates a new power-up.
     *
     * @param position    the position of the power-up
     * @param hitBox      the collision shape used for detecting the interactions
     * @param powerUpType the power-up type, defining its effect and whether it is
     *                    temporary
     * @param duration    the effect duration which is 0 for the permanent one
     */
    protected AbstractPowerUp(final Vector2 position, final S hitBox, final PowerUpType powerUpType,
            final float duration) {
        super(position, hitBox);
        this.powerUpType = powerUpType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final PowerUpType getPowerUpType() {
        return this.powerUpType;
    }

    /**
     * Creates and returns a deep copy of this power-up.
     *
     * @return a new {@link AbstractPowerUp} with the same state as this one
     */
    @Override
    public abstract AbstractPowerUp<S> copy();

}
