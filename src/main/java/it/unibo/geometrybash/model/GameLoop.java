package it.unibo.geometrybash.model;

import it.unibo.geometrybash.model.exceptions.FpsNotCalculatedException;
import it.unibo.geometrybash.model.exceptions.InvalidGameLoopConfigurationException;
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
    void start() throws InvalidGameLoopStatusException, InvalidGameLoopConfigurationException;

    /**
     * Pause The GameLoop.
     * 
     * @throws NotStartedException if the gameloop never started
     */
    void pause() throws NotStartedException,InvalidGameLoopStatusException;

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
     * Set the action to perform on every cycle.
     * 
     * @param action The action to perform on every cycle
     */
    void setUpdateAction(ActionOnLoopChange action);

    /**
     * Get the number of Frames per second of my loop.
     * 
     * @throws FpsNotCalculatedException if a client tries to get the number of FPS but it has not been calculated yet.
     * @return the number of Frames per second measured during the last second execution.
     */
    int getFPS() throws FpsNotCalculatedException;
}
