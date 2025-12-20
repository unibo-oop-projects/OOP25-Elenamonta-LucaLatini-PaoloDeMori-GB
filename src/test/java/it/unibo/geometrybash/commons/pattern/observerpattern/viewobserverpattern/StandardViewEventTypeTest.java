package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;

/**
 * Test class for {@link StandardViewEventType}.
 */
class StandardViewEventTypeTest {

    private static final String START_NAME = "start";
    private static final String RESUME_NAME = "resume";
    private static final String HOME_NAME = "home";
    private static final String RESTART_NAME = "restart";
    private static final String CLOSE_NAME = "close";
    private static final String INVENTORY_NAME = "inventory";
    private static final String GENERIC_NAME = "generic";
    private static final int SIZE = 7;

    @Test
    void testGetCommandName() {
        assertEquals(START_NAME, StandardViewEventType.START.getCommandName());
        assertEquals(RESUME_NAME, StandardViewEventType.RESUME.getCommandName());
        assertEquals(HOME_NAME, StandardViewEventType.HOME.getCommandName());
        assertEquals(RESTART_NAME, StandardViewEventType.RESTART.getCommandName());
        assertEquals(CLOSE_NAME, StandardViewEventType.CLOSE.getCommandName());
        assertEquals(INVENTORY_NAME, StandardViewEventType.INVENTORY.getCommandName());
        assertEquals(GENERIC_NAME, StandardViewEventType.GENERIC.getCommandName());
    }

    @Test
    void testCommandNamesAreDifferent() {
        assertNotEquals(StandardViewEventType.START.getCommandName(),
        StandardViewEventType.CLOSE.getCommandName()
    );
    assertNotEquals(
            StandardViewEventType.RESUME.getCommandName(),
            StandardViewEventType.RESTART.getCommandName()
        );
    }

    @Test
    void testAllValuesExist() {
        final StandardViewEventType[] values = StandardViewEventType.values();
        assertEquals(SIZE, values.length);
        for (final StandardViewEventType type : values) {
            assertNotNull(type.getCommandName());
        }
    }
}
