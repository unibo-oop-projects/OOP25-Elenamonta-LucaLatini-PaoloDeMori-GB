package it.unibo.geometrybash.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * test for {@link Coordinate}.
 */
class CoordinateTest {
    @Test
    void testCoordinateValues() {
        final int x = 10;
        final int y = 20;
        final Coordinate coord = new Coordinate(x, y);
        assertEquals(x, coord.x());
        assertEquals(y, coord.y());
    }

    @Test
    void testEquality() {
        final Coordinate coordinate1 = new Coordinate(5, 5);
        final Coordinate coordinate2 = new Coordinate(5, 5);
        assertEquals(coordinate1, coordinate2);
    }
}
