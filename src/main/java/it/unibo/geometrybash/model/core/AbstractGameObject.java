package it.unibo.geometrybash.model.core;

import java.util.Objects;

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
    // Protected fields are required for subclasses; rule disabled because these are
    // not truly public

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

    private OnStateModifiedContact onState;
    // CHECKSTYLE: VisibilityModifier ON

    /**
     * Creates a new game object with the given position and hitbox.
     *
     * @param position the initial position of the object
     */
    protected AbstractGameObject(final Vector2 position) {
        this.position = position;
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
    public void setPosition(final Vector2 vC) {
        this.position = vC;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOnStateModifierContact(final OnStateModifiedContact onStateFunc) {
        this.onState = Objects.requireNonNull(onStateFunc);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void activateContact() {
        if (this.onState != null) {
            this.onState.activateObject(this);
        }
    }
}
