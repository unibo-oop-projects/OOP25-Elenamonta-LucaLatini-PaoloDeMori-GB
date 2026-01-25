package it.unibo.geometrybash.model.player;

import java.util.List;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.core.Updatable;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Spike;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;
import it.unibo.geometrybash.model.powerup.PowerUpManager;

/**
 * Concrete implementation of a player-controlled entity in the game.
 *
 * <p>
 * The {@code PlayerImpl} does not directly modify physics bodies; it delegates
 * movement and collision response to {@link PlayerPhysics}. This allows
 * separation
 * of game logic from the underlying physics engine.
 * </p>
 *
 * <p>
 * All temporary effects are managed internally and updated via the
 * {@link #update(float)} method.
 * </p>
 */
public class PlayerImpl extends AbstractGameObject<HitBox> implements PlayerWithPhysics, Updatable {

    private static final float SIZE = 1.0f;
    private final PowerUpManager powerUpManager;
    private PlayerPhysics physics;
    private int coins;
    private Skin skin;

    /**
     * Creates a new {@code PlayerImpl} instance with a position, hitbox, and
     * physics component.
     *
     * @param position the initial position of the player in the game world
     * @param hitBox   the collision hitbox associated with the player
     */
    public PlayerImpl(final Vector2 position, final HitBox hitBox) {
        super(position, createHitBox());
        this.powerUpManager = new PowerUpManager();
        this.coins = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.powerUpManager.update(deltaTime);
        getNotEmptyPhysics().setVelocity(this.powerUpManager.getSpeedMultiplier());
        this.position = getNotEmptyPhysics().getPosition(hitBox);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jump() {
        getNotEmptyPhysics().applyJump();
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
        getNotEmptyPhysics().resetBodyTo(position);
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
    public void onSpikeCollision(final Spike obstacle) {
        if (powerUpManager.isShielded()) {
            powerUpManager.consumeShield();
            obstacle.setActive(false);
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
        final PlayerImpl copy = new PlayerImpl(new Vector2(position.x(), position.y()), createHitBox());
        copy.coins = this.coins;
        copy.skin = this.skin;
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(value = "EI2",
                        justification = "The reference to PlayerPhysics is intentionally stored as part of a one-time binding. "
                                + "The method enforces immutability of the association by preventing reassignment "
                                + "through an explicit state check inside the method. ")
    @Override
    public void bindPhysics(final PlayerPhysics phy) {
        /*
         * This check ensures that the physics component is bound exactly once.
         * The physical representation is assigned during the physics engine
         * initialization.
         */
        if (this.physics != null) {
            throw new IllegalStateException("Physics already bound");
        }
        this.physics = Objects.requireNonNull(phy);
    }

    private static HitBox createHitBox() {
        return new HitBox(
                List.of(new Vector2(0, 0), new Vector2(SIZE, 0), new Vector2(SIZE, SIZE), new Vector2(0, SIZE)));
    }

    private PlayerPhysics getNotEmptyPhysics() {
        return Objects.requireNonNull(
                physics,
                "Player physics not bound yet");
    }
}
