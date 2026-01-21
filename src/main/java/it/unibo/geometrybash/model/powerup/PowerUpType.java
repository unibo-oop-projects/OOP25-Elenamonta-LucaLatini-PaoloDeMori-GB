package it.unibo.geometrybash.model.powerup;

/**
 * Enumaration of power-up types in the game.
 */
public enum PowerUpType {

    /**
     * Collectible coin, adds to the player's score, they can be used for shop new skins.
     */
    COIN("coin"),

    /**
     * Shield, grants temporary invincibility.
     */
    SHIELD("shield"),

    /**
     *  Speed boost temporarily increase player's speed.
     */
    SPEED_BOOST("speedboost");

    private final String name;

    PowerUpType(final String name) {
        this.name = name;
    }

    /**
     * Return the identifier name of this power-up type.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

}
