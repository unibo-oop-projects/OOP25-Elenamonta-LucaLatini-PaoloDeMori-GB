package it.unibo.geometrybash.controller.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.geometrybash.commons.input.InvalidViewEventCreationArgumentsException;


/*SpotBugs signals that this class has "exception" in its name even without being an exception
 but it's clear that this is the test for the exception class*/
@SuppressFBWarnings(
    value = "NM",
    justification = "SpotBugs signals that this class is named like an exception even if it doesn't extends Exception class"
        + ", but it's clear that this class is not an exception but it's a Test"
)
class TestInvalidViewEventCreationArgumentsException {
    private static final String DEFAULT_MESSAGE = "Trying to create an view event with invalid arguments";
    private static final String ADDED_MESSAGE = "Command cannot be blank or null";

    @Test
    void testDefaultConstructor() {
        final InvalidViewEventCreationArgumentsException ex =
            new InvalidViewEventCreationArgumentsException();
        assertEquals(DEFAULT_MESSAGE, ex.getMessage());
    }

    @Test
    void testConstructorWithMessage() {
        final InvalidViewEventCreationArgumentsException ex =
            new InvalidViewEventCreationArgumentsException(ADDED_MESSAGE);
        assertEquals(DEFAULT_MESSAGE + ADDED_MESSAGE, ex.getMessage());
    }
}
