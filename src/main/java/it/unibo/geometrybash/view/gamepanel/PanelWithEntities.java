package it.unibo.geometrybash.view.gamepanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.view.UpdatableWithDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.core.SpriteRegistry;
import it.unibo.geometrybash.view.renderer.Drawable;
import it.unibo.geometrybash.view.renderer.LevelView;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * A panel that draws the entities in the level.
 */
@SuppressFBWarnings(
    value = "Se",
    justification = "This class is not serializable"
)
public class PanelWithEntities extends JPanel implements UpdatableWithDto<GameStateDto> {

    private static final long serialVersionUID = 1L;

    /**
     * Logger for warnings,infos and errors.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PanelWithEntities.class);

    /**
     * The instructions to draw correctly the entities.
     */
    private RenderContext renderContext;

    /**
     * The last infos about the level received.
     */
    private GameStateDto gameStateDto;
    /**
     * The object that effectively draws the entities.
     */
    private final Drawable<GameStateDto> levelView;

    /**
     * The container of entities' sprites.
     */
    private final SpriteRegistry spriteRegistry;

    /**
     * The constructor of this class.
     * 
     * @param renderContext The instructions to draw correctly the entities.
     * @param assetStore    The object responsible of loading the resources.
     */
    public PanelWithEntities(final RenderContext renderContext, final AssetStore assetStore) {
        this.spriteRegistry = new SpriteRegistry(assetStore);
        this.renderContext = renderContext;
        this.levelView = new LevelView(spriteRegistry);
    }

    /**
     * The method responsible of drawing the entities, this method calls the draw
     * method of the {@link LevelView}.
     * 
     * <p>
     * If the level state isn't set it will not interrupt the execution but this
     * information will be logged.
     * </p>
     */
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D graphic = (Graphics2D) g;
        graphic.setBackground(TerminalColor.BACKGROUND);
        if (this.gameStateDto != null) {
            this.levelView.draw(graphic, renderContext, gameStateDto);
        } else {
            LOGGER.warn("Panel with entities drawn without any information about world entities");
        }
    }

    /**
     * Sets a new render context if it changed.
     * 
     * @param renderContext the new render context.
     */
    public void setRenderContext(final RenderContext renderContext) {
        this.renderContext = renderContext;
    }

    /**
     * Update sthe gameState with new infos.
     */
    @Override
    public void update(final GameStateDto newGameStateDto) {
        this.gameStateDto = newGameStateDto;
        this.repaint();
    }

}
