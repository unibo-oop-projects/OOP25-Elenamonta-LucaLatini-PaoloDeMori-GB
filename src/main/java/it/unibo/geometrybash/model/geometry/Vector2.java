package it.unibo.geometrybash.model.geometry;

/**
 * Represents a 2D vector or point with integer coordinates.
 *
 * <p>
 * This record is immutable and provides the X and Y coordinates
 * of a point in a 2-dimensional space.
 *
 * @param x the X coordinate
 * @param y the Y coordinate
 */
public record Vector2(float x, float y) { }
