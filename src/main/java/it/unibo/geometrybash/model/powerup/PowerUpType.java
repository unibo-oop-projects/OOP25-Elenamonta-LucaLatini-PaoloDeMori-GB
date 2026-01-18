package it.unibo.geometrybash.model.powerup;

/**
 * Enumaration of power-up types in the game.
 */
public enum PowerUpType {

    /**
     * Collectible coin, adds to the player's score, they can be used for shop new skins.
     */
    COIN("coin", false),

    /**
     * Shield, grants temporary invincibility.
     */
    SHIELD("shield", false),

    /**
     *  Speed boost temporarily increase player's speed.
     */
    SPEED_BOOST("speedboost", true);

    private final String name;
    private final boolean temporary;

    PowerUpType(final String name, final boolean temporary) {
        this.name = name;
        this.temporary = temporary;
    }

    /**
     * Return the identifier name of this power-up type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Check if this power-up has temporary effect.
     *
     * @return true if temporary, false if permament like the coin
     */
    public boolean isTemporary() {
        return this.temporary;
    }
}
