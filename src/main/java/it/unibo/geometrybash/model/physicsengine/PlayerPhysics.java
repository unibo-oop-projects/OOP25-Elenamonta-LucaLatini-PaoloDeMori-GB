package it.unibo.geometrybash.model.physicsengine;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Defines the physics-related operations of a player entity.
 *
 * <p>
 * This interface represents the contract between the game logic layer
 * and the physics system. It exposes movement, state query, and body
 * manipulation operations without requiring the game logic to depend
 * on a specific physics engine implementation.
 * </p>
 */
public interface PlayerPhysics {

    /**
     * Applies a vertical impulse to make the player jump.
     *
     * <p>
     * The method triggers a jump action by modifying the physical state
     * of the player in the physics simulation.
     * </p>
     */
    void applyJump();

    /**
     * Scales the player's current linear velocity by the specified factor.
     *
     * <p>
     * The resulting velocity is computed by applying the multiplier to
     * the current velocity components.
     * </p>
     *
     * @param multiplier the factor used to scale the current velocity
     */
    void setVelocity(float multiplier);

    /**
     * Returns the current linear velocity of the player.
     *
     * <p>
     * The velocity is expressed using a domain-level vector type,
     * independent from the underlying physics engine.
     * </p>
     *
     * @return the player's current velocity
     */
    Vector2 getVelocity();

    /**
     * Sets the player's physical state to a new position.
     *
     * <p>
     * This operation updates the player's position in the physics world
     * and resets its dynamic state.
     * </p>
     *
     * @param pos the new position of the player
     */
    void resetBodyTo(Vector2 pos);

    /**
     * Returns the position of the player.
     *
     * @param hB represent the hitBox of teh player
     * @return the body's position of the player
     */
    Vector2 getPosition(HitBox hB);

    /**
     * Associates arbitrary user-defined data with the player's physical body.
     *
     * @param userData the object to associate with the physics body
     */
    void setUserData(Object userData);

    /**
     * Checks whether the player is currently in contact with the ground.
     *
     * @return {@code true} if the player has at least one active ground contact,
     *         {@code false} otherwise
     */
    boolean isGrounded();

    /**
     * Registers the beginning of a ground contact.
     *
     * <p>
     * Updates the internal ground contact counter used to determine whether the
     * player is currently grounded.
     * </p>
     */
    void onGroundContactBegin();

    /**
     * Registers the end of a ground contact.
     *
     * <p>
     * Updates the internal ground contact counter by removing an active ground
     * contact.
     * </p>
     */
    void onGroundContactEnd();

    /**
     * Sets active param of the physics body.
     *
     * @param activeState the current state of player body
     */
    void setActive(boolean activeState);
}
