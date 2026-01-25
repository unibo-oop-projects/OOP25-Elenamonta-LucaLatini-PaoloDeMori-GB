package it.unibo.geometrybash.model.level.loader;

import java.util.List;

import it.unibo.geometrybash.model.level.Coordinate;
import it.unibo.geometrybash.model.powerup.PowerUpType;

/**
 * The datastructure representing the infos of powerups of the same type in the json
 * level file.
 * 
 * @param coordinates the coordinates of the powerups of the given type
 * @param type the type of the powerups
 */
record JsonPowerUpData(List<Coordinate> coordinates, PowerUpType type) implements PowerUpData {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Coordinate> getCoordinates() {
        return this.coordinates;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PowerUpType getType() {
        return this.type;
    }
}
