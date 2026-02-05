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
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;
import it.unibo.geometrybash.model.physicsengine.exception.InvalidPhysicsEngineConfiguration;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerWithPhysics;
import it.unibo.geometrybash.model.powerup.PowerUp;

/**
 * Implementation of PhysicsEngine using JBox2d.
 *
 * @see PhysicsEngine
 */
public class Jbox2DPhysicsEngineImpl implements PhysicsEngine<Body> {

    private static final Vector2 GRAVITY = JBox2DValues.GRAVITY;

    private static final int VELOCITY_ITERATIONS = JBox2DValues.VELOCITY_ITERATIONS;

    private static final int POSITION_ITERATIONS = JBox2DValues.POSITION_ITERATIONS;

    private final Map<GameObject<?>, Body> physicsToModelLink = new HashMap<>();
    private final List<GameObject<?>> toRemove = new LinkedList<>();
    private BodyFactory<Body> bF;
    private World world;

    /**
     * The constructor of this class.
     */
    public Jbox2DPhysicsEngineImpl() {
        this.world = new World(new Vec2(GRAVITY.x(), GRAVITY.y()));
        bF = new BodyFactoryImpl(world);
        this.world.setContactListener(new CollisionHandler());
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
        } else {
            throw new InvalidPhysicsEngineConfiguration("Trying to add to the world an invalid game object");
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(final PlayerWithPhysics obj) {
        final Body b;
        final PlayerPhysics pPh;
        b = bF.createPlayer((Player<?>) obj);
        physicsToModelLink.put(obj, b);
        pPh = new PlayerPhysicsImpl(b);
        obj.bindPhysics(pPh);
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
        }
        toRemove.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.world = new World(new Vec2(GRAVITY.x(), GRAVITY.y()));
        this.world.setContactListener(new CollisionHandler());
        bF = new BodyFactoryImpl(world);
        this.physicsToModelLink.clear();
        this.toRemove.clear();
    }
}
