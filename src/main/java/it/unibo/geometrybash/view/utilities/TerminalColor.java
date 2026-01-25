package it.unibo.geometrybash.view.utilities;

import java.awt.Color;
import java.awt.Font;

/**
 * Centralized color and font scheme for the Main menu.
 */
public final class TerminalColor {

    /**
     * Set the black color for the background.
     */
    public static final Color BACKGROUND = Color.BLACK;

    /**
     * Set the green color for the foreground.
     */
    public static final Color FOREGROUND = new Color(51, 255, 51);

    /**
     * Set the yellow color for the pause.
     */
    public static final Color PAUSE = new Color(255, 255, 102);

    /**
     * Set the green color for the prompt.
     */
    public static final Color PROMPT = new Color(51, 255, 51);

    /**
     * Set the green color for the caret.
     */
    public static final Color CARET = new Color(51, 255, 51);

    /**
     * Set the consolas font fot the text in MainMenu.
     */
    public static final String FONT_FAMILY = "Consolas";

    /** Main application font used for standard text. */
    public static final Font MAIN_FONT = new Font(FONT_FAMILY, Font.PLAIN, 18);

    /** Bold font used for emphasized text and headings. */
    public static final Font BOLD_FONT = new Font(FONT_FAMILY, Font.BOLD, 18);

    /** Monospaced font used for ASCII or fixed-width text. */
    public static final Font ASCII_FONT = new Font("Monospaced", Font.BOLD, 12);

    private TerminalColor() { }

}
