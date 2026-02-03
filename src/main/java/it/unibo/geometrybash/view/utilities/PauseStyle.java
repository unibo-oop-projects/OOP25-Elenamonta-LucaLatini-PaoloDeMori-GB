package it.unibo.geometrybash.view.utilities;

import java.awt.Color;

/**
 * Pause visual style fot the geometry bash terminal.
 */
public class PauseStyle implements MenuStyle {

    /**
     * Creates a {@link PauseStyle} for the paused game state.
     */
    public PauseStyle() {
        // Default constructor
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Color getTextColor() {
        return TerminalColor.PAUSE;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Color getBackgroundColor() {
        return TerminalColor.BACKGROUND;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Color getAccentColor() {
        return TerminalColor.PAUSE;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getPrompt() {
        return "geometrybash@oop24:~# ";
    }
}
