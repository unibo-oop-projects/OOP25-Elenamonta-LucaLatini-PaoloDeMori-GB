package it.unibo.geometrybash.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Model that stores the history of user commands.
 */
public final class MenuModel {

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

}
