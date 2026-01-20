package it.unibo.geometrybash.model.physics;

import it.unibo.geometrybash.model.geometry.Vector2;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * Concrete implementation of {@link PlayerPhysics} based on JBox2D.
 * <p>
 * This class manages the physical state and behavior of a player entity
 * by delegating all physics-related operations to a JBox2D {@link Body}.
 * It acts as an adapter between the physics engine and the game model,
 * exposing physics data through domain-level types where required.
 * </p>
 */
public class PlayerPhysicsImpl implements PlayerPhysics{

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
    public PlayerPhysicsImpl(final Body body) {
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

        Vec2 vel = body.getLinearVelocity();
        body.setLinearVelocity(new Vec2(vel.x, 0f));

        body.applyLinearImpulse(
            new Vec2(0f, JUMP_IMPULSE),
            body.getWorldCenter()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVelocity(float multiplier) {
        final float currentSpeed = BASE_SPEED * multiplier;
        body.setLinearVelocity(new Vec2(currentSpeed, body.getLinearVelocity().y));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getVelocity() {
        return new Vector2(body.getLinearVelocity().x, body.getLinearVelocity().y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGrounded() {
        return groundContacts > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Body getBody() {
        return body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetBodyTo(Vector2 pos) {
        body.setTransform(new Vec2(pos.x(), pos.y()), 0f);
        body.setLinearVelocity(new Vec2(0f, 0f));
        body.setAngularVelocity(0f);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserData(Object userData) {
        body.setUserData(userData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementGroundContacts() {
        groundContacts++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementGroundContacts() {
        groundContacts = Math.max(0, groundContacts - 1);
    }

}
