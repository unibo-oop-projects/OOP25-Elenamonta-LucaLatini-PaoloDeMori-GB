package it.unibo.geometrybash.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestNotOnPauseException {
    private static final String DEFAULT_MESSAGE = "Trying to call the resume method on the gameloop, while it is not paused";
    private final static String SUPER_CLASS_DEFAULT_MESSAGE ="Error during a method call in the gameloop";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final NotOnPauseException ex = new NotOnPauseException();
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " +DEFAULT_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (NotOnPauseException e) {
            assertEquals(ex, e);
        }
    }

    @Test
    void testConstructorWithAddedMessage() {
        final NotOnPauseException ex = new NotOnPauseException(ADDED_MESSAGE);
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " + DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (NotOnPauseException e) {
            assertEquals(ex, e);
        }
    }
}
