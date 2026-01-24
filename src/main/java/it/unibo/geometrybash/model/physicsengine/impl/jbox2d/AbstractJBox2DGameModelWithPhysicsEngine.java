package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.dynamics.Body;

import it.unibo.geometrybash.model.AbstractGameModel;
import it.unibo.geometrybash.model.physicsengine.GameModelWithPhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;

/**
 * An Abstract implementation of the {@link GameModelWithPhysicsEngine}
 * interface that implements the getter and setter of the physics engine.
 */
public abstract class AbstractJBox2DGameModelWithPhysicsEngine extends AbstractGameModel
        implements GameModelWithPhysicsEngine<Body> {
    private PhysicsEngine<Body> physicsEngine;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPhysicsEngine(final PhysicsEngineFactory<Body> pEF) {
        this.physicsEngine = pEF.createPhysicsEngine();
    }

    /**
     * Let the subclasses access physicsEngine.
     * 
     * @return physicsEngine
     */
    protected PhysicsEngine<Body> getPhysicsEngine() {
        return this.physicsEngine;
    }

}
