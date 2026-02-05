package it.unibo.geometrybash.model.player;

import java.util.function.Consumer;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Spike;

/**
 * Represents the player entity in the game.
 *
 * <p>
 * The {@code Player} interface defines the core behavior and state of a
 * player-controlled
 * entity in game.
 * </p>
 *
 * @param <S> the type of shape used for collision detection
 */
public interface Player<S extends Shape> extends GameObject<HitBox> {

    /**
     * Makes the player jump, applying the appropriate vertical impulse.
     *
     * <p>
     * The movement logic should be delegated to the physics component to update the
     * player's position.
     * </p>
     */
    void jump();

    /**
     * Handles the player death event.
     *
     * <p>
     * Triggers an immediate respawn at the initial position and resetting all
     * temporary effects.
     * </p>
     */
    void die();

    /**
     * Respawns the player at a given position.
     *
     * @param position the position where the player should respawn
     */
    void respawn(Vector2 position);

    /**
     * Increments the number of collected coins by a given value.
     *
     * @param coin the power up that collides with the player
     * @param value the number of coins to add
     */
    void addCoin(GameObject<?> coin, int value);

    /**
     * Returns the current number of coins collected by the player.
     *
     * @return the total coins
     */
    int getCoins();

    /**
     * Sets the action to execute when the player collides with a game object with a
     * peculiar behavior.
     *
     * @param onSpecialObjectCollision the consumer that accepts the object with a
     *                                 peculiar behavior
     */
    void setOnSpecialObjectCollision(Consumer<GameObject<?>> onSpecialObjectCollision);

    /**
     * Activates a shield effect for the player.
     *
     * <p>
     * The shield protects the player from the next fatal collision.
     * This method should delegate the effect to the internal power-up manager.
     * </p>
     *
     * @param shield the object that collides with the player
     */
    void onShieldCollected(GameObject<?> shield);

    /**
     * Applies a speed boost effect to the player.
     *
     * <p>
     * This method should delegate the effect to the internal power-up manager.
     * </p>
     *
     * @param speedBoost the speedboost powerup whitch collides with player
     * @param multiplier the speed multiplier
     * @param duration   the duration of the effect in seconds
     */
    void onSpeedBoostCollected(GameObject<?> speedBoost, float multiplier, float duration);

    /**
     * Called when the player collides with a deadly obstacle.
     *
     * <p>
     * The player should internally check if any shield is active to absorb the hit,
     * or otherwise trigger the death sequence.
     * </p>
     *
     * @param obstacle the spike obstacle whitch collides with player
     */
    void onSpikeCollision(Spike obstacle);

    /**
     * Returns the current speed multiplier applied to the player.
     *
     * @return the speed multiplier
     */
    float getSpeedMultiplier();

    /**
     * Checks whether the player currently has an active shield.
     *
     * @return {@code true} if shielded, {@code false} otherwise
     */
    boolean isShielded();

    /**
     * Notifies the player that a ground contact has started.
     */
    void notifyGroundContactBegin();

    /**
     * Notifies the player that a ground contact has ended.
     */
    void notifyGroundContactEnd();

    /**
     * Returns the currently assigned skin of the player.
     *
     * @return the player's skin, or {@code null} if none is set
     */
    Skin getSkin();

    /**
     * Sets the player's skin.
     *
     * @param skin the skin to assign to the player
     */
    void setSkin(Skin skin);

    /**
     * Set onDeath param.
     *
     * @param onDeath the functional interface to set
     */
    void setOnDeath(OnDeathExecute onDeath);

    /**
     * Returns the rotation angle computed using the delta time and normalized to a
     * valid range.
     *
     * @return the player rotation angle
     */
    double getAngularRotation();

    /**
     * Sets the inner color of the player's skin.
     *
     * @param innerColor the ARGB color value to use for the inner part of the
     *                   player
     */
    void setInnerColor(int innerColor);

    /**
     * Sets the outer color of the player's skin.
     *
     * @param outerColor the ARGB color value to use for the outer part of the
     *                   player
     */
    void setOuterColor(int outerColor);

    /**
     * Return true if the player died.
     *
     * @return true if the player died.
     */
    boolean isDead();

    /**
     * Returns a defensive copy of this Player.
     *
     * @return a new Player instance with the same state
     */
    @Override
    Player<HitBox> copy();
}
