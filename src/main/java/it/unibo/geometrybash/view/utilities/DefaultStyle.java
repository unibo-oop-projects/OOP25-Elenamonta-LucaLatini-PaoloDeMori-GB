
package it.unibo.geometrybash.view.utilities;

import java.awt.Color;

/**
 * Standard visual style fot the geometry bash terminal.
 */
public class DefaultStyle implements MenuStyle {

    /**
     * Creates a {@code DefaultStyle} with the standard terminal colors and prompt.
     */
    public DefaultStyle() {
        // Default constructor
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Color getTextColor() {
        return TerminalColor.FOREGROUND;
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
        return TerminalColor.CARET;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getPrompt() {
        return "geometrybash@oop24:~# ";
    }

}
