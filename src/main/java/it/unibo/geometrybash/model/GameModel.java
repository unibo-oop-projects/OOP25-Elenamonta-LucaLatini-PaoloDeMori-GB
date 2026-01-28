package it.unibo.geometrybash.model;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObservable;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;

/**
 * The Business logic of the Game.
 * This interface offers a contract to access and modify the gamestatus and the
 * main entities of the game.
 * 
 */
public interface GameModel extends GameState, ModelObservable {

    /**
     * If possible sets the actual status of the game to Start.
     * 
     * <p>
     * Configures the model with the correct init data.
     * 
     * @param levelName the name of the level.
     * 
     * @throws InvalidModelMethodInvocationException if the method is called when
     *                                               the
     *                                               finite state machine is not in
     *                                               the correct state
     * 
     * @throws ModelExecutionException               if an error is thrown during
     *                                               the normal execution of the
     *                                               start method
     * 
     * @see Status
     */
    void start(String levelName) throws InvalidModelMethodInvocationException, ModelExecutionException;

    /**
     * If possible sets the actual status of the game to Pause.
     * 
     * @throws InvalidModelMethodInvocationException if the method is called when
     *                                               the
     *                                               finite state machine is not in
     *                                               the correct state
     * 
     * @see Status
     */
    void pause() throws InvalidModelMethodInvocationException;

    /**
     * If possible switches the actual status of the game from Pause to Running.
     * 
     * 
     * @throws InvalidModelMethodInvocationException if the method is called when
     *                                               the
     *                                               finite state machine is not in
     *                                               the correct state
     * 
     * @see Status
     */
    void resume() throws InvalidModelMethodInvocationException;

    /**
     * Resets all the fields and restart the game.
     * 
     * @throws ModelExecutionException if an error is thrown during
     *                                 the normal execution of the
     *                                 start method
     * 
     * @throws InvalidModelMethodInvocationException  if the method is called when
     *                                                the
     *                                                finite state machine is not in
     *                                                the correct state
     * 
     */
    void restart() throws InvalidModelMethodInvocationException, ModelExecutionException;

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

    /**
     * Creates the dto representing the level.
     * 
     * @return the dto representing the level.
     * @throws ModelExecutionException if the model is not configured correctly.
     */
    UpdateInfoDto toDto() throws ModelExecutionException;
}
