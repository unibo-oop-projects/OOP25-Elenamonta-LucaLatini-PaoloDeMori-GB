package it.unibo.geometrybash.model.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TestFpsNotCalculatedException {
    private static final String DEFAULT_MESSAGE = "Trying to get the number of FPS , but it has not been calculated once yet";

    private static final String ADDED_MESSAGE = "Added Message";

    @Test
    void testConstructorWithDefaultMessage() {
        final FpsNotCalculatedException ex = new FpsNotCalculatedException();
        assertEquals(DEFAULT_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (FpsNotCalculatedException e) {
            assertEquals(ex, e);
        }
    }

    @Test
    void testConstructorWithAddedMessage() {
        final FpsNotCalculatedException ex = new FpsNotCalculatedException(ADDED_MESSAGE);
        assertEquals(DEFAULT_MESSAGE + " " + ADDED_MESSAGE, ex.getMessage());
        try {
            throw(ex);
        } catch (FpsNotCalculatedException e) {
            assertEquals(ex, e);
        }
    }
}