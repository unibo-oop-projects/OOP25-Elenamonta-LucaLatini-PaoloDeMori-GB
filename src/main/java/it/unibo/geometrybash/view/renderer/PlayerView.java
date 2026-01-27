package it.unibo.geometrybash.view.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

import it.unibo.geometrybash.commons.dtos.PlayerDto;
import it.unibo.geometrybash.view.core.Camera2D;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;

public class PlayerView implements Drawable<PlayerDto> {

    private final SpriteRegistry spriteRegistry;
    private float angleRad = 0f;

    public PlayerView(final SpriteRegistry spriteRegistry) {
        this.spriteRegistry = Objects.requireNonNull(spriteRegistry);
    }

    @Override
    public void draw(Graphics2D g2d, RenderContext renderContext, PlayerDto data) {
        final Camera2D camera = renderContext.camera();

        final BufferedImage outerImg = spriteRegistry.playerOuterBase(data.skin());
        final BufferedImage innerImg = spriteRegistry.playerInnerBase(data.skin());

        final int x = camera.xToPx(data.x());
        final int y = camera.yToPx(data.y() + data.height());
        final int width = camera.sizeToPx(data.width());
        final int height = camera.sizeToPx(data.height());

        // final float dt = .deltaTime();
        // angleRad += Math.toRadians(720) * dt;

        int centerX = x + width / 2;
        int centerY = y - height / 2;
    }
}
