package it.unibo.geometrybash.commons.dtos;

/**
 * DTO for power-up rendering.
 *
 * @param x        the X position
 * @param y        the Y position
 * @param width    the width
 * @param height   the height
 * @param isActive if the power-up is active
 * @param type     the power-up type identifier ({@link DtoPowerUpType})
 */
public record PowerUpDto(
        float x,
        float y,
        float width,
        float height,
        boolean isActive,
        DtoPowerUpType type) {
}
