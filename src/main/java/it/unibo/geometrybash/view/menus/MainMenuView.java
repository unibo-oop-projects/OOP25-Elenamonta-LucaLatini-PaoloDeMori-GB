package it.unibo.geometrybash.view.menus;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Locale;

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
import it.unibo.geometrybash.commons.assets.AudioManager;
import it.unibo.geometrybash.commons.assets.AudioStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.TextAssetReader;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * Creates and manages the main menu graphical interface.
 * Handles user input and notifies observers with view events.
 * extends the {@link AbstractObservableWithSet} and implements
 * {@link ViewObservable}
 */
public final class MainMenuView extends AbstractObservableWithSet<ViewEvent> implements ViewObservable {
    /** Prompt displayed before the user input. */
    private static final String PROMPT = "geometrybash@oop24:~# ";
    /** Command to start the game session. */
    private static final String CMD_START = "start";
    /** Command to open user's inventory. */
    private static final String CMD_INVENTORY = "inventory";
    /** Command to close the application. */
    private static final String CMD_CLOSE = "close";
    /** Command to close the application. */
    private static final String CMD_EXIT = "exit";
    /** Command to resume the game from pause state. */
    private static final String CMD_RESUME = "resume";
    /** Command to display commands help. */
    private static final String CMD_HELP = "help";
    /** Command to display commands help. */
    private static final String CMD_COMMANDS = "commands";
    /** Argument for the man command specifying the resolution manual. */
    private static final String ARG_RESOLUTION = "resolution";
    /** Full command to display the manual for screen resolutions. */
    private static final String CMD_MAN_RESOLUTION = "man " + ARG_RESOLUTION;
    /** Command for set the 1920x1080 resolution. */
    private static final String BIG = "big";
    /** Command for set the 1600x9000 resolution. */
    private static final String MEDIUM = "medium";
    /** Command for set the 1024x768 resolution. */
    private static final String SMALL = "small";
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

    private final AudioStore audioStore;
    private final AudioManager manage;

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
        this.audioStore = new AudioStore(resourceLoader);
        this.manage = new AudioManager(audioStore);

        this.outputArea = createOutputArea();
        this.inputField = createInputField();

        this.frame.add(createHeaderPanel(), BorderLayout.NORTH);
        this.frame.add(createScrollableOutputPanel(), BorderLayout.CENTER);
        this.frame.add(createFooterInputPanel(), BorderLayout.SOUTH);

        setupInputListener();
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
        this.logo.setBackground(TerminalColor.BACKGROUND);
        this.logo.setForeground(TerminalColor.FOREGROUND);
        this.logo.setFont(TerminalColor.ASCII_FONT);
        this.logo.setEditable(false);
        this.logo.setFocusable(false);
        header.add(logo, BorderLayout.CENTER);

        this.insertLabel = new JLabel(
                "Insert" + CMD_COMMANDS + "or" + CMD_HELP + "to show the list of available actions");
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
        this.appendText(CMD_PREFIX + CMD_INVENTORY + "  : check your equipment");
        this.appendText(CMD_PREFIX + CMD_HELP + "  : show this list");
        this.appendText(CMD_PREFIX + CMD_MAN_RESOLUTION + "  : display available game resolutions");
        this.appendText("---------------------------");
    }

    /**
     * Displays pause message in the termina and sets the text color.
     * this method is called when the player enter in pause state.
     */
    public void showPauseMessage() {
        this.outputArea.setForeground(TerminalColor.PAUSE);
        this.inputField.setForeground(TerminalColor.PAUSE);
        this.insertLabel.setForeground(TerminalColor.PAUSE);
        this.logo.setForeground(TerminalColor.PAUSE);
        this.promptLabel.setForeground(TerminalColor.PAUSE);

        this.appendText("\n GAME PAUSED");
        this.appendText(" Type" + CMD_RESUME + "to continue your run in Geometry Bash");
        this.appendText(" ----------------------------------------------------");

    }

    /**
     * Show the victory message with the total number of coins collected.
     *
     * @param coins the number of coins collected during the level
     */
    public void showVictoryMessage(final int coins) {
        this.appendText(NEW_LINE + " LEVEL COMPLETED! YOU WON!");
        this.appendText(" -----------------------------");
        this.appendText(" total coins collected: " + coins);
        this.appendText(" -----------------------------");
        this.appendText(" type" + CMD_START + "for start new challenge");
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
        area.setBackground(TerminalColor.BACKGROUND);
        area.setForeground(TerminalColor.FOREGROUND);
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
        footer.setBackground(TerminalColor.BACKGROUND);

        this.promptLabel = new JLabel(PROMPT);
        this.promptLabel.setForeground(TerminalColor.FOREGROUND);
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
        field.setBackground(TerminalColor.BACKGROUND);
        field.setForeground(TerminalColor.FOREGROUND);
        field.setFont(TerminalColor.MAIN_FONT);
        field.setCaretColor(TerminalColor.CARET);
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
            switch (cmd) {
                case CMD_START:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.START)));
                    break;
                case CMD_INVENTORY:
                    notifyObservers(
                            ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.INVENTORY)));
                    break;
                case CMD_CLOSE:
                case CMD_EXIT:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.CLOSE)));
                    break;
                case CMD_RESUME:
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.RESUME)));
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
        this.manage.loop("it/unibo/geometrybash/audio/menu.wav");
        GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice().setFullScreenWindow(frame);
        this.frame.setVisible(true);
        this.inputField.requestFocusInWindow();
    }

    /**
     * Hides the main menu window.
     */
    public void hide() {
        this.manage.stop("it/unibo/geometrybash/audio/menu.wav");
        this.frame.setVisible(false);
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

}

