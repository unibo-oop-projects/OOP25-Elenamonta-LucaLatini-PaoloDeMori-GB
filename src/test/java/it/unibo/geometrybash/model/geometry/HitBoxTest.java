package it.unibo.geometrybash.model.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.exception.InvalidHitBoxConfiguration;

class HitBoxTest {
    private static final double EPS = 1e-6;

    @Test
    void testWidthAndHeightForTriangle() {
        final HitBox hb = new HitBox(List.of(
            new Vector2(0, 0),
            new Vector2(10, 0),
            new Vector2(10, 5)
        ));
        final float expectedWidth = 10.0f;
        final float expectedHeight = 5.0f;
        assertEquals(expectedWidth, hb.getWidth(), EPS);
        assertEquals(expectedHeight, hb.getHeight(), EPS);
    }

    @Test
    void testWidthAndHeightForHitBoxWithNegativeCoordinates() {
        final HitBox hb = new HitBox(List.of(
            new Vector2(-10, -5),
            new Vector2(0, -5),
            new Vector2(0, 5)
        ));

        assertEquals(10.0, hb.getWidth(), EPS);
        assertEquals(10.0, hb.getHeight(), EPS);
    }

    @Test
    void testImmutableListOfVertices() {
        final List<Vector2> original = new ArrayList<>(List.of(
            new Vector2(0, 0),
            new Vector2(1, 0),
            new Vector2(1, 1)
        ));

        final HitBox hb = new HitBox(original);
        original.set(0, new Vector2(100, 100));
        assertNotEquals(original.get(0), hb.getVertices().get(0));
        final List<Vector2> returned = hb.getVertices();
        assertThrows(UnsupportedOperationException.class,
            () -> returned.add(new Vector2(2, 2)));
    }

    @Test
    void testThrowWhenEmptyVertexList() {
        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of()));
    }

    @Test
    void testThrowWhenTooFewVertices() {
        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(1, 0))));
    }

    @Test
    void testThrowsWhenVertexIsNull() {
        assertThrows(NullPointerException.class, () -> new HitBox(List.of(
                new Vector2(0, 0),
                null,
                new Vector2(1, 1))));
    }

    @Test
    void testThrowsOnNonFiniteCoordinates() {
        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(Float.NaN, 0),
                new Vector2(1, 1))));

        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(Float.POSITIVE_INFINITY, 0),
                new Vector2(1, 1))));
    }

    @Test
    void testThrowsOnDuplicateVertices() {
        final Vector2 p = new Vector2(1, 1);
        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of(
                new Vector2(0, 0), p, p)));
    }

    @Test
    void testThrowsOnConcavePolygon() {
        assertThrows(InvalidHitBoxConfiguration.class, () -> new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(2, 0),
                new Vector2(1, 1),
                new Vector2(2, 2),
                new Vector2(0, 2))));
    }
}
