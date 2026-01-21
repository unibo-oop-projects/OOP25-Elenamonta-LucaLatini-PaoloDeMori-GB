package it.unibo.geometrybash.model.obstacle;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Factory for creating {@link Obstacle} instances.
 *
 * <p>
 * Centralizes obstacle instantiation logic, making it easy to
 * create obstacles from level data or configuration files.
 */
public final class ObstacleFactory {

    /**
     * Create new obstacle.
     */
    private ObstacleFactory() {
        // Default constructor for Javadoc compliance
    }

    /**
     * Creates an obstacle of the specified type at the given position.
     *
     * @param type     the obstacle's type to create
     * @param position the obstacle's position in the game world
     * @return new {@link Obstacle} istance
     */
    public static Obstacle create(final ObstacleType type, final Vector2 position) {
        return switch (type) {
            case SPIKE -> new Spike(position);
            case BLOCK -> new Block(position);
        };
    }

}
