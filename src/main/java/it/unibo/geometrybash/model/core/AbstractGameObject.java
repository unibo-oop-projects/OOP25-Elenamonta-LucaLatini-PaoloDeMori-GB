package it.unibo.geometrybash.model.core;

import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Abstract base implementation of a {@link GameObject}.
 *
 * <p>
 * Provides common state and default behavior shared by game objects.
 * Concrete subclasses must implement object-specific update logic.
 * </p>
 *
 * @param <S> the type of {@link Shape} used for this object's hitbox,
 *            e.g., {@link CircleHitBox} or {@link HitBox}
 */
public abstract class AbstractGameObject<S extends Shape> implements GameObject<S> {

    // CHECKSTYLE: VisibilityModifier OFF
    // Protected fields are required for subclasses; rule disabled because these are not truly public

    /**
     * Current position of the game object.
     */
    protected Vector2 position;

    /**
     * Hitbox associated with the game object.
     */
    protected S hitBox;

    /**
     * Active state of the game object.
     */
    protected boolean active;
    // CHECKSTYLE: VisibilityModifier ON

    /**
     * Creates a new game object with the given position and hitbox.
     *
     * @param position the initial position of the object
     * @param hitBox   the hitbox associated with the object
     */
    protected AbstractGameObject(final Vector2 position, final S hitBox) {
        this.position = position;
        this.hitBox = hitBox;
        this.active = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public S getHitBox() {
        return this.hitBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return this.active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract GameObject<S> copy();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }
}
