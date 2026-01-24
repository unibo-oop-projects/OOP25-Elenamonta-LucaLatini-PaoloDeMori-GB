package it.unibo.geometrybash.view.userinteraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.event.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.view.KeyProcessor;

/**
 * Test for {@link SwingStrategyWithObservable}.
 */
final class SwingStrategyWithObservableTest {
    private SwingStrategyWithObservable strategy;
    private ViewEvent capturedEvent;

    @BeforeEach
    void setUp() {
        capturedEvent = null;
        final KeyProcessor kp = event -> capturedEvent = event;
        strategy = new SwingStrategyWithObservable(kp);
    }

    @Test
    void testSpaceKeyMapsToJump() {
        strategy.handleInput(KeyEvent.VK_SPACE);
        assertNotNull(capturedEvent);
        assertEquals(StandardViewEventType.JUMP, capturedEvent.getType().getStandard());
    }

    @Test
    void testUpKeyMapsToJump() {
        strategy.handleInput(KeyEvent.VK_UP);
        assertNotNull(capturedEvent);
        assertEquals(StandardViewEventType.JUMP, capturedEvent.getType().getStandard());
    }

    @Test
    void testEscapeKeyMapsToMenu() {
        strategy.handleInput(KeyEvent.VK_ESCAPE);
        assertNotNull(capturedEvent);
        assertEquals(StandardViewEventType.MENU, capturedEvent.getType().getStandard());
    }

    @Test
    void testUnmappedKeyDoesNothing() {
        strategy.handleInput(KeyEvent.VK_A);
        assertNull(capturedEvent, "No event should be produced for unmapped keys");
    }
}
