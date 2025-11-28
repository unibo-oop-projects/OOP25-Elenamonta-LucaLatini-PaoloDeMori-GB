package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for ViewEvent {@link ViewEvent}.
 * 
 * @see ViewEventType
 */
class ViewEventTest {
    private static final String RESUME_NAME = "Resume";

    /**
     * Test Factories, getType and GetEvent name methods.
     */
    @Test
    void testFactories() {
        assertEquals(ViewEvent.createResumeEvent().getType(), ViewEventType.RESUME);
        assertEquals(ViewEvent.createResumeEvent().getEventName(), RESUME_NAME);

        assertNotEquals(ViewEvent.createResumeEvent(), ViewEvent.createResumeEvent());
    }
}
