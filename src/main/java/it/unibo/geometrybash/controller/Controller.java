package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObserver;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.view.View;

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
     * Returns the instance of the {@link View} used by this class.
     * 
     * @return The instance of the view.
     * @see View
     */
    View getView();

    /**
     * Start the game.
     */
    void start();
}
