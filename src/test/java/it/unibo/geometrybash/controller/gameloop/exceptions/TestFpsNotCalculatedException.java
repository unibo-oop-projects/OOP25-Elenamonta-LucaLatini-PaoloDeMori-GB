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
class TestFpsNotCalculatedException {
    private static final String DEFAULT_MESSAGE = "Trying to get the number of FPS , but it has not been calculated once yet";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final FpsNotCalculatedException ex = new FpsNotCalculatedException();
        assertEquals(DEFAULT_MESSAGE, ex.getMessage());
    }

    @Test
    void testConstructorWithAddedMessage() {
        final FpsNotCalculatedException ex = new FpsNotCalculatedException(ADDED_MESSAGE);
        assertEquals(DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
    }
}
