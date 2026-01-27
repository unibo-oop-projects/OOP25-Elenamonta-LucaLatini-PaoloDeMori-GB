package it.unibo.geometrybash.model;

import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.player.Player;

/**
 * The model state.
 */
public interface GameState {
    /**
     * Returns the player of the game.
     *
     * @return The player of the game.
     * @throws ModelExecutionException if the player isn't initialized
     */
    Player<? extends Shape> getPlayer() throws ModelExecutionException;

    /**
     * Returns the level of the game.
     *
     * @return the level of the game.
     */
    Level getLevel();

    /**
     * Returns the actual status of the game.
     *
     * @return The status of the game.
     * @see Status
     */
    Status getStatus();

}
