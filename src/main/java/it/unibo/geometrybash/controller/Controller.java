package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObserver;
import it.unibo.geometrybash.model.GameModel;

/**
 * The controller of the application.
 */
public interface Controller extends ModelObserver {
    /**
     * Returns the instance of the {@link GameModel} used by this class.
     * 
     * @return The instance of the model.
     * @see GameModel
     */
    GameModel getModel();

    /**
     * Returns the isntance of the {@link InputHandler}of this class.
     * 
     * @return The instance of the input handler
     * @see InputHandler
     */
    InputHandler getInputHandler();

    /**
     * Start the game.
     */
    void start();
}
