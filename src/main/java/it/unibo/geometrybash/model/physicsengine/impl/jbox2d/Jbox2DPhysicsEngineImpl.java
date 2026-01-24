package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.physicsengine.BodyFactory;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.exception.InvalidPhysicsEngineConfiguration;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.powerup.PowerUp;

/**
 * Implementation of PhysicsEngine using JBox2d.
 * 
 * @see PhysicsEngine
 */
public class Jbox2DPhysicsEngineImpl implements PhysicsEngine<Body> {
    /**
     * The gravity of the {@link World}.
     */
    public static final Vector2 GRAVITY = new Vector2(0f, -9.8f);
    /**
     * The Velocity Iterations to use during the
     * {@link World#step(float, int, int)}.
     */
    public static final int VELOCITY_ITERATIONS = 3;
    /**
     * The Position Iterations to use during the
     * {@link World#step(float, int, int)}.
     */
    public static final int POSITION_ITERATIONS = 3;

    private final Map<GameObject<?>, Body> physicsToModelLink = new HashMap<>();
    private final List<GameObject<?>> toRemove = new LinkedList<>();
    private final List<GameObject<?>> updatableObjects = new LinkedList<>();
    private BodyFactory<Body> bF;
    private World world;

    /**
     * The constructor of this class.
     */
    public Jbox2DPhysicsEngineImpl() {
        this.world = new World(new Vec2(GRAVITY.x(), GRAVITY.y()));
        bF = new BodyFactoryImpl(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addGameObject(final GameObject<?> obj) {
        final Body b;
        if (obj instanceof Obstacle) {
            b = bF.createObstacle((Obstacle) obj);
            physicsToModelLink.put(obj, b);
        } else if (obj instanceof PowerUp) {
            b = bF.createPowerUp((PowerUp<?>) obj);
            physicsToModelLink.put(obj, b);
        } else if (obj instanceof Player) {
            b = bF.createPlayer((Player<?>) obj);
            physicsToModelLink.put(obj, b);
            addToUpdatableObject(obj);
        } else {
            throw new InvalidPhysicsEngineConfiguration("Trying to add to the world an invalid game object");
        }

    }

    /**
     * Add a GameObject to the list of objects to synchronize after the world
     * update.
     * 
     * @param gO the {@link GameObject} to add
     */
    private void addToUpdatableObject(final GameObject<?> gO) {
        updatableObjects.add(gO);
    }

    /**
     * Remove a GameObject from the list of objects to synchronize after the world
     * update.
     * 
     * @param gO the {@link GameObject} to remove
     */
    public void removeUpdatableObject(final GameObject<?> gO) {
        updatableObjects.remove(gO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeGameObject(final GameObject<?> obj) {
        this.toRemove.add(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePhysicsEngine(final float deltaTime) {
        world.step(deltaTime, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        clearToRemove();
    }

    /**
     * Apply the safe elimination of the items in the list that contains the item to
     * remove from the world.
     */
    private void clearToRemove() {
        for (final GameObject<?> gameObject : toRemove) {
            final Body b = physicsToModelLink.remove(gameObject);
            if (b != null) {
                world.destroyBody(b);
            }
            if (updatableObjects.contains(gameObject)) {
                updatableObjects.remove(gameObject);
            }
        }
        toRemove.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void synchronizeGameEntitiesWithPhysicsEntities() {
        for (final GameObject<?> gameObject : updatableObjects) {
            final Body b = physicsToModelLink.get(gameObject);
            final Vec2 bodyPosition = b.getPosition();
            final float bodyX = bodyPosition.x - (gameObject.getHitBox().getWidth() / 2f);
            final float bodyY = bodyPosition.y - (gameObject.getHitBox().getHeight() / 2f);
            gameObject.setPosition(new Vector2(bodyX, bodyY));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.world = new World(new Vec2(GRAVITY.x(), GRAVITY.y()));
        bF = new BodyFactoryImpl(world);
        this.physicsToModelLink.clear();
        this.toRemove.clear();
        this.updatableObjects.clear();
    }
}
