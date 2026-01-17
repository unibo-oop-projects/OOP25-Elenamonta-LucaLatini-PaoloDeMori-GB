package it.unibo.geometrybash.model.powerup;

/**
 * Enumaration of power-up types in the game.
 */
public enum PowerUpType {

    /**
     * Collectile coin, adds to the palyer's score, they can be used for shop new skins.
     */
    COIN("coin", false),

    /**
     * Shield, grants temporary invincibility.
     */
    SHIELD("shield", true),

    /**
     *  Speed boos temporarily increase player's speed.
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
     * Check ig this power-up has temporary effect.
     *
     * @return true if temporary, false if permament like the coin
     */
    public boolean isTemporary() {
        return this.temporary;
    }
}
