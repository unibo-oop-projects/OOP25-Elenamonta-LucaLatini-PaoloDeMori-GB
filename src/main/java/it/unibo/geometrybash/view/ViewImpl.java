package it.unibo.geometrybash.view;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.dtos.GameStateDto;
import it.unibo.geometrybash.commons.pattern.observerpattern.AbstractObservableWithSet;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.view.core.Camera2D;
import it.unibo.geometrybash.view.core.RenderContext;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.gamepanel.GameFrame;
import it.unibo.geometrybash.view.gamepanel.GameFrameBuilder;
import it.unibo.geometrybash.view.gamepanel.PanelsFactory;
import it.unibo.geometrybash.view.gamepanel.PanelsFactoryImpl;
import it.unibo.geometrybash.view.menus.MainMenuView;
import it.unibo.geometrybash.view.utilities.GameResolution;
import it.unibo.geometrybash.view.utilities.TerminalColor;

/**
 * Implementation of the view of the game.
 */
public class ViewImpl extends AbstractObservableWithSet<ViewEvent> implements View {

    private static final String TITLE_GAME = "Geometry Bash - Playing";
    private final ResourceLoader resourceLoader;
    private final AssetStore assetStore;
    private final MainMenuView menuView;
    private final PanelsFactory panelsFactory;
    private GameFrame<GameStateDto> gameFrame;
    private RenderContext renderContext;

    /**
     * The constructor of the view of the game.
     *
     * @param resourceLoader the object used to retrieve resources
     * @param assetStore     the object that retrieves resources
     */
    public ViewImpl(final ResourceLoader resourceLoader, final AssetStore assetStore) {
        this.resourceLoader = resourceLoader;
        this.assetStore = assetStore;
        this.menuView = new MainMenuView(this.resourceLoader);
        this.panelsFactory = new PanelsFactoryImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final GameResolution resolution) {
        this.renderContext = new RenderContext(new Camera2D(resolution, 0), resolution.getViewPortWidth(),
                resolution.getViewPortHeight());
        this.gameFrame = new GameFrameBuilder()
                .setMainPanel(this.panelsFactory, this.renderContext, this.assetStore)
                .setBackGroundColor(TerminalColor.BACKGROUND)
                .setGameTitle(TITLE_GAME)
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() throws NotStartedViewException {
        this.menuView.display();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException {
        this.renderContext.setOffset(dto.getStateDto().cameraOffsetX());
        this.gameFrame.update(dto.getStateDto());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeView(final ViewScene scene) {
        switch (scene) {
            case ViewScene.START_MENU:
                if (this.gameFrame != null) {
                    this.gameFrame.setVisible(false);
                }
                this.menuView.display();
                break;
            case ViewScene.IN_GAME:
                this.menuView.hide();
                this.gameFrame.setVisible(true);
                break;
            case ViewScene.PAUSE:
                this.menuView.showPauseMessage();
                this.gameFrame.setVisible(false);
                this.menuView.display();
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disposeView() {
        this.gameFrame.dispose();
        this.menuView.hide();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void victory(final int coins, final int totalCoins) {
        this.menuView.showVictoryMessage(coins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {
        this.menuView.showPauseMessage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showResolutionOptions() {
        this.menuView.showManResolution();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showCommandsError(final String command) {
        this.menuView.showUnknownCommandError(command);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showExecutionError(final String executionError) {
        this.menuView.showGameExecutionError(executionError);
    }
}
