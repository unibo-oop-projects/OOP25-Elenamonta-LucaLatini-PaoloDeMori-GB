package it.unibo.geometrybash.model.geometry;

/**
 * Represents a geometric shape used for collision detection.
 *
 * <p>This interface provides common method for different shape types
 * (polygonal,circular) that can be used as collision boundaries
 */
public interface Shape {

    /**
     * Return the width of the bounding box of this shape.
     *
     * @return the width
     */
    float getWidth();

    /**
     * Returns the height of the bounding box of this shape.
     *
     * @return the height
     */
    float getHeight();

}
