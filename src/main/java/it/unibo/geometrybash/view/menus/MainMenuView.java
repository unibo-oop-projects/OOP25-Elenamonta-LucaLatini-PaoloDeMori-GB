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
import it.unibo.geometrybash.controller.input.CompositeInputHandler;
import it.unibo.geometrybash.model.MenuModel;
import it.unibo.geometrybash.commons.assets.AudioManager;
import it.unibo.geometrybash.commons.assets.AudioStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.commons.assets.TextAssetReader;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * Creates and manages the main menu graphical interface.
 * Handles user input and notifies observers with view events.
 * extends the {@link AbstractObservableWithSet} and implements
 * {@link ViewObservable}
 */
@SuppressWarnings("checkstyle:LineLength")
public final class MainMenuView extends AbstractObservableWithSet<ViewEvent> implements ViewObservable {
    /** Prompt displayed before the user input. */
    private static final String PROMPT = "geometrybash@oop24:~# ";
    private final JFrame frame;
    private final JTextArea outputArea;
    private final JTextField inputField;
    private final TextAssetReader textReader;
    private final ResourceLoader resourceLoader;

    private final AudioStore audioStore;
    private final AudioManager manage;

    /**
     * Initializes the main menu view and its graphical components.
     */
    public MainMenuView() {
        this.frame = new JFrame();
        this.frame.setUndecorated(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setBackground(TerminalColor.BACKGROUND);
        this.frame.setLayout(new BorderLayout());

        this.resourceLoader = new ResourceLoaderImpl();
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
        final JTextArea logo = new JTextArea(title);
        logo.setBackground(TerminalColor.BACKGROUND);
        logo.setForeground(TerminalColor.FOREGROUND);
        logo.setFont(TerminalColor.ASCII_FONT);
        logo.setEditable(false);
        logo.setFocusable(false);
        header.add(logo, BorderLayout.CENTER);

        final JLabel instrLabel = new JLabel("Insert 'commands' or 'help' to show the list of available actions");
        instrLabel.setForeground(TerminalColor.FOREGROUND);
        instrLabel.setFont(TerminalColor.MAIN_FONT);
        instrLabel.setHorizontalAlignment(JLabel.CENTER);
        header.add(instrLabel, BorderLayout.SOUTH);
        return header;
    }

    /**
     * Displays the list of available commands to the user.
     */
    public void showCommands() {
        this.appendText("--- AVAILABLE COMMANDS ---");
        this.appendText(" > start      : begin your geometry bash adventure");
        this.appendText(" > inventory  : check your equipment");
        this.appendText(" > close/exit : quit the terminal");
        this.appendText(" > help/cmds  : show this list");
        this.appendText("---------------------------");
    }

    /**
     * Displays an error message when a command is not found.
     * This method is intended to be called by the controller.
     *
     * @param command the invalid command entered by the user
     */
    public void showUnknownCommandError(final String command) {
        this.appendText(" ERROR: '" + command + "' is not recognized as a command.");
        this.appendText("     Type 'help' or 'commands' to see the list of available actions.");
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

        final JLabel promptLabel = new JLabel(PROMPT);
        promptLabel.setForeground(TerminalColor.FOREGROUND);
        promptLabel.setFont(TerminalColor.MAIN_FONT);

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
                case "start":
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.START)));
                    break;
                case "inventory":
                    notifyObservers(
                            ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.INVENTORY)));
                    break;
                case "close":
                case "exit":
                    notifyObservers(ViewEvent.createEvent(ViewEventTypeFactory.standard(StandardViewEventType.CLOSE)));
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

    /**
     * Launches the main menu view for testing purposes.
     *
     * @param args program arguments
     */
    public static void main(final String[] args) {
        final MainMenuView menu = new MainMenuView();
        final CompositeInputHandler cH = new CompositeInputHandler();
        final MenuModel mm = new MenuModel();
        cH.setGenericCommandHandler(command -> {
            mm.addCommand(command);
            if ("history".equals(command)) {
                for (final String string : mm.getHistory()) {
                    menu.appendText(string);
                }
            } else if ("help".equals(command) || "commands".equals(command) || "cmds".equals(command)) {
                menu.showCommands();
            } else {
                menu.showUnknownCommandError(command);
            }
        });
        cH.setActionForEvent(StandardViewEventType.CLOSE, () -> {
            System.exit(0);
        });
        menu.addObserver(cH);
        menu.display();
    }

}
