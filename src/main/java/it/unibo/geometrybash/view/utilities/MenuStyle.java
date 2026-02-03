package it.unibo.geometrybash.view.utilities;

import java.awt.Color;

/**
 * Interface representing a visual style for the main menu that has the bash style.
 */
public interface MenuStyle {

    /**
     * Retrives the color for the text.
     *
     * @return the color for the text.
     */
    Color getTextColor();

    /**
     * Retrives the color for the background.
     *
     * @return the color for the background.
     */
    Color getBackgroundColor();

    /**
     * Retrives the color for the accent elements.
     *
     * @return the color for the accent elements.
     */
    Color getAccentColor();

    /**
     * Retrives the string displayed as the terminal prompt.
     *
     * @return the string displayed as the terminal prompt.
     */
    String getPrompt();
}
