package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.dynamics.Body;

import it.unibo.geometrybash.model.physicsengine.PhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;

public class JBox2dPhysicsEngineFactory implements PhysicsEngineFactory<Body>{

    @Override
    public PhysicsEngine<Body> createPhysicsEngine() {
        return new Jbox2DPhysicsEngineImpl();
    }
    
}
