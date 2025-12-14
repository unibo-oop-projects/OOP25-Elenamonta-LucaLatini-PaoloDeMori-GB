package it.unibo.geometrybash.view;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;

/**
 * The interface to implement to create a view of the game.
 * 
 * @see it.unibo.geometrybash.controller.Controller
 */
public interface View {

    /**
     * Method to init the view.
     */
    void init();

    /**
     * Method to show the view on screen.
     * 
     * @throws NotStartedViewException if the view wasn't initialized correctly
     */
    void show() throws NotStartedViewException;

    /**
     * Method to hide and stop the execution of the view.
     * 
     * @throws NotStartedViewException if the view wasn't initialized correctly
     */
    void stop() throws NotStartedViewException;

    /**
     * Method called by the controller to update the view.
     * 
     * <p>It should be called by the controller when it receives the update of a gameloop cycle,
     *  by the model that it observes.
     * 
     * @param dto the Data Transfer Object that contains the information of the game state.
     * 
     * @throws NotStartedViewException if the view wasn't initialized correctly
     * @throws ExecutionWithIllegalThreadException if the view is not being updated on a dedicated thread
     * @see it.unibo.geometrybash.controller.Controller
     */
    void update(UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException;

    /**
     * Method called by the Controller to switch between completely different scenes.
     * 
     * <p> This method is called to switch from the current visualization to a completely different scene.
     * 
     * @param scene the scene to switch to.
     * @throws NotStartedViewException if the view wasn't initialized correctly
     */
    void changeView(ViewScene scene) throws NotStartedViewException;
}
