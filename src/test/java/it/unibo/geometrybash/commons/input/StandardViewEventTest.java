package it.unibo.geometrybash.commons.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
/**
 * Tests for StandardViewEvent.
 */
class StandardViewEventTest {
     @Test
    void testConstructorWithValidType() {
        final StandardViewEvent event = new StandardViewEvent(StandardViewEventType.START);
        assertFalse(event.isGeneric());
        assertEquals(StandardViewEventType.START, event.getStandard());
        assertEquals("start", event.getCommand());
    }

    @Test
    void testConstructorWithNull() {
        assertThrows(InvalidViewEventCreationArgumentsException.class,
            () -> new StandardViewEvent(null));
    }
}
