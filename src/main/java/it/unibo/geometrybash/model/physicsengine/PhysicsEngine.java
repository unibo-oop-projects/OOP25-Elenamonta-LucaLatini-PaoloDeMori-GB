package it.unibo.geometrybash.model.physicsengine;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.player.PlayerWithPhysics;

/**
 * A physics engine that handle the game.
 * 
 * @param <T> the body class that represents the objects in the physics engine.
 */
public interface PhysicsEngine<T> {

    /**
     * A method to add the equivalent version of a gameObject in the physics-engine
     * world
     * and save the correspondence of the GameObject with its physiscs-world
     * version.
     * 
     * @param obj the GameObject to create in the physics-engine world
     */
    void addGameObject(GameObject<?> obj);

    /**
     * A method to remove the equivalent version of a gameObject from the
     * physics-engine world
     * and remove the correspondence of the GameObject with its physics-world
     * version.
     * 
     * @param obj the GameObject to remove in the physics-engine world
     */
    void removeGameObject(GameObject<?> obj);

    /**
     * A method to update the physics-engine.
     * 
     * @param deltaTime the time elapsed since the last update.
     */
    void updatePhysicsEngine(float deltaTime);

    /**
     * A method to add the equivalent version of a player in the physics-engine
     * world
     * and save the correspondence of the GameObject with its physiscs-world
     * version.
     * 
     * @param obj the player to create in the physics-engine world
     */
    void addPlayer(PlayerWithPhysics obj);

    /**
     * Resets the physics engine.
     */
    void reset();
}
