package it.unibo.geometrybash.commons.dtos;

/**
 * Defines obstacle types for {@link PowerUpDto}.
 */
public enum DtoPowerUpType {

    /**
     * A collectible coin that increases the score.
     */
    COIN,

    /**
     * A power-up that temporarily increases the player speed.
     */
    SPEED_BOOST,

    /**
     * A power-up that provides protection to the player, for only one mortal collision.
     */
    SHIELD
}
