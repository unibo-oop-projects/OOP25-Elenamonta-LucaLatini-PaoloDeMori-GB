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

public class ViewImpl extends AbstractObservableWithSet<ViewEvent> implements View {

    private static final String TITLE_GAME = "Geometry Bash - Playing";
    private final ResourceLoader resourceLoader;
    private final AssetStore assetStore;
    private final Camera2D camera;
    private RenderContext renderContext;
    private MainMenuView menuView;
    private PanelsFactory panelsFactory;
    private GameFrame<GameStateDto> gameFrame;

    public ViewImpl(final ResourceLoader resourceLoader, final AssetStore assetStore, final Camera2D camera) {
        this.resourceLoader = resourceLoader;
        this.assetStore = assetStore;
        this.camera = camera;
        this.menuView = new MainMenuView(this.resourceLoader);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final GameResolution resolution) {
        this.renderContext = new RenderContext(camera, resolution.getViewPortWidth(), resolution.getViewPortHeight());
        this.panelsFactory = new PanelsFactoryImpl();
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
    public void update(UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException {
        this.gameFrame.update(dto.getStateDto());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeView(ViewScene scene) {
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
            case ViewScene.PAUSE:
                this.menuView.showPauseMessage();
                this.gameFrame.setVisible(false);
                this.menuView.display();
            default:
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

}
