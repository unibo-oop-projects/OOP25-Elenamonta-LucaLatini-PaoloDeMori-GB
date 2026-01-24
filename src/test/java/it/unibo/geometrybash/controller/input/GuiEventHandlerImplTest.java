package it.unibo.geometrybash.controller.input;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Tests for {@link GuiEventHandlerImpl} to improve coverage.
 */
final class GuiEventHandlerImplTest {
    private GuiEventHandlerImpl guiHandler;

    @BeforeEach
    void setUp() {
        guiHandler = new GuiEventHandlerImpl();
    }

    @Test
    void testHandleGenericEventWithoutHandler() {
        final ViewEventType eventType = ViewEventTypeFactory.generic("test-cmd");
        final ViewEvent event = ViewEvent.createEvent(eventType);
        guiHandler.handleViewEvent(event);
    }


    // test for check LOGGER.warn("No action registered for event: {}")
    @Test
    void testHandleStandardEventWithoutAction() {
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.START);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        guiHandler.handleViewEvent(event);
    }

    //test for check if it trhow correctly the new IllegalArgumentException(...)
    @Test
    void testSetActionForGenericTypeThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            guiHandler.setActionForEvent(StandardViewEventType.GENERIC, () -> {
            });
        });
    }

    @Test
    void testHandleViewEventWithNull() {
        assertThrows(NullPointerException.class, () -> guiHandler.handleViewEvent(null));
    }
}
