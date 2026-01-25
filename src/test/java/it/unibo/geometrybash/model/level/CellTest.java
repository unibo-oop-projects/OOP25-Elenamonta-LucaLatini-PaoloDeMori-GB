package it.unibo.geometrybash.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.map.CellImpl;
import it.unibo.geometrybash.model.level.map.Cell;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;

/**
 * test for {@link Cell}.
 */
class CellTest {
    @Test
    void testCellWithNoGameObject() {
        final Obstacle block = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(0, 0));
        final Cell cell = new CellImpl(block);
        assertTrue(cell.getGameObject().isPresent());
        assertEquals(block, cell.getGameObject().get());

    }
}
