package it.unibo.geometrybash.controller.gameloop.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/*SpotBugs signals that this class has "exception" in its name even without being an exception
 but it's clear that this is the test for the exception class*/
@SuppressFBWarnings(
    value = "NM",
    justification = "SpotBugs signals that this class is named like an exception even if it doesn't extends Exception class"
        + ", but it's clear that this class is not an exception but it's a Test"
)
class TestNotStartedException {
    private static final String DEFAULT_MESSAGE = "Trying to call a method on the gameloop, while it is not being started";
    private static final String SUPER_CLASS_DEFAULT_MESSAGE = "Error during a method call in the gameloop";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final NotStartedException ex = new NotStartedException();
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " + DEFAULT_MESSAGE, ex.getMessage());
    }

    @Test
    void testConstructorWithAddedMessage() {
        final NotStartedException ex = new NotStartedException(ADDED_MESSAGE);
        assertEquals(SUPER_CLASS_DEFAULT_MESSAGE + " " + DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
    }
}
