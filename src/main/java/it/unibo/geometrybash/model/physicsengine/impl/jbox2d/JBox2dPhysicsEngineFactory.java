package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.dynamics.Body;

import it.unibo.geometrybash.model.physicsengine.PhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;

/**
 * {@inheritDoc}
 * 
 * <p>
 * An implementation that uses JBox2d physics engine.
 * </p>
 */
public class JBox2dPhysicsEngineFactory implements PhysicsEngineFactory<Body> {

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
