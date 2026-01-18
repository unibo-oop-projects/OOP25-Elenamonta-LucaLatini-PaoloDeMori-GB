package it.unibo.geometrybash.model.obstacle;

/**
 * Enumeration of obstacle types which are in the game.
 */
public enum ObstacleType {

    /**
     *  Triangular spike kill the player on contact with it.
     */
    SPIKE("spike", true),

    /**
     *  Is a solid block where player can lands on it.
     */
    BLOCK("block", false);

    private final String name;
    private final boolean deadly;

    ObstacleType(final String name, final boolean deadly) {
        this.name = name;
        this.deadly = deadly;
    }

    /**
     * Return the name of this obstacle type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if this obstacle type can kills the player on contact.
     *
     * @return true if deadly, false otherwise
     */
    public boolean isDeadly() {
        return this.deadly;
    }

}
