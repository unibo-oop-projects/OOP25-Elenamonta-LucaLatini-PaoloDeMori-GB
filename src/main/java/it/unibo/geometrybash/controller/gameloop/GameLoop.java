package it.unibo.geometrybash.controller.gameloop;

import it.unibo.geometrybash.controller.gameloop.exceptions.FpsNotCalculatedException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopConfigurationException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotOnPauseException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotStartedException;

/**
 * A interface to create gameloops.
 */
public interface GameLoop {
    /**
     * Starts the GameLoop.
     * 
     * @throws InvalidGameLoopStatusException if the gameloop has already been started
     * @throws InvalidGameLoopConfigurationException if the gameloop isn't correctly initialized
     */
    void start() throws InvalidGameLoopStatusException, InvalidGameLoopConfigurationException;

    /**
     * Pauses The GameLoop.
     * 
     * @throws NotStartedException if the gameloop never started
     */
    void pause() throws NotStartedException, InvalidGameLoopStatusException;

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
     * Sets the action to perform on every cycle.
     * 
     * @param action The action to perform on every cycle
     */
    void setUpdateAction(ActionOnLoopChange action);

    /**
     * Gets the number of Frames per second of my loop.
     * 
     * @return the number of Frames per second measured during the last second execution.
     * 
     * @throws FpsNotCalculatedException if a client tries to get the number of FPS but it has not been calculated yet.
     */
    short getFPS() throws FpsNotCalculatedException;

    /**
     * Gets a boolean that is set true if the gameloop's last exectuion terminated safely.
     * 
     * @return true if the gameloop was safely interrupted, false if it didn't.
     */
    boolean isTerminatedSafely();
}
