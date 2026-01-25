package it.unibo.geometrybash.model.level.loader;

import java.util.List;

/**
 * The data structure representing the level created using a file.
 */
public interface LevelData {

    /**
     * Gets the name of the level.
     * 
     * @return the name of the level
     */
    String getName();

    /**
     * Gets the player start position on the x axis.
     * 
     * @return the x start position
     */
    float getPlayerStarterX();

    /**
     * Gets the player start position on the y axis.
     * 
     * @return the y start position
     */
    float getPlayerStarterY();

    /**
     * Gets the position of the finish line of the game on the x axis.
     * 
     * @return the position of the finish line of the game.
     */
    float getWinX();

    /**
     * Gets the level of the base floor of the level.
     * 
     * @return the y value of the floor position.
     */
    int getFloorLevelY();

    /**
     * Gets the horiontal number of cells in the game.
     * 
     * @return the horiontal number of cells in the game.
     */
    int getWidth();

    /**
     * Gets the list of datastructures that represent the powerups in the file.
     * 
     * @return the list of datastructures that represent the powerups.
     */
    List<? extends PowerUpData> getPowerUps();

    /**
     * Gets the list of datastructures that represent the obstacles in the file.
     * 
     * @return the list of datastructures that represent the obstacles.
     */
    List<? extends ObstacleData> getObs();

}
