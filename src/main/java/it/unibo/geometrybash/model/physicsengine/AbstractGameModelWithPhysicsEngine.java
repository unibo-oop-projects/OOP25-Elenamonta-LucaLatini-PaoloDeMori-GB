package it.unibo.geometrybash.model.physicsengine;

import it.unibo.geometrybash.model.AbstractGameModel;

/**
 * An Abstract implementation of the {@link GameModelWithPhysicsEngine}
 * interface that implements the getter and setter of the physics engine.
 * 
 * @param <T> the type of entities used by the physics engine.
 */
public abstract class AbstractGameModelWithPhysicsEngine<T> extends AbstractGameModel
        implements GameModelWithPhysicsEngine<T> {
    private PhysicsEngine<T> physicsEngine;

    /**
     * Default Constructor.
     */
    public AbstractGameModelWithPhysicsEngine() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPhysicsEngine(final PhysicsEngineFactory<T> pEF) {
        this.physicsEngine = pEF.createPhysicsEngine();
    }

    /**
     * Let the subclasses access physicsEngine.
     * 
     * @return physicsEngine
     */
    protected PhysicsEngine<T> getPhysicsEngine() {
        return this.physicsEngine;
    }

}
