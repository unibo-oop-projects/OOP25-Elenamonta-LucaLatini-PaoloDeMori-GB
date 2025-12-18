package it.unibo.geometrybash.controller.input;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;
import it.unibo.geometrybash.controller.OnGenericCommandAction;
import it.unibo.geometrybash.controller.OnInputEventAction;

/**
 * Implementation of {@link GuiEventHandler} for handling GUI events.
 */
public final class GuiEventHandlerImpl implements GuiEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiEventHandlerImpl.class);

    private final Map<StandardViewEventType, OnInputEventAction> actionMap;
    private OnGenericCommandAction genericCommandHandler;

    /**
     * Creates a new GuiEventHandlerImpl with empty action mappings.
     */
    public GuiEventHandlerImpl() {
        this.actionMap = new EnumMap<>(StandardViewEventType.class);
    }

    @Override
    public void handleViewEvent(final ViewEvent event) {
        Objects.requireNonNull(event, "ViewEvent cannot be null");
        final ViewEventType eventType = event.getType();

        if (eventType.isGeneric()) {
            if (genericCommandHandler != null) {
                genericCommandHandler.executeAction(eventType.getCommand());
            } else {
                LOGGER.warn("No handler for generic command: {}", eventType.getCommand());
            }
        } else {
            final OnInputEventAction action = actionMap.get(eventType.getStandard());
            if (action != null) {
                action.executeAction();
            } else {
                LOGGER.warn("No action registered for event: {}", eventType.getStandard());
            }
        }
    }

    @Override
    public void setActionForEvent(final StandardViewEventType type, final OnInputEventAction action) {
        Objects.requireNonNull(type, "Event type cannot be null");
        Objects.requireNonNull(action, "Action cannot be null");
        if (type == StandardViewEventType.GENERIC) {
            throw new IllegalArgumentException(
                "Cannot set action for GENERIC. Use setGenericCommandHandler instead."
            );
        }
        actionMap.put(type, action);
    }

    @Override
    public void setGenericCommandHandler(final OnGenericCommandAction handler) {
        this.genericCommandHandler = Objects.requireNonNull(handler, "Handler cannot be null");
    }
}
