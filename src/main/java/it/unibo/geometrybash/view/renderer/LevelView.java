package it.unibo.geometrybash.view.renderer;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;

/**
 * The class responsible to draw effectively the entities in the level.
 * 
 * @see SpriteRegistry
 * @see PlayerView
 * @see ObstacleView
 * @see PowerUpView
 * @see RenderContext
 * @see GameStateDto
 */
public class LevelView implements Drawable<GameStateDto> {

    /**
     * Logger for warnings,infos and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LevelView.class);

    private final ObstacleView obstacleView;
    private final PowerUpView powerUpView;
    private final PlayerView playerView;

    private final SpriteRegistry spriteRegistry;

    /**
     * The constructor of this level.
     * 
     * @param spriteRegistry the cache containing the loaded sprites.
     */
    public LevelView(final SpriteRegistry spriteRegistry) {
        this.spriteRegistry = spriteRegistry;
        this.obstacleView = new ObstacleView(this.spriteRegistry);
        this.powerUpView = new PowerUpView(this.spriteRegistry);
        this.playerView = new PlayerView(this.spriteRegistry);
    }

    /**
     * The class responsible of drawing the entities of the level.
     * 
     * {@inheritDoc}
     */
    @Override
    public void draw(final Graphics2D g2d, final RenderContext renderContext, final GameStateDto data) {
        g2d.clearRect(0, 0, renderContext.viewportWidth(), renderContext.viewportHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        if (data != null) {
            this.obstacleView.draw(g2d, renderContext, data.obstacles());
            this.powerUpView.draw(g2d, renderContext, data.powerUps());
            this.playerView.draw(g2d, renderContext, data.player());
        } else {
            LOGGER.warn("Level drawn without any information about the world");
        }
    }

}
