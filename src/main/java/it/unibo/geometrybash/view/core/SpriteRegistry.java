package it.unibo.geometrybash.view.core;

import java.awt.Color;
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

    private static final int ALL_BITS_MASK = 0xFF;
    private static final int MAX_CHANNEL_VALUE = 255;
    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
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
     * @throws NullPointerException if {@code skin} or {@code skin.outerSprite()} is
     *                              {@code null}
     */
    public BufferedImage playerOuterBase(final SkinDto skin) {
        Objects.requireNonNull(skin, "skin must not be null");
        Objects.requireNonNull(skin.outerSprite(), "skin.outerSprite must not be null");
        return tint(assets.getImage(skin.outerSprite()), new Color(skin.primaryColor()));
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
        Objects.requireNonNull(skin.innerSprite(), "ski n.innerSprite must not be null");
        return tint(assets.getImage(skin.innerSprite()), new Color(skin.secondaryColor()));
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

    private static BufferedImage tint(final BufferedImage src, final Color tint) {
        final int w = src.getWidth();
        final int h = src.getHeight();
        final BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        final int tr = tint.getRed();
        final int tg = tint.getGreen();
        final int tb = tint.getBlue();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                final int argb = src.getRGB(x, y);
                final int a = (argb >>> ALPHA_SHIFT) & ALL_BITS_MASK;

                if (a == 0) {
                    out.setRGB(x, y, 0);
                    continue;
                }

                int r = (argb >>> RED_SHIFT) & ALL_BITS_MASK;
                int g = (argb >>> GREEN_SHIFT) & ALL_BITS_MASK;
                int b = argb & ALL_BITS_MASK;

                r = r * tr / MAX_CHANNEL_VALUE;
                g = g * tg / MAX_CHANNEL_VALUE;
                b = b * tb / MAX_CHANNEL_VALUE;

                out.setRGB(x, y, (a << ALPHA_SHIFT) | (r << RED_SHIFT) | (g << GREEN_SHIFT) | b);
            }
        }
        return out;
    }
}
