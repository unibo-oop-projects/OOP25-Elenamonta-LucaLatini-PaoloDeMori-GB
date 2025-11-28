package it.unibo.geometrybash.controller;


/**
 * This interface represents an action that has to be executed after an input received.
 * 
 * @see InputHandler
 * 
 */
@FunctionalInterface
public interface OnInputEventAction {
    /**
     * The action to perform.
     */
    void executeAction();
}
