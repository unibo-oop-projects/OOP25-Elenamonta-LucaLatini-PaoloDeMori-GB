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

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnMainKeyPressed(final OnInputEventAction action) {
        userInputHandler.setOnJumpAction(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnMenuKeyPressed(final OnInputEventAction action) {
        userInputHandler.setOnMenuAction(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnResumeKeyPressed(final OnInputEventAction action) {
        guiEventHandler.setActionForEvent(StandardViewEventType.RESUME, action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActionForEvent(final StandardViewEventType type, final OnInputEventAction action) {
        guiEventHandler.setActionForEvent(type, action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGenericCommandHandler(final OnGenericCommandAction handler) {
        guiEventHandler.setGenericCommandHandler(handler);
    }
}
