package it.unibo.geometrybash.model.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.map.Cell;
import it.unibo.geometrybash.model.level.map.GameMap;
import it.unibo.geometrybash.model.powerup.Coin;

/**
 * Concrete implementation of {@link Level}.
 */
public final class LevelImpl implements Level {

    private final String name;
    private final Vector2 playerStartPos;
    private final GameMap map;
    private final float winX;

    /**
     * Creates new level with the specified logic properties.
     *
     * @param name           the descriptive name of the level.
     * @param playerStartPos the precise {@link Vector2} position where the player
     *                       spawn.
     * @param map            the {@link GameMap} containing the grid layout of the
     *                       level's tiles.
     * @param winX           the horizontal coordinate that the player must cross
     *                       for win the game.
     */
    public LevelImpl(final String name,
            final Vector2 playerStartPos,
            final GameMap map,
            final float winX) {

        this.name = name;
        this.playerStartPos = playerStartPos;
        this.map = map;
        this.winX = winX;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector2 getPlayerStartPosition() {
        return this.playerStartPos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject<?>> getGameObjectsInRange(final Vector2 start, final Vector2 end) {
        final int xStart = (int) Math.floor(start.x());
        final int xEnd = (int) Math.floor(end.x());
        final List<Cell> cellsInRange = this.map.getCellInRange(xStart, xEnd);
        final List<GameObject<?>> results = new ArrayList<>();

        for (final Cell cell : cellsInRange) {
            if (cell.getGameObject().isPresent()) {
                results.add(cell.getGameObject().get());
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean playerWin(final Vector2 playerPos) {
        return playerPos.x() >= this.winX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameObject<?>> getGameObjectAtPosition(final Vector2 pos) {
        final int xPos = (int) Math.floor(pos.x());
        final int yPos = (int) Math.floor(pos.y());

        return this.map.getCell(new Coordinate(xPos, yPos))
                .flatMap(Cell::getGameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject<?>> getAllGameObject() {
        final List<GameObject<?>> allObjects = new ArrayList<>();
        for (final Cell cell : this.map.getAllCells()) {
            if (cell.getGameObject().isPresent()) {
                allObjects.add(cell.getGameObject().get());
            }
        }
        return allObjects;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public float getWinX() {
        return this.winX;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalCoins() {
        return (int) this.getAllGameObject().stream()
                .filter(obj -> obj instanceof Coin)
                .count();
    }

}
