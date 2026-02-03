package it.unibo.geometrybash.view.menus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.input.ViewEventTypeFactory;
import it.unibo.geometrybash.commons.pattern.observerpattern.AbstractObservableWithSet;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewObservable;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.TextAssetReader;
import it.unibo.geometrybash.view.utilities.DefaultStyle;
import it.unibo.geometrybash.view.utilities.PauseStyle;
import it.unibo.geometrybash.view.utilities.MenuStyle;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * Creates and manages the main menu graphical interface.
 * Handles user input and notifies observers with view events.
 * extends the {@link AbstractObservableWithSet} and implements
 * {@link ViewObservable}
 */
public final class MainMenuView extends AbstractObservableWithSet<ViewEvent> implements ViewObservable {
    /** Prompt displayed before the user input. */
    public static final String PROMPT = "geometrybash@oop24:~# ";
    /** Command to start the game session. */
    public static final String CMD_START = "start";
    /** Command to close the application. */
    public static final String CMD_CLOSE = "close";
    /** Command to restar the level. */
    public static final String CMD_RESTART = "restart";
    /** Command to close the application. */
    public static final String CMD_EXIT = "exit";
    /** Command to resume the game from pause state. */
    public static final String CMD_RESUME = "resume";
    /** Command to display commands help. */
    public static final String CMD_HELP = "help";
    /** Command to display commands help. */
    public static final String CMD_COMMANDS = "commands";
    /** Argument for the man command specifying the resolution manual. */
    public static final String ARG_RESOLUTION = "resolution";
    /** Full command to display the manual for screen resolutions. */
    public static final String CMD_MAN_RESOLUTION = "man " + ARG_RESOLUTION;
    /** Command for set the 1920x1080 resolution. */
    public static final String BIG = "big";
    /** Command for set the 1600x9000 resolution. */
    public static final String MEDIUM = "medium";
    /** Command for set the 1024x768 resolution. */
    public static final String SMALL = "small";
    /** Command to display avaible colors. */
    public static final String CMD_COLORS = "colors";
    /** Command to set the player's color. */
    public static final String CMD_SET_COLOR = "setcolor";
    /** characters for parameters representation. */
    public static final String STANDARD_SEPARATOR = "-";
    /** Flag for set the player's inner color. */
    public static final String FLAG_INNER = STANDARD_SEPARATOR + "inner";
    /** Flag for set the player's outer color. */
    public static final String FLAG_OUTER = STANDARD_SEPARATOR + "outer";
    /** List of avaible colors for the terminal display. */
    public static final Map<String, Integer> AVAILABLE_COLORS = Map.of(
            "red", 0xFFFF0000,
            "blue", 0xFF0000FF,
            "green", 0xFF00FF00,
            "yellow", 0xFFFFD700,
            "white", 0xFFFFFFFF);
    /** Prefix for command list items. */
    private static final String CMD_PREFIX = " > ";
    /** Command for create new line. */
    private static final String NEW_LINE = "\n";

    private final JFrame frame;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private JTextArea logo;
    private JLabel insertLabel;
    private JLabel promptLabel;
    private final TextAssetReader textReader;
    private final ResourceLoader resourceLoader;

    private final MenuStyle defaultStyle = new DefaultStyle();
    private final MenuStyle pauseStyle = new PauseStyle();

    /**
     * Initializes the main menu view and its graphical components.
     *
     * @param resourceLoader the object used to retrieve resources
     */
    public MainMenuView(final ResourceLoader resourceLoader) {
        this.frame = new JFrame();
        this.frame.setUndecorated(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setBackground(TerminalColor.BACKGROUND);
        this.frame.setLayout(new BorderLayout());
        this.resourceLoader = resourceLoader;
        this.textReader = new TextAssetReader(this.resourceLoader);
        this.outputArea = createOutputArea();
        this.inputField = createInputField();

        this.frame.add(createHeaderPanel(), BorderLayout.NORTH);
        this.frame.add(createScrollableOutputPanel(), BorderLayout.CENTER);
        this.frame.add(createFooterInputPanel(), BorderLayout.SOUTH);

        setupInputListener();
        this.applyStyle(this.defaultStyle);
    }

    /**
     * Applies the styling to all graphical component.
     *
     * @param style the styling strategy to apply.
     */
    private void applyStyle(final MenuStyle style) {
        this.outputArea.setForeground(style.getTextColor());
        this.outputArea.setBackground(style.getBackgroundColor());
        this.inputField.setForeground(style.getTextColor());
        this.inputField.setBackground(style.getBackgroundColor());
        this.inputField.setCaretColor(style.getAccentColor());
        this.logo.setForeground(style.getTextColor());
        this.logo.setBackground(style.getBackgroundColor());
        this.insertLabel.setForeground(style.getTextColor());
        this.promptLabel.setForeground(style.getTextColor());
        this.promptLabel.setText(style.getPrompt());
        this.promptLabel.getParent().setBackground(style.getBackgroundColor());
        this.frame.getContentPane().setBackground(style.getBackgroundColor());
    }

    /**
     * Creates the header panel containing the ASCII logo.
     *
     * @return the header panel
     */
    private JPanel createHeaderPanel() {
        final JPanel header = new JPanel(new BorderLayout());
        header.setBackground(TerminalColor.BACKGROUND);

        final JPanel margin = new JPanel();
        final int height = 15;
        margin.setPreferredSize(new Dimension(0, height));
        margin.setBackground(TerminalColor.BACKGROUND);
        header.add(margin, BorderLayout.NORTH);

        final String title = textReader.readAll("it/unibo/geometrybash/startMenu/logo.txt");
        this.logo = new JTextArea(title);
        this.logo.setFont(TerminalColor.ASCII_FONT);
        this.logo.setEditable(false);
        this.logo.setFocusable(false);
        header.add(logo, BorderLayout.CENTER);

        this.insertLabel = new JLabel(
                "Insert " + CMD_COMMANDS + " or " + CMD_HELP + " to show the list of available actions");
        this.insertLabel.setForeground(TerminalColor.FOREGROUND);
        this.insertLabel.setFont(TerminalColor.MAIN_FONT);
        this.insertLabel.setHorizontalAlignment(JLabel.CENTER);
        header.add(insertLabel, BorderLayout.SOUTH);
        return header;
    }

    /**
     * Displays the list of available commands to the user.
     */
    public void showCommands() {
        this.appendText(NEW_LINE + " --- AVAILABLE COMMANDS ---");
        this.appendText(CMD_PREFIX + CMD_START + "      : begin your geometry bash adventure");
        this.appendText(CMD_PREFIX + CMD_HELP + "  : show this list");
        this.appendText(CMD_PREFIX + CMD_MAN_RESOLUTION + "  : display available game resolutions");
        this.appendText(CMD_PREFIX + CMD_COLORS + "  : show customization colors");
        this.appendText("---------------------------");
    }

    /**
     * Displays pause message in the termina and sets the text color.
     * this method is called when the player enter in pause state.
     */
    public void showPauseMessage() {
        this.applyStyle(this.pauseStyle);
        this.appendText("\n GAME PAUSED");
        this.appendText(" Type " + CMD_RESUME + " to continue your run in Geometry Bash");
        this.appendText(" ----------------------------------------------------");

    }

    /**
     * Show the victory message with the total number of coins collected.
     *
     * @param playerCoins the number of coins collected during the level
     * @param totalCoins  the total number of coins in the level
     */
    public void showVictoryMessage(final int playerCoins, final int totalCoins) {
        this.applyStyle(defaultStyle);
        this.appendText(NEW_LINE + " LEVEL COMPLETED! YOU WON!");
        this.appendText(" -----------------------------");
        this.appendText(" you have collected " + playerCoins + " out of " + totalCoins + " avaible coins!");
        this.appendText(" -----------------------------");
        this.appendText(" type " + CMD_START + " for start new challenge");
    }

    /**
     * Shows all the colors available that can be used to customize the player.
     */
    public void showAvailableColors() {
        this.appendText(NEW_LINE + " AVAIBLE CUSTOMIZAION COLOR");
        for (final String color : AVAILABLE_COLORS.keySet()) {
            this.appendText(CMD_PREFIX + color.toUpperCase(Locale.ROOT));
        }
        this.appendText(" -----------------------------");
        this.appendText(" Usage: " + CMD_SET_COLOR + " [" + FLAG_INNER + "|" + FLAG_OUTER + "] -<color>");
        this.appendText(" Example: " + CMD_SET_COLOR + " " + FLAG_INNER + " -red");
    }

    /**
     * Displays an error message when a command is not found.
     * This method is intended to be called by the controller.
     *
     * @param command the invalid command entered by the user
     */
    public void showUnknownCommandError(final String command) {
        this.appendText(NEW_LINE + " ERROR: '" + command + "' is not recognized as a command.");
        this.appendText("     Type" + CMD_HELP + "or" + CMD_COMMANDS + "to see the list of available actions.");
    }

    /**
     * Prints on the terminal a manual-style description of all supported screen
     * resolutions, similar to the Unix {@code man} command.
     */
    public void showManResolution() {
        appendText(
                NEW_LINE + " " + ARG_RESOLUTION.toUpperCase(Locale.ROOT) + "(1)               Geometry Bash Manual"
                        + NEW_LINE);
        appendText(" NAME");
        appendText(" " + ARG_RESOLUTION + " - show supported screen resolutions" + NEW_LINE);

        appendText(" SYNOPSIS");
        appendText(" " + CMD_MAN_RESOLUTION + NEW_LINE);

        appendText(" DESCRIPTION");
        appendText(" Displays all screen resolutions supported by Geometry Bash." + NEW_LINE);

        appendText(" SUPPORTED RESOLUTIONS");
        appendText(" " + BIG + "     - 1920 x 1080");
        appendText(" " + MEDIUM + "  - 1600 x 900");
        appendText(" " + SMALL + "   - 1024 x 768" + NEW_LINE);

        appendText("-----------------------------------------------");
    }

    /**
     * Displays a message when an error occurs during game execution.
     *
     * @param executionError the description of the error occurred
     */
    public void showGameExecutionError(final String executionError) {
        this.appendText(NEW_LINE + "  CRITICAL ERROR !");
        this.appendText("An unexpected issue occurred during the game session:");
        this.appendText(CMD_PREFIX + executionError);
        this.appendText(" ----------------------------------------------------");
        this.appendText("Please try to restart the game or contact support.");
    }

    /**
     * Creates a scrollable panel for the output area.
     *
     * @return the scroll pane containing the output area
     */
    private JScrollPane createScrollableOutputPanel() {
        final JScrollPane scroll = new JScrollPane(this.outputArea);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setBackground(TerminalColor.BACKGROUND);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    /**
     * Creates the text area used to display output text.
     *
     * @return the output text area
     */
    private JTextArea createOutputArea() {
        final JTextArea area = new JTextArea();
        area.setFont(TerminalColor.MAIN_FONT);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    /**
     * Creates the footer panel containing the input field and prompt label.
     *
     * @return the footer panel
     */
    private JPanel createFooterInputPanel() {
        final JPanel footer = new JPanel(new BorderLayout());

        this.promptLabel = new JLabel(PROMPT);
        this.promptLabel.setFont(TerminalColor.MAIN_FONT);

        footer.add(promptLabel, BorderLayout.WEST);
        footer.add(this.inputField, BorderLayout.CENTER);

        return footer;
    }

    /**
     * Creates the text field used to receive user input.
     *
     * @return the input text field
     */
    private JTextField createInputField() {
        final JTextField field = new JTextField();
        field.setFont(TerminalColor.MAIN_FONT);
        field.setBorder(null);
        return field;
    }

    /**
     * Registers the keyboard listener for handling user input.
     */
    private void setupInputListener() {
        this.inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processCommand();
                }
            }
        });
    }

    /**
     * Processes the command entered by the user and notifies observers.
     */
    private void processCommand() {
        final String rawCmd = inputField.getText().trim();
        if (!rawCmd.isEmpty()) {
            final String cmd = rawCmd.toLowerCase(Locale.ROOT);
            final String firstCommand = cmd.split(" ")[0];
            switch (firstCommand) {
                case CMD_START:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.START)));
                    break;
                case CMD_RESTART:
                    notifyObservers(
                            ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.RESTART)));
                    break;
                case CMD_HELP:
                case CMD_COMMANDS:
                    this.showCommands();
                    break;
                case CMD_CLOSE:
                case CMD_EXIT:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.CLOSE)));
                    break;
                case CMD_RESUME:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.RESUME)));
                    break;
                case CMD_COLORS:
                    this.showAvailableColors();
                    break;
                case "man":
                    if (CMD_MAN_RESOLUTION.equals(cmd)) {
                        this.showManResolution();
                    } else {
                        this.showUnknownCommandError(rawCmd);
                    }
                    break;
                default:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.generic(rawCmd)));
            }
            inputField.setText("");
        }
    }

    /**
     * Displays the main menu in full screen mode.
     */
    public void display() {
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().setFullScreenWindow(frame);
        this.frame.setVisible(true);
        this.inputField.requestFocusInWindow();
    }

    /**
     * Hides the main menu window.
     */
    public void hide() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }

    /**
     * Release all the screen resources used by main menu.
     */
    public void dispose() {
        this.frame.dispose();
    }

    /**
     * Appends a line of text to the output area.
     *
     * @param text the text to append
     */
    public void appendText(final String text) {
        this.outputArea.append(text + "\n");
        this.outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }

    /**
     * Restores the normal terminal style.
     */
    public void resumeFromPause() {
        this.applyStyle(this.defaultStyle);
    }

}
