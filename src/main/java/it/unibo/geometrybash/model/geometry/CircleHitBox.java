package it.unibo.geometrybash.model.geometry;

/**
 * Represents a circular collision shape defined by a radius.
 *
 * <p>
 * Ideal for round objects like coins and other powewr-ups which have a circle hitbox
 */
public final class CircleHitBox implements Shape {

    private final float radius;

    /**
     * Constructs a CircleHitBox with the given radius.
     *
     * @param radius the radius of the circle( must be positive)
     * @throws IllegalArgumentException if radius is not positive
     */
    public CircleHitBox(final float radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }

    /**
     * Returns the radius of the hitbox.
     *
     * @return the radius
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * Returns the diameter as the bounding box width.
     *
     * @return the diameter (2 * radius)
     */
    @Override
    public float getWidth() {
        return this.radius * 2;
    }

    /**
     * Returns the diameter as the bounding box height.
     *
     * @return the diameter (2 * radius)
     */
    @Override
    public float getHeight() {
        return this.radius * 2;
    }

}
