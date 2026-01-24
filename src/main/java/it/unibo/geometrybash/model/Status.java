package it.unibo.geometrybash.model;

/**
 * The representation of the State of the game.
 */
public enum Status {
    /**
     * Set when the player died.
     */
    GAMEOVER("GameOver"),
    /**
     * Set when the user is playing.
     */
    PLAYING("Playing"),
    /**
     * Set when the user is in the menu Pause.
     */
    ONPAUSE("OnPause"),
    /**
     * Set when the user hasn't started the game yet.
     */
    NEVERSTARTED("NeverStarted");

    private final String name; //the name of the type

    Status(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the type of the event.
     * 
     * @return a String representation of the type.
     */
    public String getName() {
        return this.name;
    }
}
