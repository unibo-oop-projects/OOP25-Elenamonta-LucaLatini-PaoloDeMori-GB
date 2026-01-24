package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Concrete implementation of {@link PlayerPhysics} based on JBox2D.
 *
 * <p>
 * This class manages the physical state and behavior of a player entity
 * by delegating all physics-related operations to a JBox2D {@link Body}.
 * It acts as an adapter between the physics engine and the game model,
 * exposing physics data through domain-level types where required.
 * </p>
 */
public class PlayerPhysicsImpl implements PlayerPhysics {

    /**
     * Magnitude of the vertical impulse applied when a jump is performed.
     */
    private static final float JUMP_IMPULSE = 6.5f;

    /**
     * Player's default speed.
     */
    private static final float BASE_SPEED = 5.0f;

    /**
     * JBox2D body representing the player's physical presence in the world.
     */
    private final Body body;

    /**
     * Number of active contacts between the player and ground surfaces.
     */
    private int groundContacts;

    /**
     * Creates a new physics component for a player entity.
     *
     * @param body the JBox2D body associated with the player
     */
    protected PlayerPhysicsImpl(final Body body) {
        this.body = body;
        this.groundContacts = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyJump() {
        if (!isGrounded()) {
            return;
        }

        final Vec2 velocity = this.body.getLinearVelocity();
        this.body.setLinearVelocity(new Vec2(velocity.x, 0f));

        this.body.applyLinearImpulse(
                new Vec2(0f, JUMP_IMPULSE),
                this.body.getWorldCenter());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVelocity(final float multiplier) {
        final float currentSpeed = BASE_SPEED * multiplier;
        this.body.setLinearVelocity(new Vec2(currentSpeed, this.body.getLinearVelocity().y));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getVelocity() {
        return new Vector2(this.body.getLinearVelocity().x, this.body.getLinearVelocity().y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGrounded() {
        return this.groundContacts > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetBodyTo(final Vector2 position) {
        this.body.setTransform(new Vec2(position.x(), position.y()), 0f);
        this.body.setLinearVelocity(new Vec2(0f, 0f));
        this.body.setAngularVelocity(0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(body.getPosition().x, body.getPosition().y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserData(final Object userData) {
        this.body.setUserData(userData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementGroundContacts() {
        this.groundContacts++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementGroundContacts() {
        this.groundContacts = Math.max(0, this.groundContacts - 1);
    }

}
