package it.unibo.geometrybash.model.obstacle;

/**
 * Enumeration of obstacle types which are in the game.
 */
public enum ObstacleType {

    /**
     *  Triangular spike kill the player on contact with it.
     */
    SPIKE("spike"),

    /**
     *  Is a solid block where player can lands on it.
     */
    BLOCK("block");

    private final String name;

    ObstacleType(final String name) {
        this.name = name;
    }

    /**
     * Return the name of this obstacle type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

}
