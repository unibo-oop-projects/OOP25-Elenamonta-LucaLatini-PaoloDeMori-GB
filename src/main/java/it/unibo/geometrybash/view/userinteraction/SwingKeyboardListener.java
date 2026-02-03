package it.unibo.geometrybash.view.userinteraction;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Swing key listener that forwards key presses to a provided strategy.
 */
public final class SwingKeyboardListener extends KeyAdapter implements InputListener {

    private InputListenerStrategy onClickStrategy;

    /**
     * Creates new {@code SwingKeyboardListener} with the given input strategy.
     *
     * @param strategy the strategy to execute when a key is pressed
     */
    public SwingKeyboardListener(final InputListenerStrategy strategy) {
        this.onClickStrategy = strategy;
    }

    /**
     * Set the strategy to run when a key press is detected.
     *
     * @param strategy non-null strategy
     */
    @Override
    public void setOnClickStrategy(final InputListenerStrategy strategy) {
        this.onClickStrategy = strategy;
    }

    /**
     * Not supported in this implementation.
     *
     * @param strategy ignored
     * @throws UnsupportedOperationException always
     */
    @Override
    public void setOnReleasedStrategy(final InputListenerStrategy strategy) {
        throw new UnsupportedOperationException("In our implementation released keys aren't evaluated");
    }

    /**
     * Forward the pressed key code to the configured strategy.
     *
     * @param e the KeyEvent (non-null)
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        onClickStrategy.handleInput(e.getKeyCode());
    }
}
