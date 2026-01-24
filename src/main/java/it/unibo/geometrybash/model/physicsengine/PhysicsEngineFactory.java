package it.unibo.geometrybash.model.physicsengine;

/**
 * A factory interface to create PhysicsEngines.
 * 
 * @param <T> the body class that represents the objects in the physics engine.
 */
@FunctionalInterface
public interface PhysicsEngineFactory<T> {
    /**
     * Creates a physics engine.
     * 
     * @return the physics engine.
     */
    PhysicsEngine<T> createPhysicsEngine();
}
