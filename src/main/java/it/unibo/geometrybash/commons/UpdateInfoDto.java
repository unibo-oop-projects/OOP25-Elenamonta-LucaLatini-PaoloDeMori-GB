package it.unibo.geometrybash.commons;

import java.io.Serializable;

import it.unibo.geometrybash.commons.dtos.GameStateDto;

/**
 * The information used to update the view.
 *
 * <p>
 * This Data Transfer Object should be used by the
 * {@link it.unibo.geometrybash.controller.Controller} to send the view
 * the information to update after a cycle of gameloop.
 * Implements {@link Serializable} to ensure a correct communciation
 * </p>
 *
 * @see it.unibo.geometrybash.controller.Controller
 */
public final class UpdateInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The current game state to be sent to the view.
     */
    private final GameStateDto gameState;

    /**
     * Creates a new {@code UpdateInfoDto}.
     *
     * @param gameState the current game state to be sent to the view
     */
    public UpdateInfoDto(final GameStateDto gameState) {
        this.gameState = gameState;
    }

    /**
     * Returns the game state contained in this DTO.
     *
     * @return the current {@link GameStateDto}
     */
    public GameStateDto getStateDto() {
        return this.gameState;
    }
}
