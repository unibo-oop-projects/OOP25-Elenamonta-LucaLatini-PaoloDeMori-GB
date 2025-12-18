package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewObserver;
import java.awt.event.KeyEvent;
/**
 * Interface for handling user input events originating from the view.
 *
 * <p>Implementations must translate raw input into application-specific actions
 * and forward them to the controller or model as appropriate.
 */

public interface InputHandler extends ViewObserver {

    /**
     * Sets the action to perform when the main key is pressed.
     *
     * @param action the action to perform
     */
    void setOnMainKeyPressed(OnInputEventAction action);

    /**
     * Sets the action to perform when the menu key is pressed.
     *
     * @param action the action to perform
     */
    void setOnMenuKeyPressed(OnInputEventAction action);

    /**
     * Sets the action to perform when resume is triggered from the GUI.
     *
     * @param action the action to perform
     */
    void setOnResumeKeyPressed(OnInputEventAction action);

    /**
     * Sets the action for a specific standard view event type.
     *
     * @param type the event type
     * @param action the action to perform
     * @throws IllegalArgumentException if type is GENERIC
     */
    void setActionForEvent(StandardViewEventType type, OnInputEventAction action);

    /**
     * Sets the handler for generic terminal commands.
     *
     * @param handler the handler for generic commands
     */
    void setGenericCommandHandler(OnGenericCommandAction handler);

    /**
     * Handles a keyboard input event.
     *
     * @param event the keyboard event
     */
    void handleKeyboardInput(KeyEvent event);
}
