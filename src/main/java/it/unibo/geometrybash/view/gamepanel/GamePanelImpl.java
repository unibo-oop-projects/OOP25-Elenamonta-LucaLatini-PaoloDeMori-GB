package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.AbstractObservableWithSet;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.view.UpdatableWithDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.userinteraction.SwingKeyboardListener;
import it.unibo.geometrybash.view.userinteraction.SwingStrategyWithObservable;
import it.unibo.geometrybash.view.utilities.GameResolution;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * Implemantation of the {@link GamePanel} interface, using {@link GameFrame}
 * as game window and {@link PanelsFactory} to create the main panel.
 * 
 * @see GameFrameBuilder
 */
public class GamePanelImpl extends AbstractObservableWithSet<ViewEvent>
        implements UpdatableWithDto<GameStateDto>, GamePanel {

    private GameFrame<GameStateDto> gameFrame;
    private final PanelsFactory panelsFactory;
    private final SwingKeyboardListener swingKeyboardListener = new SwingKeyboardListener(
            new SwingStrategyWithObservable(
                    this::notifyObservers));

    /**
     * The constructor of this class that accepts a {@link PanelsFactory}
     * to create the game main panel.
     * 
     * @param panelsFactory the factory to create the main game panel.
     */
    public GamePanelImpl(final PanelsFactory panelsFactory) {
        this.panelsFactory = panelsFactory;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final RenderContext renderContext, final AssetStore assetStore, final String gameTitle,
            final GameResolution gameResolution) {
        this.gameFrame = new GameFrameBuilder()
                .setMainPanel(this.panelsFactory, renderContext, assetStore)
                .setBackGroundColor(TerminalColor.BACKGROUND)
                .setGameTitle(gameTitle)
                .setResolution(gameResolution)
                .build();
        this.gameFrame.addKeyListener(
                this.swingKeyboardListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {
        if (this.gameFrame != null) {
            this.gameFrame.setVisible(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        if (this.gameFrame != null) {
            this.gameFrame.setVisible(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameStateDto gameStateDto) {
        this.gameFrame.update(gameStateDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        if (this.gameFrame != null) {
            this.gameFrame.dispose();
        }
    }

}
