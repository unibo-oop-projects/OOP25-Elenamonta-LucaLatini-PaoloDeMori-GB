package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for {@link ViewEventType}.
 * 
 * @see ViewEvent
 */
class ViewEventTypeTest {
    private static final String RESUME_NAME = "Resume";

    /*
        Check that the name is set and get  correctly
    */
    @Test
    void testGetName() {
        assertEquals(ViewEventType.RESUME.getName(), RESUME_NAME);
    }
}
