package it.unibo.geometrybash.model.level.loader;

import java.util.List;

import it.unibo.geometrybash.model.level.Coordinate;
import it.unibo.geometrybash.model.powerup.PowerUpType;

/**
 * The datastructure representing the infos of powerups of the same type in the
 * level file.
 */
public interface PowerUpData {
    /**
     * Returns the containing the coordinates of every powerup of the same type in
     * the level.
     * 
     * @return the list of coordinates of every powerup
     */
    List<Coordinate> getCoordinates();

    /**
     * Returns the type of the obstacle present in the level linked with the
     * coordinates of each of its instances.
     * 
     * @return the type of the obstacles.
     */
    PowerUpType getType();

}
