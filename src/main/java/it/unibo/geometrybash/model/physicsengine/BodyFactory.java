package it.unibo.geometrybash.model.physicsengine;

import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.powerup.PowerUp;

/**
 * A factory that creates JBox2d instances.
 * 
 * @param <T> the body class that represents the objects in the physics engine.
 */
public interface BodyFactory<T> {

    /**
     * Create the Obstacle representation in the physics engine.
     * 
     * @param obj the obstacle to represent in the physical world
     * @return the obstacle just created
     */
    T createObstacle(Obstacle obj);

    /**
     * Create the powerup representation in the physics engine.
     * 
     * @param obj the powerup to represent in the physical world
     * @return the powerup just created
     */
    T createPowerUp(PowerUp<? extends Shape> obj);

    /**
     * Create the player representation in the physics engine.
     * 
     * @param p the player to represent in the physical world
     * @return the player just created
     */
    T createPlayer(Player<? extends Shape> p);
}
