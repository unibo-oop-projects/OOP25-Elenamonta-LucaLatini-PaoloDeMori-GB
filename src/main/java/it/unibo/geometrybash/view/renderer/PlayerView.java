package it.unibo.geometrybash.view.renderer;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.geometrybash.commons.dtos.PlayerDto;
import it.unibo.geometrybash.view.core.Camera2D;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;

/**
 * Handles the rendering off the player.
 */
public class PlayerView implements Drawable<PlayerDto> {

    private final SpriteRegistry spriteRegistry;

    /**
     * Creates a new {@code PlayerView}.
     *
     * @param spriteRegistry the registry to retrieve obstacle sprites.
     */
    public PlayerView(final SpriteRegistry spriteRegistry) {
        this.spriteRegistry = Objects.requireNonNull(spriteRegistry);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g2d, final RenderContext renderContext, final PlayerDto data) {
        final Camera2D camera = renderContext.camera();

        final BufferedImage outerImg = spriteRegistry.playerOuterBase(data.skin());
        final BufferedImage innerImg = spriteRegistry.playerInnerBase(data.skin());

        final int x = camera.xToPx(data.x());
        final int y = camera.yToPx(data.y() + data.height());
        final int width = camera.sizeToPx(data.width());
        final int height = camera.sizeToPx(data.height());

        final int centerX = x + width / 2;
        final int centerY = y + height / 2;

        final AffineTransform oldTx = g2d.getTransform();

        try {
            g2d.rotate(data.rotationRad(), centerX, centerY);

            g2d.drawImage(innerImg, x, y, width, height, null);
            g2d.drawImage(outerImg, x, y, width, height, null);
        } finally {
            g2d.setTransform(oldTx);
        }
    }
}
