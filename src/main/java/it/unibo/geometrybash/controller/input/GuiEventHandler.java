package it.unibo.geometrybash.controller.input;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.controller.OnGenericCommandAction;
import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Interface for handling GUI/View events.
 */
public interface GuiEventHandler {

    /**
     * Handles a view event from the GUI.
     *
     * @param event the view event
     */
    void handleViewEvent(ViewEvent event);

    /**
     * Sets the action to perform on a specific standard event type.
     *
     * @param type the standard event type
     * @param action the action to execute
     */
    void setActionForEvent(StandardViewEventType type, OnInputEventAction action);

    /**
     * Sets the handler fot generic terminal commands.
     *
     * @param handler the handler that the recives the command string
     */
    void setGenericCommandHandler(OnGenericCommandAction handler);

}
