package it.unibo.geometrybash.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestInvalidGameLoopStatusException {
    private static final String DEFAULT_MESSAGE = "Error during a method call in the gameloop";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final InvalidGameLoopStatusException ex = new InvalidGameLoopStatusException();
        assertEquals(DEFAULT_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (InvalidGameLoopStatusException e) {
            assertEquals(ex, e);
        }
    }

    @Test
    void testConstructorWithAddedMessage() {
        final InvalidGameLoopStatusException ex = new InvalidGameLoopStatusException(ADDED_MESSAGE);
        assertEquals(DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (InvalidGameLoopStatusException e) {
            assertEquals(ex, e);
        }
    }
}
