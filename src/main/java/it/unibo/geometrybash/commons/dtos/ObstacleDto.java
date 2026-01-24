package it.unibo.geometrybash.commons.dtos;

/**
 * DTO for obstacle rendering.
 *
 * @param x        the X position
 * @param y        the Y position
 * @param width    the width
 * @param height   the height
 * @param isActive if the obstacle is active
 * @param type     the obstacle type identifier ({@link DtoObstaclesType})
 */
public record ObstacleDto(
        float x,
        float y,
        float width,
        float height,
        boolean isActive,
        DtoObstaclesType type) {
}
