package it.unibo.geometrybash.view;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewObservable;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * The interface to implement to create a view of the game.
 *
 * @see it.unibo.geometrybash.controller.Controller
 */
public interface View extends ViewObservable {

    /**
     * Method to init the game panel view.
     *
     * @param resolution resolutions for the game
     */
    void init(GameResolution resolution);

    /**
     * Method to show the view on screen.
     *
     * @throws NotStartedViewException if the view wasn't initialized correctly
     */
    void show() throws NotStartedViewException;

    /**
     * Method called by the controller to update the view.
     *
     * <p>
     * It should be called by the controller when it receives the update of a
     * gameloop cycle,
     * by the model that it observes.
     *
     * @param dto the Data Transfer Object that contains the information of the game
     *            state.
     *
     * @throws NotStartedViewException             if the view wasn't initialized
     *                                             correctly
     * @throws ExecutionWithIllegalThreadException if the view is not being updated
     *                                             on a dedicated thread
     * @see it.unibo.geometrybash.controller.Controller
     */
    void update(UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException;

    /**
     * Method called by the Controller to switch between completely different
     * scenes.
     *
     * <p>
     * This method is called to switch from the current visualization to a
     * completely different scene.
     *
     * @param scene the scene to switch to.
     */
    void changeView(ViewScene scene);

    /**
     * Method called by the controller to dispose the view.
     */
    void disposeView();

    /**
     * Methos called by the controller to show the victory message with the total number of coins collected.
     *
     * @param coins number of coins taken by the player
     * @param totalCoins total number of coins in the level
     */
    void victory(int coins, int totalCoins);

    /**
     * Methos called by the controller to show pause menu.
     */
    void pause();

    /**
     * Method called by the controller to show an error message when a command is not found.
     *
     * @param command represent the command typed from the user
     */
    void showCommandsError(String command);

    /**
     * Methos called by the controller to show all options to reset resolution of the screen.
     */
    void showResolutionOptions();

    /**
     * Methos called by the controller to show error message when an error occurs during game execution.
     *
     * @param executionError the description of the error occurred
     */
    void showExecutionError(String executionError);
}
