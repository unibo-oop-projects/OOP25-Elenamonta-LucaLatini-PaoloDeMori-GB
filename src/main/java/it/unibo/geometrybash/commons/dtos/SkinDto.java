package it.unibo.geometrybash.commons.dtos;

/**
 * DTO that describes a player skin using two sprite layers and two user-defined
 * colors.
 *
 * @param outerSprite    the outer layer sprite
 * @param innerSprite    the inner layer sprite
 * @param primaryColor   the tint color for the outer layer
 * @param secondaryColor the tint color for the inner layer
 */
public record SkinDto(
        String outerSprite,
        String innerSprite,
        int primaryColor,
        int secondaryColor) {
}
