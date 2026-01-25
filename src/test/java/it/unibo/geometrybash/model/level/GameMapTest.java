package it.unibo.geometrybash.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.map.CellImpl;
import it.unibo.geometrybash.model.level.map.GameMap;
import it.unibo.geometrybash.model.level.map.GameMapImpl;
import it.unibo.geometrybash.model.level.map.Cell;
import it.unibo.geometrybash.model.level.map.exceptions.GameMapOperationException;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;

/**
 * Test for {@link GameMap}.
 */
class GameMapTest {

    @Test
    void testGetPresentCell() throws GameMapOperationException {
        final Coordinate coordinate = new Coordinate(1, 1);
        final Cell cell = new CellImpl(ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(0, 0)));
        final GameMap map = new GameMapImpl(Map.of(coordinate, cell));
        assertEquals(cell, map.getPresentCell(coordinate));
    }

    @Test
    void testGetPresentCellThrowsException() {
        final GameMap map = new GameMapImpl(Map.of());
        final Coordinate missCoordinate = new Coordinate(5, 5);
        assertThrows(GameMapOperationException.class, () -> {
            map.getPresentCell(missCoordinate);
        });
    }

    @Test
    void testCellInRange() {
        final Coordinate c1 = new Coordinate(1, 0);
        final Coordinate c2 = new Coordinate(2, 0);
        final int startX = 10;
        final int startY = 20;
        final GameMap map = new GameMapImpl(Map.of(
                c1, new CellImpl(ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(0, 0))),
                c2, new CellImpl(ObstacleFactory.create(ObstacleType.SPIKE, new Vector2(0, 0)))));
        assertEquals(2, map.getCellInRange(1, 2).size());
        assertEquals(0, map.getCellInRange(startX, startY).size());
        assertEquals(2, map.getAllCells().size());
    }

    @Test
    void testIsCoordinateValid() {
        final Coordinate valid = new Coordinate(5, 5);
        final Coordinate notValid = new Coordinate(0, 0);
        final GameMap map = new GameMapImpl(
                Map.of(valid, new CellImpl(ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(5, 5)))));
        assertTrue(map.isCoordinateValid(valid));
        assertFalse(map.isCoordinateValid(notValid));
    }

    @Test
    void testGetCell() {
        final Coordinate c = new Coordinate(5, 5);
        final Cell cell = new CellImpl(ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(0, 0)));
        final GameMap map = new GameMapImpl(Map.of(c, cell));
        assertTrue(map.getCell(c).isPresent());
        assertEquals(cell, map.getCell(c).get());
        assertTrue(map.getCell(new Coordinate(0, 0)).isEmpty());
        assertEquals(Optional.empty(), map.getCell(new Coordinate(0, 0)));
    }

    @Test
    void testGetPresentCellExceptionHandling() {
        final int invalid = 99;
        final GameMap map = new GameMapImpl(Map.of(
                new Coordinate(1, 1), new CellImpl(ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(0, 0)))));
        assertThrows(GameMapOperationException.class, () -> {
            map.getPresentCell(new Coordinate(invalid, 1));
        });
        assertThrows(GameMapOperationException.class, () -> {
            map.getPresentCell(new Coordinate(1, invalid));
        });
    }
}
