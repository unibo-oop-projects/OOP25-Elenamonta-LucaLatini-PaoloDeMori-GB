package it.unibo.geometrybash.controller.input;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Tests for {@link UserInputHandlerImpl} to improve coverage.
 */
final class UserInputHandlerImplTest {

    private UserInputHandlerImpl handler;

    @BeforeEach
    void setUp() {
        handler = new UserInputHandlerImpl();
    }

    @Test
    void testHandleNonUserInputEvent() {
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.START);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.handleUserInputEvent(event);
    }

    @Test
    void testHandleUserInputWithoutRegisteredAction() {
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.JUMP);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.handleUserInputEvent(event);
    }

}
