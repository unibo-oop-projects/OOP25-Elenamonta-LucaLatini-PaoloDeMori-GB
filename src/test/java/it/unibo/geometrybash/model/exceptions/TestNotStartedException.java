package it.unibo.geometrybash.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestNotStartedException {
    private static final String DEFAULT_MESSAGE = "Trying to call a method on the gameloop, while it is not being started";
    private final static String SUPER_CLASS_DEFAULT_MESSAGE ="Error during a method call in the gameloop";


    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final NotStartedException ex = new NotStartedException();
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " + DEFAULT_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (NotStartedException e) {
            assertEquals(ex, e);
        }
    }

    @Test
    void testConstructorWithAddedMessage() {
        final NotStartedException ex = new NotStartedException(ADDED_MESSAGE);
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " + DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (NotStartedException e) {
            assertEquals(ex, e);
        }
    }
}
