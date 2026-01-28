package it.unibo.geometrybash.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import it.unibo.geometrybash.commons.dtos.DtoObstaclesType;
import it.unibo.geometrybash.commons.dtos.DtoPowerUpType;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.commons.dtos.ObstacleDto;
import it.unibo.geometrybash.commons.dtos.PlayerDto;
import it.unibo.geometrybash.commons.dtos.PowerUpDto;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.powerup.PowerUp;

/**
 * Mapper responsible for converting the internal GameModel state into the Game
 * state dtos.
 *
 * <p>
 * It manages the view culling, obtaining only the game entities that need to be
 * drawn, after having obtained them it creates the related dtos.
 */
public final class GameStateMapper {

        /**
         * The distance in meters that the player kepts from le left edge of the screen.
         * This way the video player is given the time needed to react to the appearance
         * of obstacles.
         */
        private static final float CAMERA_OFFSET_MARGIN = 5.0f;

        /**
         * Safety margin behind the player for culling.
         * Since the player is kept 5m from the left edge, a 7m margin covers the
         * entire visible area behind plus 2m of safety.
         */
        private static final float CULLING_X_LEFT = 7.0f;

        /**
         * Safety margin ahead of the player for culling.
         * The visibility window is approximately 20 meters wide(1920/96ppm). Since 5
         * meters
         * are behind the player (CAMERA_OFFSET_MARGIN), there are 15 meters visible
         * ahead. An 18m margin (15m + 3m safety) ensures all incoming objects
         * are fetched before they appear on screen.
         */
        private static final float CULLING_X_RIGHT = 18.0f;

        /**
         * Converts the current {@link GameModel} into a {@link GameStateDto}.
         *
         * <p>
         * The method performs culling based on the player position and maps all
         * visible game objects into their corresponding DTO representations.
         *
         * @param model the game model to convert
         * @return the corresponding {@link GameStateDto}
         * @throws ModelExecutionException if a problem occured while collecting the
         *                                 model data.
         * @throws NullPointerException    if the model is null.
         */
        public GameStateDto toDto(final GameModel model) throws ModelExecutionException {
                Objects.requireNonNull(model, "the model can't be null");
                final Player<?> player = model.getPlayer();
                final float playerX = player.getPosition().x();

                final float cameraX = Math.max(0, playerX - CAMERA_OFFSET_MARGIN);

                final Level level = model.getLevel();
                final List<GameObject<?>> visibileObjects = level.getGameObjectsInRange(
                                new Vector2(Math.max(0, playerX - CULLING_X_LEFT), 0),
                                new Vector2(playerX + CULLING_X_RIGHT, 0));

                final List<ObstacleDto> obstacles = visibileObjects.stream()
                                .filter(obj -> obj instanceof Obstacle)
                                .map(obj -> (Obstacle) obj)
                                .map(this::mapObstacle)
                                .collect(Collectors.toList());

                final List<PowerUpDto> powerUp = visibileObjects.stream()
                                .filter(pu -> pu instanceof PowerUp)
                                .map(pu -> (PowerUp<?>) pu)
                                .map(this::mapPowerUp)
                                .collect(Collectors.toList());

                return new GameStateDto(mapPlayer(player), obstacles, powerUp, cameraX, player.getCoins(),
                                model.getStatus());

        }

        /**
         * Maps an {@link Obstacle} into its DTO representation.
         *
         * @param obs the obstacle to convert
         * @return the corresponding {@link ObstacleDto}
         */
        private ObstacleDto mapObstacle(final Obstacle obs) {
                return new ObstacleDto(
                                obs.getPosition().x(),
                                obs.getPosition().y(),
                                obs.getHitBox().getWidth(),
                                obs.getHitBox().getHeight(),
                                obs.isActive(),
                                DtoObstaclesType.valueOf(obs.getObstacleType().name()));

        }

        /**
         * Maps a {@link PowerUp} into its DTO representation.
         *
         * @param powerUp the power-up to convert
         * @return the corresponding {@link PowerUpDto}
         */
        private PowerUpDto mapPowerUp(final PowerUp<?> powerUp) {
                return new PowerUpDto(
                                powerUp.getPosition().x(),
                                powerUp.getPosition().y(),
                                powerUp.getHitBox().getWidth(),
                                powerUp.getHitBox().getHeight(),
                                powerUp.isActive(),
                                DtoPowerUpType.valueOf(powerUp.getPowerUpType().name()));
        }

        /**
         * Maps the {@link Player} into its DTO representation.
         *
         * @param player the player to convert
         * @return the corresponding {@link PlayerDto}
         */
        private PlayerDto mapPlayer(final Player<?> player) {
                return new PlayerDto(
                                player.getPosition().x(),
                                player.getPosition().y(),
                                player.getHitBox().getWidth(),
                                player.getHitBox().getHeight(),
                                player.isActive(),
                                player.isShielded(),
                                null,
                                player.getAngularRotation());
        }
}
