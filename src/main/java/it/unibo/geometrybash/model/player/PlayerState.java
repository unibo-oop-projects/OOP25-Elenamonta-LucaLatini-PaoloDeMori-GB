package it.unibo.geometrybash.model.player;

/**
 * Represents the current state of the player.
 *
 * <p>
 * This enum is used to manage the graphics animation of the player
 * </p>
 */
public enum PlayerState {

    /**
     * The player is currently in the air after performing a jump.
     */
    JUMPING("jump"),

    /**
     * The player is standing or moving while in contact with the ground.
     */
    ON_GROUND("on ground");

    private final String name;

    PlayerState(final String name) {
        this.name = name;
    }

    /**
     * Return the identifier name of player state.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
