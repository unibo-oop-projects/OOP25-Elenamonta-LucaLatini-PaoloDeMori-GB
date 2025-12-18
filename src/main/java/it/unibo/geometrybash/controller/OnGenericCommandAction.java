package it.unibo.geometrybash.controller;

/**
 * Functional interface for handling generic terminal commands from the view.
 * 
 * <p>This is used to process custom bash-style commands entered by the user
 * that are not standard predefined events.
 * 
 * @see OnInputEventAction
 */
@FunctionalInterface
public interface OnGenericCommandAction {
    /**
     * Executes the action for the given terminal command.
     * 
     * @param command the terminal command string entered by the user
     */
    void executeAction(String command);
}
