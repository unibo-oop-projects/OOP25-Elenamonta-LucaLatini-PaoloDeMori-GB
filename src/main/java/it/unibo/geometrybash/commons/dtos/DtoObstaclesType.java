package it.unibo.geometrybash.commons.dtos;

/**
 * Defines obstacle types for {@link ObstacleDto}.
 */
public enum DtoObstaclesType {

    /**
     * A spike obstacle can kill the player.
     */
    SPIKE,

    /**
     * A solid block where player can land, the collision doesn't kill it.
     */
    BLOCK
}
