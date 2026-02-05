package it.unibo.geometrybash.commons.dtos;

/**
 * DTO that describes a player skin using two sprite layers and two user-defined
 * colors.
 * 
 * @param primaryColor   the tint color for the outer layer
 * @param secondaryColor the tint color for the inner layer
 */
public record SkinDto(
        int primaryColor,
        int secondaryColor) {
}
