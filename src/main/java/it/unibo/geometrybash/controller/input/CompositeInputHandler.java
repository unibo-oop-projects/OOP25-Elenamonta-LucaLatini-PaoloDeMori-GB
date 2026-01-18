package it.unibo.geometrybash.controller.input;

import java.util.Objects;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;
import it.unibo.geometrybash.controller.InputHandler;
import it.unibo.geometrybash.controller.OnGenericCommandAction;
import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Composite implementation of {@link InputHandler} that delegates to
 * specialized handlers for keyboard and GUI events.
 */
public final class CompositeInputHandler implements InputHandler {

     private final UserInputHandler userInputHandler;
    private final GuiEventHandler guiEventHandler;

    /**
     * Creates a new CompositeInputHandler with default handlers.
     */
    public CompositeInputHandler() {
        this.userInputHandler = new UserInputHandlerImpl();
        this.guiEventHandler = new GuiEventHandlerImpl();
    }

    @Override
    public void update(final ViewEvent event) {
        Objects.requireNonNull(event, "ViewEvent cannot be null");
        final ViewEventType eventType = event.getType();
        if (eventType.isFromUserInput()) {
            userInputHandler.handleUserInputEvent(event);
        } else {
            guiEventHandler.handleViewEvent(event);
        }
    }

    @Override
    public void setOnMainKeyPressed(final OnInputEventAction action) {
        userInputHandler.setOnJumpAction(action);
    }

    @Override
    public void setOnMenuKeyPressed(final OnInputEventAction action) {
        userInputHandler.setOnMenuAction(action);
    }

    @Override
    public void setOnResumeKeyPressed(final OnInputEventAction action) {
        guiEventHandler.setActionForEvent(StandardViewEventType.RESUME, action);
    }

    @Override
    public void setActionForEvent(final StandardViewEventType type, final OnInputEventAction action) {
        guiEventHandler.setActionForEvent(type, action);
    }

    @Override
    public void setGenericCommandHandler(final OnGenericCommandAction handler) {
        guiEventHandler.setGenericCommandHandler(handler);
    }
}
