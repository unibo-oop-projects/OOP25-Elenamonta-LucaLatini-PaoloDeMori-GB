package it.unibo.geometrybash.model.physicsengine;

import it.unibo.geometrybash.model.GameModel;

/**
 * A GameModel that uses a physics engine to execute the correct flow of the
 * game.
 * 
 *  @param <T> the body class that represents the objects in the physics engine.
 */
public interface GameModelWithPhysicsEngine<T> extends GameModel {

    /**
     * Sets the physics engine that orchestrate the game.
     * 
     * @param pEF The physics engine used by the game.
     * 
     * @see PhysicsEngine
     */
    void setPhysicsEngine(PhysicsEngineFactory<T> pEF);
}
