package it.unibo.geometrybash.model;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObservable;

/**
 * The Business logic of the Game.
 * This interface offers a contract to access and modify the gamestatus and the main entities of the game.
 * 
 * @see GameLoop
 */
public interface GameModel extends ModelObservable {
    /**
     * Return the actual status of the game.
     * 
     * @return The status of the game.
     * @see Status
     */
    Status getStatus();

    /**
     * Updates the status of the game.
     * 
     * <p>The implementation of this method receive a {@link Status} instance as input
     *  evaluate the transition from the previous status and perform necessary actions before applying the new status.
     * 
     * @param status The new status.
     */
    void setStatus(Status status);

    /**
     * Starts for the first time the GameLoop.
     * 
     * @see GameLoop
     */
    void startGameLoop();

    /**
     * Returns the player of the game.
     * 
     * @return The player of the game.
     */
    Player getPlayer();

    /**
     * Returns the level of the game.
     * 
     * @return the level of the game.
     */
    Level getLevel();
}
