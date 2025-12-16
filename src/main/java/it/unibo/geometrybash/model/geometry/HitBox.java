package it.unibo.geometrybash.model.geometry;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.ToIntFunction;

/**
 * The HitBox class represents a polygonal shape defined by a list of vertices.
 */
public final class HitBox {
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
     * @param vertices the list of vertices; must contain at least {@value #MIN_VERTEX} points
     * @throws IllegalArgumentException if the list contains fewer than {@value #MIN_VERTEX} vertices
     */
    public HitBox(final List<Vector2> vertices) {
        if (vertices.size() < MIN_VERTEX) {
            throw new IllegalArgumentException(
                "A HitBox must have at least " + MIN_VERTEX + " vertices"
            );
        }
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
     * Calculates the width of the HitBox as the difference between the maximum and minimum X coordinates.
     *
     * @return the width of the HitBox
     * @throws NoSuchElementException if the list of vertices is empty
     */
    public int getWidth() {
        return deltaInt(Axis.X);
    }

    /**
     * Calculates the height of the HitBox as the difference between the maximum and minimum Y coordinates.
     *
     * @return the height of the HitBox
     * @throws NoSuchElementException if the list of vertices is empty
     */
    public int getHeight() {
        return deltaInt(Axis.Y);
    }

    private int deltaInt(final Axis axis) {
        final ToIntFunction<Vector2> extractor = axis == Axis.X ? Vector2::x : Vector2::y;

        final IntSummaryStatistics stats = this.vertices.stream()
                            .mapToInt(extractor)
                            .summaryStatistics();

        return stats.getMax() - stats.getMin();
    }
}
