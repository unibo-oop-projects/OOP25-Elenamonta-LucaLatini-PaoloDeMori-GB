package it.unibo.geometrybash.commons.input;

import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEventType;

/**
 * Enum representing standard view event types.
 * {@link #GENERIC} represents a custom terminal command.
 *
 * @see ViewEventType
 */
public enum StandardViewEventType {

    /**
     * Event to start the game.
     */
    START("start"),

    /**
     * Event to resume a paused game.
     */
    RESUME("resume"),

    /**
     * Event to restart the current level.
     */
    RESTART("restart"),

    /**
     * Event to close the application.
     */
    CLOSE("close"),

    /**
     * Event when player push the button jump action (SPACE or UP arrow).
     */
    JUMP("jump"),

    /**
     * Event when player opens menu/pause with the keyboard's button (ESC key).
     */
    MENU("menu"),

    /**
     * Represents a generic terminal command.
     * The actual command string is stored in {@link ViewEventType#getCommand()}.
     */
    GENERIC("generic");

    private final String commandName;

    StandardViewEventType(final String commandName) {
        this.commandName = commandName;
    }

    /**
     * Returns the command name in lowercase.
     *
     * @return the command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Checks if this event type represents a user input action (keyboard).
     *
     * @return true if this is a user input event (JUMP, MENU), false otherwise
     */
    public boolean isUserInput() {
        return this == JUMP || this == MENU;
    }

    /**
     * Checks if this event type represents a GUI action (buttons, menu clicks).
     *
     * @return true if this is a GUI event, false otherwise
     */
    public boolean isGuiEvent() {
        return this == START || this == RESUME
                || this == RESTART || this == CLOSE;
    }
}
