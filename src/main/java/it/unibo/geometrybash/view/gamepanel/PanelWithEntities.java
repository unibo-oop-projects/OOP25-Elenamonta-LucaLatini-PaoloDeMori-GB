package it.unibo.geometrybash.view.gamepanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.UpdatableWithDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;
import it.unibo.geometrybash.view.renderer.Drawable;
import it.unibo.geometrybash.view.renderer.LevelView;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * A panel that draws the entities in the level,
 */
public class PanelWithEntities extends JPanel implements UpdatableWithDto<GameStateDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanelWithEntities.class);

    private RenderContext renderContext;

    private GameStateDto gameStateDto;
    private Drawable<GameStateDto> levelView;

    private final SpriteRegistry spriteRegistry;

    public PanelWithEntities(RenderContext renderContext, AssetStore assetStore) {
        this.spriteRegistry = new SpriteRegistry(assetStore);
        this.renderContext = renderContext;
        this.levelView = new LevelView(spriteRegistry);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphic = (Graphics2D) g;
        graphic.setBackground(TerminalColor.BACKGROUND);
        if (this.gameStateDto != null) {
            this.levelView.draw((Graphics2D) graphic, renderContext, gameStateDto);
        } else {
            LOGGER.warn("Panel with entities drawn without any information about world entities");
        }
    }

    public void setRenderContext(RenderContext r) {
        this.renderContext = r;
    }

    @Override
    public void update(GameStateDto gameStateDto) {
        this.gameStateDto = gameStateDto;
        this.repaint();
    }

}
