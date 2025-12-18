package it.unibo.geometrybash.model.core;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Abstract base implementation of a {@link GameObject}.
 *
 * <p>
 * Provides common state and default behavior shared by game objects.
 * Concrete subclasses must implement object-specific update logic.
 * </p>
 */
public abstract class AbstractGameObject implements GameObject {

    // CHECKSTYLE: VisibilityModifier OFF
    // Protected fields are required for subclasses; rule disabled because these are not truly public

    /**
     * Current position of the game object.
     */
    protected Vector2 position;

    /**
     * Hitbox associated with the game object.
     */
    protected HitBox hitBox;

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
    protected AbstractGameObject(final Vector2 position, final HitBox hitBox) {
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
    public HitBox getHitBox() {
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
    public abstract GameObject copy();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(final boolean active) {
        this.active = active;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends GameObject> getType() {
        return this.getClass();
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Must be implemented by concrete game objects.
     * </p>
     */
    @Override
    public abstract void update(float dt);
}
