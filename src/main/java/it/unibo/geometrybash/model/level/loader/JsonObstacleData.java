package it.unibo.geometrybash.model.level.loader;

import java.util.List;

import it.unibo.geometrybash.model.level.Coordinate;
import it.unibo.geometrybash.model.obstacle.ObstacleType;

/**
 * The datastructure representing the infos of obstacle of the same type in the json
 * level file.
 * 
 * @param coordinates the coordinates of the obstacle of the given type
 * @param type the type of the obstacles
 */
record JsonObstacleData(List<Coordinate> coordinates, ObstacleType type) implements ObstacleData {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coordinate> getCoordinates() {
        return this.coordinates();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObstacleType getType() {
        return this.type;
    }
}
