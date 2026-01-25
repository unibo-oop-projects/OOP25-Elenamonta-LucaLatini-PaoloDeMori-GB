package it.unibo.geometrybash.model.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.map.Cell;
import it.unibo.geometrybash.model.level.map.CellImpl;
import it.unibo.geometrybash.model.level.map.GameMap;
import it.unibo.geometrybash.model.level.map.GameMapImpl;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;

/**
 * Test for {@link Level}.
 */
class LevelTest {

    /**
     * level name for the test.
     */
    private static final String LEVEL_NAME = "level";

    @Test
    void testLevelSettings() {
        final String name = "level";
        final Vector2 start = new Vector2(0, 0);
        final float winDistance = 100.0f;
        final Level level = new LevelImpl(name, start, new GameMapImpl(Map.of()), winDistance);
        assertEquals(name, level.getName());
        assertEquals(start, level.getPlayerStartPosition());
    }

    @Test
    void testWinCondition() {
        final int firstX = 5;
        final int secondY = 10;
        final float winX = 10.0f;
        final Level level = new LevelImpl("test", new Vector2(0, 0), new GameMapImpl(Map.of()), winX);
        assertFalse(level.playerWin(new Vector2(firstX, 0)));
        assertTrue(level.playerWin(new Vector2(secondY, 0)));
    }

    @Test
    void testGameObjectInRange() {
        final Obstacle block1 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(1, 0));
        final Obstacle spike1 = ObstacleFactory.create(ObstacleType.SPIKE, new Vector2(2, 0));
        final Obstacle block2 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(10, 0));
        final Map<Coordinate, Cell> cells = Map.of(
            new Coordinate(1, 0), new CellImpl(block1),
            new Coordinate(2, 0), new CellImpl(spike1),
            new Coordinate(5, 0), new CellImpl(block2)
        );
        final GameMap map = new GameMapImpl(cells);
        final Level level = new LevelImpl(LEVEL_NAME, new Vector2(0, 0), map, 10);
        final List<GameObject<?>> obstacles = level.getGameObjectsInRange(new Vector2(0.0f, 0.0f), new Vector2(4.0f, 0.0f));
        assertEquals(2, obstacles.size());
        assertTrue(obstacles.contains(block1));
        assertTrue(obstacles.contains(spike1));
        assertFalse(obstacles.contains(block2));
    }

    @Test
    void testGetAllGameObject() {
        final Obstacle block1 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(1, 0));
        final Obstacle spike1 = ObstacleFactory.create(ObstacleType.SPIKE, new Vector2(2, 0));
        final Obstacle block2 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(10, 0));
        final Map<Coordinate, Cell> cells = Map.of(
            new Coordinate(1, 0), new CellImpl(block1),
            new Coordinate(2, 0), new CellImpl(spike1),
            new Coordinate(5, 0), new CellImpl(block2)
        );
        final GameMap map = new GameMapImpl(cells);
        final Level level = new LevelImpl(LEVEL_NAME, new Vector2(0, 0), map, 10);
        final List<GameObject<?>> obstacles = level.getAllGameObject();
        assertEquals(3, obstacles.size());
        assertTrue(obstacles.contains(block1));
        assertTrue(obstacles.contains(spike1));
        assertTrue(obstacles.contains(block2));
    }

    @Test
    void testGetObjectAtPosition() {
        final Obstacle block1 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(1, 0));
        final Obstacle spike1 = ObstacleFactory.create(ObstacleType.SPIKE, new Vector2(2, 0));
        final Obstacle block2 = ObstacleFactory.create(ObstacleType.BLOCK, new Vector2(10, 0));
        final Map<Coordinate, Cell> cells = Map.of(
            new Coordinate(1, 0), new CellImpl(block1),
            new Coordinate(2, 0), new CellImpl(spike1),
            new Coordinate(5, 0), new CellImpl(block2)
        );
        final GameMap map = new GameMapImpl(cells);
        final Level level = new LevelImpl(LEVEL_NAME, new Vector2(0, 0), map, 10);
        final Optional<GameObject<?>> object = level.getGameObjectAtPosition(new Vector2(1, 0));
        assertEquals(block1, object.get());

    }
}
