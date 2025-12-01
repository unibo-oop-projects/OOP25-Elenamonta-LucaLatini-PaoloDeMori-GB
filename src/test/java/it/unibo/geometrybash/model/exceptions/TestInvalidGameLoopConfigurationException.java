package it.unibo.geometrybash.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestInvalidGameLoopConfigurationException {
    private static final String DEFAULT_MESSAGE = "Trying to execute the gameloop without a correct initialization";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final InvalidGameLoopConfigurationException ex = new InvalidGameLoopConfigurationException();
        assertEquals(DEFAULT_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (InvalidGameLoopConfigurationException e) {
            assertEquals(ex, e);
        }
    }

    @Test
    void testConstructorWithAddedMessage() {
        final InvalidGameLoopConfigurationException ex = new InvalidGameLoopConfigurationException(ADDED_MESSAGE);
        assertEquals(DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (InvalidGameLoopConfigurationException e) {
            assertEquals(ex, e);
        }
    }
}
