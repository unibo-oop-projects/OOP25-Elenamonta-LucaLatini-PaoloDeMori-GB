package it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern;

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
     * Event to return to the home menu.
     */
    HOME("home"),

    /**
     * Event to restart the current level.
     */
    RESTART("restart"),

    /**
     * Event to close the application.
     */
    CLOSE("close"),

    /**
     * Event to open the inventory.
     */
    INVENTORY("inventory"),

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
}
