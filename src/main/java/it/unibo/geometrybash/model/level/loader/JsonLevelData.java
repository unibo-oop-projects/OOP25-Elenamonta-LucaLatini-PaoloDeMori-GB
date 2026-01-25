package it.unibo.geometrybash.model.level.loader;

import java.util.List;

/**
 * The data structure representing the level created using a json.
 * 
 * @param name           the name of the level
 * @param playerStarterX the starting position on the x axis of the player
 * @param playerStarterY the starting position on the y axis of the player
 * @param winX           the finish line position on the x axis
 * @param floorLevelY    the height of the floor represented as a coordinate on
 *                       the y axis
 * @param width          the length in cells of the level
 * @param powerUps       the list of powerups
 * @param obs            the list of obstacles
 */
record JsonLevelData(
        String name,
        float playerStarterX,
        float playerStarterY,
        float winX,
        int floorLevelY,
        int width,
        List<JsonPowerUpData> powerUps,
        List<JsonObstacleData> obs) implements LevelData {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPlayerStarterY() {
        return this.playerStarterY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getPlayerStarterX() {
        return this.playerStarterX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float getWinX() {
        return this.winX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFloorLevelY() {
        return this.floorLevelY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth() {
        return width();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JsonPowerUpData> getPowerUps() {
        return this.powerUps();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JsonObstacleData> getObs() {
        return this.obs;
    }

}
