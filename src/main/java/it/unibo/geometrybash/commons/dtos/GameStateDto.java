package it.unibo.geometrybash.commons.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import it.unibo.geometrybash.model.Status;

/**
 * DTO containing all data needed to render a frame.
 *
 * @param player        the player data
 * @param obstacles     the list of obstacles
 * @param powerUps      the list of power-ups
 * @param cameraOffsetX the camera X offset
 * @param score         the current cumulated coins
 * @param gameStatus    the current game's status
 * @param progress      the level progres
 */
public record GameStateDto(
        PlayerDto player,
        List<ObstacleDto> obstacles,
        List<PowerUpDto> powerUps,
        float cameraOffsetX,
        int score,
        Status gameStatus,
        float progress) implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a new {@code GameStateDto} representing the current game state.
     *
     * @param player        the player data
     * @param obstacles     the list of obstacles
     * @param powerUps      the list of power-ups
     * @param cameraOffsetX the camera X offset
     * @param score         the current score
     * @param gameStatus    the game status
     * @param progress      the level progres
     */
    public GameStateDto {
        Objects.requireNonNull(player, "player");
        Objects.requireNonNull(obstacles, "obstacles");
        Objects.requireNonNull(powerUps, "powerUps");
        Objects.requireNonNull(gameStatus, "gameStatus");
        Objects.requireNonNull(progress, "progress");

        obstacles = List.copyOf(obstacles);
        powerUps = List.copyOf(powerUps);
    }
}
