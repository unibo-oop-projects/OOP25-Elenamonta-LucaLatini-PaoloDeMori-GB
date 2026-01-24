package it.unibo.geometrybash.controller.input;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;
import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Implementation of {@link UserInputHandler} for handling user input events.
 * Maps user actions (JUMP, MENU) to configured actions.
 */
public final class UserInputHandlerImpl implements UserInputHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInputHandlerImpl.class);

    private final Map<StandardViewEventType, OnInputEventAction> actionMap;

    /**
     * Creates a new UserInputHandlerImpl with empty action mappings.
     */
    public UserInputHandlerImpl() {
        this.actionMap = new EnumMap<>(StandardViewEventType.class);
        //used EnumMap for efficiency.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleUserInputEvent(final ViewEvent event) {
        Objects.requireNonNull(event, "ViewEvent cannot be null");

        final ViewEventType eventType = event.getType();
        if (!eventType.getStandard().isUserInput()) {
            LOGGER.warn("Received non-user-input event: {}", eventType.getStandard());
            return;
        }
        final OnInputEventAction action = actionMap.get(eventType.getStandard());
        if (action != null) {
            action.executeAction();
        } else {
            LOGGER.warn("No action registered for user input: {}", eventType.getStandard());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnJumpAction(final OnInputEventAction action) {
        Objects.requireNonNull(action, "Jump action cannot be null");
        actionMap.put(StandardViewEventType.JUMP, action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnMenuAction(final OnInputEventAction action) {
        Objects.requireNonNull(action, "Menu action cannot be null");
        actionMap.put(StandardViewEventType.MENU, action);
    }
}
