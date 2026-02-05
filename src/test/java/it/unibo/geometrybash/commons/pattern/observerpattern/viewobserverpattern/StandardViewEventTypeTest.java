package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;

/**
 * Test class for {@link StandardViewEventType}.
 */
class StandardViewEventTypeTest {

    private static final String START_NAME = "start";
    private static final String RESUME_NAME = "resume";
    private static final String RESTART_NAME = "restart";
    private static final String CLOSE_NAME = "close";
    private static final String JUMP_NAME = "jump";
    private static final String MENU_NAME = "menu";
    private static final String GENERIC_NAME = "generic";
    private static final int SIZE = 7;

    @Test
    void testGetCommandName() {
        assertEquals(START_NAME, StandardViewEventType.START.getCommandName());
        assertEquals(RESUME_NAME, StandardViewEventType.RESUME.getCommandName());
        assertEquals(RESTART_NAME, StandardViewEventType.RESTART.getCommandName());
        assertEquals(CLOSE_NAME, StandardViewEventType.CLOSE.getCommandName());
        assertEquals(JUMP_NAME, StandardViewEventType.JUMP.getCommandName());
        assertEquals(MENU_NAME, StandardViewEventType.MENU.getCommandName());
        assertEquals(GENERIC_NAME, StandardViewEventType.GENERIC.getCommandName());
    }

    @Test
    void testCommandNamesAreDifferent() {
        assertNotEquals(StandardViewEventType.START.getCommandName(),
                StandardViewEventType.CLOSE.getCommandName());
        assertNotEquals(
                StandardViewEventType.RESUME.getCommandName(),
                StandardViewEventType.RESTART.getCommandName());
    }

    @Test
    void testAllValuesExist() {
        final StandardViewEventType[] values = StandardViewEventType.values();
        assertEquals(SIZE, values.length);
        for (final StandardViewEventType type : values) {
            assertNotNull(type.getCommandName());
        }
    }

    @Test
    void testIsGuiEvent() {
        assertTrue(StandardViewEventType.START.isGuiEvent());
        assertTrue(StandardViewEventType.RESUME.isGuiEvent());
        assertTrue(StandardViewEventType.RESTART.isGuiEvent());
        assertTrue(StandardViewEventType.CLOSE.isGuiEvent());
        assertFalse(StandardViewEventType.JUMP.isGuiEvent());
        assertFalse(StandardViewEventType.MENU.isGuiEvent());
        assertFalse(StandardViewEventType.GENERIC.isGuiEvent());
    }

    @Test
    void testIsUserInput() {
        assertTrue(StandardViewEventType.JUMP.isUserInput());
        assertTrue(StandardViewEventType.MENU.isUserInput());
        assertFalse(StandardViewEventType.START.isUserInput());
        assertFalse(StandardViewEventType.CLOSE.isUserInput());
        assertFalse(StandardViewEventType.GENERIC.isUserInput());
    }
}
