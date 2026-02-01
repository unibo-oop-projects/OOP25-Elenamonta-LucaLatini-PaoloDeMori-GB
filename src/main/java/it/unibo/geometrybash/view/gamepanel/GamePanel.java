package it.unibo.geometrybash.view.gamepanel;

import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.AbstractObservableWithSet;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.view.UpdatableWithDto;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.userinteraction.SwingKeyboardListener;
import it.unibo.geometrybash.view.userinteraction.SwingStrategyWithObservable;
import it.unibo.geometrybash.view.utilities.TerminalColor;

public class GamePanel extends AbstractObservableWithSet<ViewEvent> implements UpdatableWithDto<GameStateDto> {

    private GameFrame<GameStateDto> gameFrame;
    private PanelsFactory panelsFactory;
    private SwingKeyboardListener swingKeyboardListener = new SwingKeyboardListener(new SwingStrategyWithObservable(
            e -> notifyObservers(e)));

    public GamePanel(PanelsFactory panelsFactory) {
        this.panelsFactory = panelsFactory;

    }

    public void init(RenderContext renderContext, AssetStore assetStore, String gameTitle) {
        this.gameFrame = new GameFrameBuilder()
                .setMainPanel(this.panelsFactory, renderContext, assetStore)
                .setBackGroundColor(TerminalColor.BACKGROUND)
                .setGameTitle(gameTitle)
                .build();
        this.gameFrame.addKeyListener(
                this.swingKeyboardListener);
    }

    public void show() {
        if (this.gameFrame != null) {
            this.gameFrame.setVisible(true);
        }
    }

    public void hide() {
        if (this.gameFrame != null) {
            this.gameFrame.setVisible(false);
        }
    }

    @Override
    public void update(GameStateDto gameStateDto) {
        this.gameFrame.update(gameStateDto);
    }

    public void dispose() {
        if (this.gameFrame != null) {
            this.gameFrame.dispose();
        }
    }

}
