package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.dynamics.Body;

import it.unibo.geometrybash.model.physicsengine.PhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;

/**
 * A factory interface to create a JBox2D physics engine.
 * 
 * 
 * <p>
 * An implementation of {@link PhysicsEngineFactory} that uses JBox2d physics
 * engine.
 * </p>
 */
public class JBox2DPhysicsEngineFactory implements PhysicsEngineFactory<Body> {

    /**
     * Default constructor.
     */
    public JBox2DPhysicsEngineFactory() {
        // default constructor.
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * An implementation that uses JBox2d physics engine.
     * </p>
     */
    @Override
    public PhysicsEngine<Body> createPhysicsEngine() {
        return new Jbox2DPhysicsEngineImpl();
    }

}
