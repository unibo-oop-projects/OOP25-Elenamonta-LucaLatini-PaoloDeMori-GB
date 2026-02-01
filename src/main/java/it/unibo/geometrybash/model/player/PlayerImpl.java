package it.unibo.geometrybash.model.player;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
    private static final double TWO_PI = Math.PI * 2.0;
    private static final double ANGULAR_SPEED_RAD_S = Math.toRadians(720.0);
    private final PowerUpManager powerUpManager;
    private Skin skin;
    private boolean dead;
    private PlayerPhysics physics;
    private int coins;
    private OnDeathExecute onDeath;
    private double rotationRad;
    private final int numVertices;
    private final double stepAngle;
    private Consumer<GameObject<?>> onSpecialObjectCollision;

    /**
     * Creates a new {@code PlayerImpl} instance with a position, hitbox, and
     * physics component.
     *
     * @param position the initial position of the player in the game world
     */
    public PlayerImpl(final Vector2 position) {
        super(position);
        this.hitBox = createHitBox();
        this.powerUpManager = new PowerUpManager();
        this.skin = new Skin();
        this.coins = 0;
        this.physics = null;
        this.dead = false;
        this.numVertices = this.hitBox.getVertices().size();
        this.stepAngle = TWO_PI / numVertices;
        this.rotationRad = 0.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final float deltaTime) {
        this.powerUpManager.update(deltaTime);
        getNotEmptyPhysics().setVelocity(this.powerUpManager.getSpeedMultiplier());
        this.position = getNotEmptyPhysics().getPosition(hitBox);
        if (!this.physics.isGrounded()) {
            // player rotate with a angular rotation of 4PI/s
            this.rotationRad += ANGULAR_SPEED_RAD_S * deltaTime;
            this.rotationRad = normalizeAngle(this.rotationRad);
        } else {
            this.rotationRad = normalizeAngle(this.rotationRad);
            // take the nearest approximation of possible orientation values (multiples of
            // stepAngle)
            final double k = Math.round(this.rotationRad / stepAngle);
            this.rotationRad = k * stepAngle;
        }
    }

    // normalize angle to the [0, 2PI) range
    private double normalizeAngle(double angle) {
        angle %= TWO_PI;
        if (angle < 0) {
            angle += TWO_PI;
        }
        return angle;
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
        this.dead = true;
        this.coins = 0;
        this.powerUpManager.reset();
        this.onDeath.onDeath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void respawn(final Vector2 position) {
        if (this.dead) {
            getNotEmptyPhysics().resetBodyTo(position);
            this.position = position;
            this.dead = false;
        }
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
    public void setOnSpecialObjectCollision(final Consumer<GameObject<?>> onSpecialObjectCollision) {
        this.onSpecialObjectCollision = Objects.requireNonNull(onSpecialObjectCollision);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onShieldCollected(final GameObject<?> shield) {
        powerUpManager.activateShield();
        this.onSpecialObjectCollision.accept(shield);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSpeedBoostCollected(final GameObject<?> speedBoost, final float multiplier, final float duration) {
        powerUpManager.applySpeedModifier(multiplier, duration);
        this.onSpecialObjectCollision.accept(speedBoost);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSpikeCollision(final Spike obstacle) {
        if (powerUpManager.isShielded()) {
            powerUpManager.consumeShield();
            obstacle.setActive(false);
            this.onSpecialObjectCollision.accept(obstacle);
        } else if (!this.dead) {
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
    public Player<HitBox> copy() {
        final PlayerImpl copy = new PlayerImpl(new Vector2(position.x(), position.y()));
        copy.coins = this.coins;
        copy.skin = this.skin;
        copy.dead = false;
        copy.rotationRad = this.rotationRad;
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyGroundContactBegin() {
        getNotEmptyPhysics().onGroundContactBegin();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyGroundContactEnd() {
        getNotEmptyPhysics().onGroundContactEnd();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(value = "EI2", justification = ""
            + "The reference to PlayerPhysics is intentionally stored as part of a one-time binding. "
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnDeath(final OnDeathExecute onDeath) {
        this.onDeath = Objects.requireNonNull(onDeath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAngularRotation() {
        return this.rotationRad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInnerColor(final int innerColor) {
        this.skin = this.skin.withColors(innerColor, this.skin.getOuterColor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOuterColor(final int outerColor) {
        this.skin = this.skin.withColors(this.skin.getInnerColor(), outerColor);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDead() {
        return this.dead;
    }
}
