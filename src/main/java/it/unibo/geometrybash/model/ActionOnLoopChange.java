package it.unibo.geometrybash.model;

/**
 * This interface represents an action that has to be executed after a change in the gameloop.
 * 
 * @see GameLoop
 * 
 */
@FunctionalInterface
public interface ActionOnLoopChange {
    /**
     * The action to execute.
     */
    void executeAction();
}
