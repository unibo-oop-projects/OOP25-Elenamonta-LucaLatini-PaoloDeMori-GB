package it.unibo.geometrybash.model.geometry;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

import it.unibo.geometrybash.model.geometry.exception.InvalidHitBoxConfiguration;

/**
 * The HitBox class represents a polygonal shape defined by a list of vertices.
 *
 * <p>
 * A HitBox is defined by a list of vertices in a two-dimensional space.
 * It is assumed that the polygon is convex and that its vertices are
 * provided in counter-clockwise order. These assumptions are required
 * to ensure the correctness of the geometric validation and computations
 * performed by this class.
 * </p>
 */
public final class HitBox implements Shape {
    /**
     * Represents the axes of a 2D coordinate system.
     */
    public enum Axis {
        /** Represents the X-axis (horizontal coordinate). */
        X,
        /** Represents the Y-axis (vertical coordinate). */
        Y
    }

    private static final int MIN_VERTEX = 3;
    private final List<Vector2> vertices;

    /**
     * Constructs a HitBox with a list of vertices.
     *
     * @param vertices the list of vertices
     * @throws InvalidHitBoxConfiguration if the hitbox configuration is invalid
     */
    public HitBox(final List<Vector2> vertices) {
        isValidHitBox(vertices);
        this.vertices = List.copyOf(vertices);
    }

    /**
     * Returns the list of vertices of the HitBox.
     *
     * @return an unmodifiable list of vertices
     */
    public List<Vector2> getVertices() {
        return List.copyOf(this.vertices);
    }

    /**
     * Calculates the width of the HitBox as the difference between the maximum and
     * minimum X coordinates.
     *
     * @return the width of the HitBox
     * @throws NoSuchElementException if the list of vertices is empty
     */
    @Override
    public float getWidth() {
        return delta(Axis.X);
    }

    /**
     * Calculates the height of the HitBox as the difference between the maximum and
     * minimum Y coordinates.
     *
     * @return the height of the HitBox
     * @throws NoSuchElementException if the list of vertices is empty
     */
    @Override
    public float getHeight() {
        return delta(Axis.Y);
    }

    private float delta(final Axis axis) {
        float min = Float.POSITIVE_INFINITY;
        float max = Float.NEGATIVE_INFINITY;

        for (final Vector2 v : this.vertices) {
            final float value = axis == Axis.X ? v.x() : v.y();
            min = Math.min(min, value);
            max = Math.max(max, value);
        }

        return max - min;
    }

    private void isValidHitBox(final List<Vector2> polygonVertices) {
        if (polygonVertices.isEmpty()) {
            throw new InvalidHitBoxConfiguration("Vertices list is empty.");
        }

        if (polygonVertices.size() < MIN_VERTEX) {
            throw new InvalidHitBoxConfiguration("A HitBox must have at least 3 vertices.");
        }

        if (polygonVertices.stream().anyMatch(v -> v == null || !Float.isFinite(v.x()) || !Float.isFinite(v.y()))) {
            throw new InvalidHitBoxConfiguration("HitBox contains invalid vertices");
        }

        if (polygonVertices.stream().distinct().count() != polygonVertices.size()) {
            throw new InvalidHitBoxConfiguration("Duplicate vertices found.");
        }

        /*
         * If all determinants have the same sign, the HitBox represents a convex
         * polygon; otherwise, it is concave.
         */
        final List<Float> crosses = getCrossesList(polygonVertices);
        final boolean sign = crosses.getFirst() > 0;
        if (crosses.stream().anyMatch(c -> (c > 0) != sign)) {
            throw new InvalidHitBoxConfiguration("Concave HitBox are not supported.");
        }
    }

    private List<Float> getCrossesList(final List<Vector2> verticesList) {
        final int n = verticesList.size();
        return IntStream.range(0, n)
                .mapToObj(i -> {
                    final Vector2 p0 = verticesList.get(i);
                    final Vector2 p1 = verticesList.get((i + 1) % n);
                    final Vector2 p2 = verticesList.get((i + 2) % n);

                    final Vector2 e1 = vectorBetweenConsecutiveVertex(p1, p0);
                    final Vector2 e2 = vectorBetweenConsecutiveVertex(p2, p1);

                    return crossProductFromVertex(e1, e2);
                })
                .toList();
    }

    private Vector2 vectorBetweenConsecutiveVertex(final Vector2 v, final Vector2 u) {
        return new Vector2(
                v.x() - u.x(),
                v.y() - u.y());
    }

    private float crossProductFromVertex(final Vector2 v, final Vector2 u) {
        return v.x() * u.y() - v.y() * u.x();
    }
}
