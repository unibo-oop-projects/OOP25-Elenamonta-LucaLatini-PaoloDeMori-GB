package it.unibo.geometrybash.model.player;

import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.core.Updatable;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physics.PlayerPhysics;
import it.unibo.geometrybash.model.powerup.PowerUpManager;

/**
 * Concrete implementation of a player-controlled entity in the game.
 *
 * <p>
 * The {@code PlayerImpl} does not directly modify physics bodies; it delegates
 * movement and collision response to {@link PlayerPhysics}. This allows separation
 * of game logic from the underlying physics engine.
 * </p>
 *
 * <p>
 * All temporary effects are managed internally and updated via the {@link #update(float)} method.
 * </p>
 */
public class PlayerImpl extends AbstractGameObject<HitBox> implements Player, Updatable {

    /**
     *  Manages all temporary power-up effects for the player.
     */
    private final PowerUpManager powerUpManager;

    /**
     * The physics component handle all physics interactions.
     */
    private final PlayerPhysics physics;

    /**
     * The total number of coins currently collected by the player.
     */
    private int coins;

    /**
     * The visual representation or skin currently assigned to the player.
     */
    private Skin skin;

    /**
     * Creates a new {@code PlayerImpl} instance with a position, hitbox, and physics component.
     *
     * @param position the initial position of the player in the game world
     * @param hitBox the collision hitbox associated with the player
     * @param physics the physics component responsible for movement and collisions
     */
    protected PlayerImpl(final Vector2 position, final HitBox hitBox, final PlayerPhysics physics) {
        super(position, hitBox);
        this.physics = physics;
        this.powerUpManager = new PowerUpManager();
        this.coins = 0;
        this.skin = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.powerUpManager.update(deltaTime);
        this.physics.setVelocity(this.powerUpManager.getSpeedMultiplier());
        this.position = this.physics.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jump() {
        this.physics.applyJump();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void die() {
        this.coins = 0;
        this.powerUpManager.reset();
        respawn(new Vector2(0f, 0f));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void respawn(final Vector2 position) {
        this.physics.resetBodyTo(position);
        this.position = position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCoin(final int value) {
        this.coins += value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCoins() {
        return this.coins;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onShieldCollected() {
        powerUpManager.activateShield();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSpeedBoostCollected(final float multiplier, final float duration) {
        powerUpManager.applySpeedModifier(multiplier, duration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSpikeCollision() {
        if (powerUpManager.isShielded()) {
            powerUpManager.consumeShield();
        } else {
            die();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getSpeedMultiplier() {
        return powerUpManager.getSpeedMultiplier();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShielded() {
        return powerUpManager.isShielded();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Skin getSkin() {
        return this.skin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSkin(final Skin skin) {
        this.skin = skin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject<HitBox> copy() {
        final PlayerImpl copy = new PlayerImpl(new Vector2(position.x(), position.y()), hitBox, physics);
        copy.coins = this.coins;
        copy.skin = this.skin;
        return copy;
    }

}
