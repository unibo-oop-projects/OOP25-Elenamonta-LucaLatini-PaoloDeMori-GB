package it.unibo.geometrybash.model.level.loader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import it.unibo.geometrybash.model.level.map.Cell;
import it.unibo.geometrybash.model.level.map.CellImpl;
import it.unibo.geometrybash.model.level.map.GameMap;
import it.unibo.geometrybash.model.level.map.GameMapImpl;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.Coordinate;
import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.level.LevelImpl;
import it.unibo.geometrybash.model.level.loader.exception.LoadingFileException;
import it.unibo.geometrybash.model.powerup.PowerUp;
import it.unibo.geometrybash.model.powerup.PowerUpFactory;
import it.unibo.geometrybash.model.powerup.PowerUpType;

/**
 * A {@link LevelLoader} implementation using Gson to parse it from a json file.
 */
public class LevelLoaderImpl implements LevelLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(LevelLoaderImpl.class);
    private static final String STANDARD_EXCEPTION_MESSAGE = "The level file obstacle's section isn't correctly formatted";

    /**
     * Given an object representation of the loaded level returns a level Map.
     * 
     * @param data object representation of the loaded level
     * @return A level Map {@see Map}
     * @throws LoadingFileException if a problem occured during the map creation
     */
    private Map<Coordinate, Cell> loadLevel(final JsonLevelData data) throws LoadingFileException {
        final Map<Coordinate, Cell> loadedData = new HashMap<>();

        if (data.getObs() != null) {
            for (final JsonObstacleData obs : data.getObs()) {
                fromDataToObstacle(obs.getType(), obs, loadedData);
            }
        }

        if (data.getPowerUps() != null) {
            for (final JsonPowerUpData pUps : data.getPowerUps()) {
                fromDataToPowerUp(pUps.getType(), pUps, loadedData);
            }
        }

        for (int x = 0; x < data.getWidth(); x++) {
            final Coordinate c = new Coordinate(x, data.getFloorLevelY());
            if (!loadedData.containsKey(c)) {
                final Obstacle ob = ObstacleFactory.create(ObstacleType.BLOCK,
                        new Vector2((float) c.x(), (float) c.y()));
                loadedData.put(c, new CellImpl(ob));
            }
        }

        return loadedData;

    }

    /**
     * Given a {@link ObstacleType} and the data representation of the obstacle in
     * the file, populates a
     * datastructure with the coordinates and the Cell representing the obstacle.
     * 
     * @param type           the type of the obstacle
     * @param obstaclesData  the data representation of the obstacle
     * @param cellsContainer the datastructure to populate
     * @throws LoadingFileException if a problem occured during the map creation
     */
    private void fromDataToObstacle(final ObstacleType type, final JsonObstacleData obstaclesData,
            final Map<Coordinate, Cell> cellsContainer)
            throws LoadingFileException {
        for (final Coordinate coordinate : obstaclesData.getCoordinates()) {
            try {
                if (cellsContainer.containsKey(coordinate)) {
                    LOGGER.error("Two obstacles in the level file have the same coordinate");
                    throw new LoadingFileException("Two obstacles in the level file have the same coordinate");
                }
                final Obstacle ob = ObstacleFactory.create(type,
                        new Vector2((float) coordinate.x(), (float) coordinate.y()));
                final Cell cell = new CellImpl(ob);
                cellsContainer.put(coordinate, cell);
            } catch (final IllegalArgumentException e) {
                LOGGER.error(STANDARD_EXCEPTION_MESSAGE);
                throw new LoadingFileException("Exception occured while parsing an obstacle", e);
            }
        }
    }

    /**
     * Given a {@link PowerUpType} and the data representation of the powerup in
     * the file, populates a
     * datastructure with the coordinates and the Cell representing the powerup.
     * 
     * @param type           the type of the powerup
     * @param powerUpsData   the data representation of the powerup
     * @param cellsContainer the datastructure to populate
     * @throws LoadingFileException if a problem occured during the map creation
     */
    private void fromDataToPowerUp(final PowerUpType type, final JsonPowerUpData powerUpsData,
            final Map<Coordinate, Cell> cellsContainer)
            throws LoadingFileException {
        for (final Coordinate coordinate : powerUpsData.getCoordinates()) {
            try {
                if (cellsContainer.containsKey(coordinate)) {
                    LOGGER.error("Two powerups in the level file have the same coordinate");
                    throw new LoadingFileException("Two powerups in the level file have the same coordinate");
                }
                final PowerUp<?> ob = PowerUpFactory.create(type,
                        new Vector2((float) coordinate.x(), (float) coordinate.y()));
                final Cell cell = new CellImpl(ob);
                cellsContainer.put(coordinate, cell);
            } catch (final IllegalArgumentException e) {
                LOGGER.error(STANDARD_EXCEPTION_MESSAGE);
                throw new LoadingFileException(STANDARD_EXCEPTION_MESSAGE, e);
            }
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level levelLoaderFromInputStream(final InputStream jsonFile) throws LoadingFileException {

        final String standardWrongFileFormatException = "The level file isn't correctly formatted or doesn't exist ";
        final Gson jsonReader = new Gson();

        try (Reader r = new InputStreamReader(jsonFile, StandardCharsets.UTF_8)) {
            final JsonLevelData data = jsonReader.fromJson(r, JsonLevelData.class);
            final Map<Coordinate, Cell> loadedLevel = loadLevel(data);
            final GameMap gM = new GameMapImpl(loadedLevel);
            return new LevelImpl(data.getName(), new Vector2(data.getPlayerStarterX(), data.getPlayerStarterY()), gM,
                    data.getWinX());
        } catch (final LoadingFileException | JsonSyntaxException | IOException e) {
            LOGGER.error(standardWrongFileFormatException + e.getMessage());
            throw new LoadingFileException(
                    standardWrongFileFormatException + e.getMessage(), e);
        }
    }

}
