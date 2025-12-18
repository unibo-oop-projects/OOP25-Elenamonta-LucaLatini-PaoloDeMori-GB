package it.unibo.geometrybash.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Tests for ViewEventTypeFactory.
 */
class ViewEventTypeFactoryTest {
    @Test
    void testStandardEvent() {
        final ViewEventType start = ViewEventTypeFactory.standard(StandardViewEventType.START);
        assertFalse(start.isGeneric());
        assertEquals(StandardViewEventType.START, start.getStandard());
        assertEquals("start", start.getCommand());
    }

    @Test
    void testGenericEvent() {
        final ViewEventType cmd = ViewEventTypeFactory.generic("help");
        assertTrue(cmd.isGeneric());
        assertEquals(StandardViewEventType.GENERIC, cmd.getStandard());
        assertEquals("help", cmd.getCommand());
    }

    @Test
    void testCannotCreateStandardWithGenericType() {
        assertThrows(IllegalArgumentException.class, 
            () -> ViewEventTypeFactory.standard(StandardViewEventType.GENERIC));
    }

    @Test
    void testCannotCreateGenericWithEmptyCommand() {
        assertThrows(InvalidViewEventCreationArgumentsException.class, 
            () -> ViewEventTypeFactory.generic(""));
    }

    @Test
    void testCannotCreateWithNull() {
        assertThrows(NullPointerException.class, 
            () -> ViewEventTypeFactory.standard(null));
        assertThrows(InvalidViewEventCreationArgumentsException.class, 
            () -> ViewEventTypeFactory.generic(null));
    }
}
