package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;

import java.util.Objects;

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

    private static final float JUMP_IMPULSE = JBox2DValues.JUMP_IMPULSE;
    private static final float BASE_SPEED = JBox2DValues.BASE_SPEED;
    private static final float TOLLERANCE = 0.001f;
    private final Body body;
    private int groundContacts;

    private Vec2 lastPosition;
    private short counter;

    /**
     * Creates a new physics component for a player entity.
     *
     * @param body the JBox2D body associated with the player
     */
    protected PlayerPhysicsImpl(final Body body) {
        this.body = Objects.requireNonNull(body);
        this.body.setLinearVelocity(new Vec2(BASE_SPEED, 0f));
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
        if (lastPosition != null && Math.abs(this.body.getPosition().x - lastPosition.x) < TOLLERANCE && isGrounded()) {
            counter++;
            if (counter > 2) {
                this.body.setActive(false);
                this.body.setAwake(true);
                this.body.synchronizeTransform();
                this.body.setActive(true);
                counter = 0;
            }
        } else {
            counter = 0;
        }
        lastPosition = this.body.getPosition().clone();
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
    public void resetBodyTo(final Vector2 position) {
        this.body.setLinearVelocity(new Vec2(BASE_SPEED, 0f));
        this.body.setAngularVelocity(0f);
        this.body.setTransform(new Vec2(position.x(), position.y()), 0f);
        this.body.setAwake(true);
        this.groundContacts = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPosition(final HitBox hB) {
        // Converts the physics body position to the model position, which represents
        // the bottom-left corner of the entity.
        return new Vector2(
                body.getPosition().x - (hB.getWidth() / 2f),
                body.getPosition().y - (hB.getHeight() / 2f));
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
    public boolean isGrounded() {
        return this.groundContacts > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGroundContactBegin() {
        this.groundContacts++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGroundContactEnd() {
        this.groundContacts = Math.max(0, this.groundContacts - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(final boolean activeState) {
        this.body.setActive(activeState);
    }
}
