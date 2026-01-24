package it.unibo.geometrybash.view.core;

import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.DtoObstaclesType;
import it.unibo.geometrybash.commons.dtos.DtoPowerUpType;
import it.unibo.geometrybash.commons.dtos.SkinDto;

/**
 * Central registry for all sprites used by the view layer.
 */
public final class SpriteRegistry {

    private final AssetStore assets;

    /**
     * Creates a new {@code SpriteRegistry}.
     *
     * @param assets the asset store used to load and cache images
     */
    public SpriteRegistry(final AssetStore assets) {
        this.assets = Objects.requireNonNull(assets, "assets must not be null");
    }

    /**
     * Returns the sprite associated with the given obstacle type.
     *
     * @param type the obstacle type whose sprite is requested
     * @return the {@link BufferedImage} representing the obstacle
     * @throws NullPointerException if {@code type} is {@code null}
     */
    public BufferedImage obstacleSprite(final DtoObstaclesType type) {
        Objects.requireNonNull(type, "obstacle type must not be null");
        return assets.getImage(obstaclePath(type));
    }

    /**
     * Returns the sprite associated with the given power up type.
     *
     * @param type the power up type whose sprite is requested
     * @return the {@link BufferedImage} representing the power up
     * @throws NullPointerException if {@code type} is {@code null}
     */
    public BufferedImage powerUpSprite(final DtoPowerUpType type) {
        Objects.requireNonNull(type, "powerup type must not be null");
        return assets.getImage(powerUpPath(type));
    }

    /**
     * Loads outer sprite for the player skin.
     *
     * @param skin the skin descriptor containing the resource identifier for the
     *             outer sprite
     * @return the base {@link BufferedImage} for the player's outer sprite
     * @throws NullPointerException if {@code skin} or {@code skin.outerSprite()} is {@code null}
     */
    public BufferedImage playerOuterBase(final SkinDto skin) {
        Objects.requireNonNull(skin, "skin must not be null");
        Objects.requireNonNull(skin.outerSprite(), "skin.outerSprite must not be null");
        return assets.getImage(skin.outerSprite());
    }

    /**
     * Loads inner sprite for the player skin.
     *
     * @param skin the skin descriptor containing the resource identifier for the
     *             inner sprite
     * @return the base {@link BufferedImage} for the player's inner sprite
     * @throws NullPointerException if {@code skin} or {@code skin.innerSprite()} is
     *                              {@code null}
     */
    public BufferedImage playerInnerBase(final SkinDto skin) {
        Objects.requireNonNull(skin, "skin must not be null");
        Objects.requireNonNull(skin.innerSprite(), "skin.innerSprite must not be null");
        return assets.getImage(skin.innerSprite());
    }

    private String obstaclePath(final DtoObstaclesType type) {
        return switch (type) {
            case SPIKE -> "images/obstacles/spike.png";
            case BLOCK -> "images/obstacles/block.png";
        };
    }

    private String powerUpPath(final DtoPowerUpType type) {
        return switch (type) {
            case COIN -> "images/powerups/coin.png";
            case SPEED_BOOST -> "images/powerups/speed.png";
            case SHIELD -> "images/powerups/shield.png";
        };
    }
}
