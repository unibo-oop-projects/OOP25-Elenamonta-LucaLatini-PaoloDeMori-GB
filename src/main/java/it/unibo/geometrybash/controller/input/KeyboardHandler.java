package it.unibo.geometrybash.controller.input;

import java.awt.event.KeyEvent;

import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Interface for handling keyboard events.
 */
public interface KeyboardHandler {

    /**
     * Handles a key pressed event.
     * 
     * @param event the key event
     */
    void handleKeyPressed(KeyEvent event);

    /**
     * Sets the action to complete when main key is pressed.
     * 
     * @param action th action to execute
     */
    void setMainKeyAction(OnInputEventAction action);

    /**
     * Sets the action to execute when the menu key is pressed.
     * 
     * @param action the action to perform
     */
    void setMenuKeyAction(OnInputEventAction action);
}
