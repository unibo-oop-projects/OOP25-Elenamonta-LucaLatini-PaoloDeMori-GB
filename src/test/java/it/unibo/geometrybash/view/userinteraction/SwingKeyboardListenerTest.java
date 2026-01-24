package it.unibo.geometrybash.view.userinteraction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.awt.event.KeyEvent;
import java.awt.Component;
import javax.swing.JButton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SwingKeyboardListener}.
 */
final class SwingKeyboardListenerTest {
    private SwingKeyboardListener listener;
    private int capturedKeyCode;
    private final Component component = new JButton();

    @BeforeEach
    void setUp() {
        capturedKeyCode = -1;
        final InputListenerStrategy strategy = keyCode -> capturedKeyCode = keyCode;
        listener = new SwingKeyboardListener(strategy);
    }

    //simulate swing's keyevent and check if the listener get and pass the keyCode to the strategy
    @Test
    void testKeyPressedDelegation() {
        final int testKeyCode = KeyEvent.VK_SPACE;
        final KeyEvent event = new KeyEvent(component, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, testKeyCode, ' ');

        listener.keyPressed(event);

        assertEquals(testKeyCode, capturedKeyCode, "The strategy should receive the correct key code");
    }

    //check if it is possible change strategy after objcet's creation
    @Test
    void testSetOnClickStrategy() {
        final int[] newCaptured = {-1};
        listener.setOnClickStrategy(keyCode -> newCaptured[0] = keyCode);
        final KeyEvent event = new KeyEvent(component, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        listener.keyPressed(event);
        assertEquals(KeyEvent.VK_ENTER, newCaptured[0], "The new strategy should be used");
        assertEquals(-1, capturedKeyCode, "The old strategy should not be called");
    }

    @Test
    void testSetOnReleasedStrategyThrows() {
        assertThrows(UnsupportedOperationException.class, () -> listener.setOnReleasedStrategy(keyCode -> { }));
    }
}
