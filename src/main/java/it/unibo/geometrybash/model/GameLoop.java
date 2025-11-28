package it.unibo.geometrybash.model;

import it.unibo.geometrybash.model.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.model.exceptions.NotOnPauseException;
import it.unibo.geometrybash.model.exceptions.NotStartedException;

/**
 * A interface to create gameloops.
 */
public interface GameLoop {
    /**
     * Starts the GameLoop.
     * 
     * @throws InvalidGameLoopStatusException if the gameloop has already been started
     */
    void start() throws InvalidGameLoopStatusException;

    /**
     * Pause The GameLoop.
     * 
     * @throws NotStartedException if the gameloop never started
     */
    void pause() throws NotStartedException;

    /**
     * Resumes The GameLoop.
     * 
     * @throws NotOnPauseException if the gameloop is not paused
     * @throws NotStartedException if the gameloop never started
     */
    void resume() throws NotOnPauseException, NotStartedException;

    /**
     * Stops definetively the GameLoop.
     * 
     * @throws NotStartedException if the gameloop never started
     */
    void stop() throws NotStartedException;

    /**
     * Set an action to perform on every cycle.
     * 
     * @param action The action to perform on every cycle.
     */
    void setOnUpdate(ActionOnLoopChange action);

    /**
     * Set an action to perform on GameOver.
     * 
     * @param action The action to perform on Game Over
     */
    void setOnGameOver(ActionOnLoopChange action);
}
