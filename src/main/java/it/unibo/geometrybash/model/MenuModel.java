package it.unibo.geometrybash.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Model that stores the history of user commands.
 */
public final class MenuModel {

    public static final List<String> LEVELS_NAME_LIST = List.of("Level1", "DemoLevel");

    /**
     * Stores the history of user commands.
     */
    private final List<String> history = new LinkedList<>();

    /**
     * Creates a new {@code MenuModel}.
     */
    public MenuModel() {
        // default constructor.
    }

    /**
     * Adds a command to the history.
     *
     * @param command the command to store
     */
    public void addCommand(final String command) {
        this.history.add(command);
    }

    /**
     * Returns a copy of the command history.
     *
     * @return the list of stored commands
     */
    public List<String> getHistory() {
        return new LinkedList<>(this.history);
    }

    /**
     * Returns a copy of the level's names.
     *
     * @return the list of level's names.
     */
    public List<String> getLevelsNames() {
        return new LinkedList<>(LEVELS_NAME_LIST);
    }
}
