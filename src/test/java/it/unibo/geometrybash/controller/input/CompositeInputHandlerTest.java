package it.unibo.geometrybash.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Tests for {@link CompositeInputHandler}.
 */
final class CompositeInputHandlerTest {

    private CompositeInputHandler handler;
    private boolean actionExecuted;

    @BeforeEach
    void setUp() {
        handler = new CompositeInputHandler();
        actionExecuted = false;
    }

    @Test
    void testUpdateWithNullEvent() {
        assertThrows(NullPointerException.class, () -> handler.update(null));
    }

    @Test
    void testUserInputDelegation() {
        handler.setOnMainKeyPressed(() -> actionExecuted = true);
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.JUMP);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.update(event);
        assertTrue(actionExecuted, "Action should be executed when JUMP event is updated");
    }

    @Test
    void testGuiEventDelegation() {
        handler.setActionForEvent(StandardViewEventType.START, () -> actionExecuted = true);
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.START);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.update(event);
        assertTrue(actionExecuted, "Action should be executed when START event is updated");
    }

    @Test
    void testMenuKeyDelegation() {
        handler.setOnMenuKeyPressed(() -> actionExecuted = true);
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.MENU);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.update(event);
        assertTrue(actionExecuted, "Action should be executed when MENU event is updated");
    }

    @Test
    void testResumeKeyDelegation() {
        handler.setOnResumeKeyPressed(() -> actionExecuted = true);
        final ViewEventType eventType = ViewEventTypeFactory.standard(StandardViewEventType.RESUME);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.update(event);
        assertTrue(actionExecuted, "Action should be executed when RESUME event is updated");
    }

    @Test
    void testGenericCommandDelegation() {
        final String testCommand = "test-cmd";
        final StringBuilder capturedCommand = new StringBuilder();
        handler.setGenericCommandHandler(cmd -> {
            actionExecuted = true;
            capturedCommand.append(cmd);
        });
        final ViewEventType eventType = ViewEventTypeFactory.generic(testCommand);
        final ViewEvent event = ViewEvent.createEvent(eventType);
        handler.update(event);
        assertTrue(actionExecuted);
        assertEquals(testCommand, capturedCommand.toString());
    }
}
