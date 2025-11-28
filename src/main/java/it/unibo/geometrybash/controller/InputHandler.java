package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewObserver;

/**
 * A class that Handle every kind of input from the user, each keyboard input and gui input.
 *
 */
public interface InputHandler extends ViewObserver {
    /**
     * Set the action to perform when the main key is pressed.
     * 
     * @param action the action to perform
     * @see OnInputEventAction
     */
    void setOnMainKeyPressed(OnInputEventAction action);

    /**
     * Set the action to perform when the menu key is pressed.
     * 
     * @param action the action to perform
     * @see OnInputEventAction
     */
    void setOnMenuKeyPressed(OnInputEventAction action);

    /**
     * Set the action to perform when the resume key is pressed.
     * 
     * @param action the action to perform
     * @see OnInputEventAction
     */
    void setOnResumeKeyPressed(OnInputEventAction action);
}
