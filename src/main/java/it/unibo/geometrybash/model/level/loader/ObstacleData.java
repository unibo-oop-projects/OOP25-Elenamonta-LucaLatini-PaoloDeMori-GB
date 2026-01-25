package it.unibo.geometrybash.model.level.loader;

import java.util.List;

import it.unibo.geometrybash.model.level.Coordinate;
import it.unibo.geometrybash.model.obstacle.ObstacleType;

/**
 * The datastructure representing the infos of obstacle of the same type in the
 * level file.
 */
interface ObstacleData {

    /**
     * Returns the containing the coordinates of every obstacle of the same type in
     * the level.
     * 
     * @return the list of coordinates of every obstacle
     */
    List<Coordinate> getCoordinates();

    /**
     * Returns the type of the obstacle present in the level linked with the
     * coordinates of each of its instances.
     * 
     * @return the type of the obstacles.
     */
    ObstacleType getType();

}
