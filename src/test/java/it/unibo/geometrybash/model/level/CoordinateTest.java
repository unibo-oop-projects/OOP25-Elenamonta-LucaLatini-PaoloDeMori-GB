package it.unibo.geometrybash.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * test for {@link Coordinate}.
 */
class CoordinateTest {
    @Test
    void testCoordinateValues() {
        final Coordinate coord = new Coordinate(10, 20);
        assertEquals(10, coord.x());
        assertEquals(20, coord.y());
    }

    @Test
    void testEquality() {
        final Coordinate coordinate1 = new Coordinate(5, 5);
        final Coordinate coordinate2 = new Coordinate(5, 5);
        assertEquals(coordinate1, coordinate2);
    }
}
