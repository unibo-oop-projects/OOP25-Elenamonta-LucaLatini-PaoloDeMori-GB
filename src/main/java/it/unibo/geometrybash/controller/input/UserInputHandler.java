package it.unibo.geometrybash.controller.input;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Interface for handling user input events (keyboard actions).
 * This handler processes player actions like jump and menu (for the moment).
 */
public interface UserInputHandler {

  /**
   * Handles a view event representing user input.
   *
   * @param event the view event
   */
  void handleUserInputEvent(ViewEvent event);

  /**
   * Sets the action to execute when player performs jump.
   *
   * @param action the action to execute
   */
  void setOnJumpAction(OnInputEventAction action);

  /**
   * Sets the action to execute when player opens menu/pause.
   *
   * @param action the action to execute
   */
  void setOnMenuAction(OnInputEventAction action);
}
