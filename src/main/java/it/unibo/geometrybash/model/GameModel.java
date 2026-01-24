package it.unibo.geometrybash.model;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObservable;

/**
 * The Business logic of the Game.
 * This interface offers a contract to access and modify the gamestatus and the main entities of the game.
 * 
 */
public interface GameModel extends GameState, ModelObservable {
    /**
     * If possible sets the actual status of the game to Pause.
     * 
     * @see Status
     */
    void pause();

    /**
     * If possible switches the actual status of the game from Pause to Running.
     * 
     * @see Status
     */
    void resume();

    /**
     * Resets all the fields and restart the game.
     */
    void restart();

    /**
     * Tries to make the player jump.
     * 
     */
    void jumpSignal();

    /**
     * Updates the model.
     * 
     * @param deltaTime the time elapsed since last update.
     */
    void update(float deltaTime);
}
