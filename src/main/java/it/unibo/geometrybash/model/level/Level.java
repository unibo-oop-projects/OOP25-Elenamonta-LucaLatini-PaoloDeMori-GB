package it.unibo.geometrybash.model.level;

import java.util.List;
import java.util.Optional;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Represent a game level, coordinating the map and the high level logic.
 */
public interface Level {

    /**
     * Retrives the level's name.
     *
     * @return the level's name.
     */
    String getName();

    /**
     * Gets the initial position of the player in the level.
     *
     * @return the starting position of the player.
     */
    Vector2 getPlayerStartPosition();

    /**
     * Retrives all the game objects in a specific range.
     *
     * @param start the starting point.
     * @param end   the end point.
     * @return a list of game objects.
     */
    List<GameObject<?>> getGameObjectsInRange(Vector2 start, Vector2 end);

    /**
     * Check if the player's position allows them to win.
     *
     * @param playerPos the current position of the player.
     * @return true if the player won.
     */
    boolean playerWin(Vector2 playerPos);

    /**
     * Return the game object located at a specific spatial position.
     *
     * <p>
     * Convert the Vector2 into a coordinates to identify the corresponding cell in
     * the map.
     * it is usefull for precise interaction checks.
     *
     * @param pos the {@link Vector2} position.
     * @return an Optional containing {@link GameObject} if present at the
     *         calculated coordinates,
     *         or an empty optional if the position is empty.
     */
    Optional<GameObject<?>> getGameObjectAtPosition(Vector2 pos);

    /**
     * Retrives all the game object in the game.
     *
     * @return a list of games object.
     */
    List<GameObject<?>> getAllGameObject();

    /**
     * Retrives the level's x win.
     *
     * @return the win x
     */

    float getWinX();

    /**
     * the total number of coins in the level.
     *
     * @return the number of coins in the level.
     */
    int getTotalCoins();
}
