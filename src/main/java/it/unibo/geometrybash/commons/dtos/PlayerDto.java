package it.unibo.geometrybash.commons.dtos;

/**
 * DTO for player rendering.
 *
 * @param x         the X position
 * @param y         the Y position
 * @param width     the width
 * @param height    the height
 * @param isActive  if the player is active
 * @param hasShield whether player has active shield
 * @param skin      the player skin configuration
 */
public record PlayerDto(
        float x,
        float y,
        float width,
        float height,
        boolean isActive,
        boolean hasShield,
        SkinDto skin) {
}
