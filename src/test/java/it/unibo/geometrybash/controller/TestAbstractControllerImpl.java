package it.unibo.geometrybash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.assets.AssetStore;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.assets.ResourceLoaderImpl;
import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.AbstractObservableWithSet;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.viewobserverpattern.ViewEvent;
import it.unibo.geometrybash.controller.gameloop.GameLoopFactory;
import it.unibo.geometrybash.controller.gameloop.GameLoopFixedExecutionTimeFactory;
import it.unibo.geometrybash.controller.input.InputHandlerFactory;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.model.GameModelImpl;
import it.unibo.geometrybash.model.Status;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2DPhysicsEngineFactory;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewImpl;
import it.unibo.geometrybash.view.ViewScene;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.utilities.GameResolution;

class TestAbstractControllerImpl {

    private final ResourceLoader rL = new ResourceLoaderImpl();

    private final View view = new ViewImpl(rL, new AssetStore(rL));

    @Test
    void evaluateDeltaTimeTest() {
        final float delta = 1 / 60f;

        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                view, rL);
        assertEquals(delta, abs.evaluateDeltaTime());
    }

    @Test
    void controllerInterfacesTest() {
        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                new ViewImpl(rL, new AssetStore(rL)), rL);
        assertEquals(Status.NEVERSTARTED, abs.getModelStatus());
        assertTrue(abs.isTheViewSet());
        assertTrue(abs.isTheModelSet());
    }

    @Test
    void startTest() {
        final ViewMock viewMock = new ViewMock();
        final MockAudioScheduler cAS = new MockAudioScheduler();
        final GameModel gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final MockInputHandlerFactory inputHandlerFactory = new MockInputHandlerFactory();

        final AbstractControllerImpl controller = new MockAbstractController(gM, viewMock,
                new GameLoopFixedExecutionTimeFactory(), inputHandlerFactory, cAS);

        controller.start();
        assertTrue(cAS.isStart);
        assertTrue(viewMock.isShow);
        assertTrue(inputHandlerFactory.isAllTrue());
    }

    @Test
    void updateTest() {
        final ViewMock viewMock = new ViewMock();
        final MockAudioScheduler cAS = new MockAudioScheduler();
        final GameModel gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final MockInputHandlerFactory inputHandlerFactory = new MockInputHandlerFactory();

        final AbstractControllerImpl controller = new MockAbstractController(gM, viewMock,
                new GameLoopFixedExecutionTimeFactory(), inputHandlerFactory, cAS);
        controller.start();
        controller.update(ModelEvent.createVictoryEvent());
        assertTrue(cAS.isFromGameToMenu);
    }

    class MockInputHandlerFactory implements InputHandlerFactory {
        private boolean isSetOnMainKeyPressed;
        private boolean isSetOnMenuKeyPressed;
        private boolean isSetOnResumeKeyPressed;
        private boolean isSetActionForEvent;
        private boolean issetGenericCommandHandler;

        boolean isAllTrue() {
            return this.isSetOnMainKeyPressed
                    && this.isSetOnMenuKeyPressed
                    && this.isSetOnResumeKeyPressed
                    && this.isSetActionForEvent
                    && this.issetGenericCommandHandler;
        }

        @Override
        public InputHandler createInputHandler() {
            return new InputHandler() {
                private final List<StandardViewEventType> setStandard = new ArrayList<>();

                @Override
                public void update(final ViewEvent event) {
                }

                @Override
                public void setOnMainKeyPressed(final OnInputEventAction action) {
                    isSetOnMainKeyPressed = true;
                }

                @Override
                public void setOnMenuKeyPressed(final OnInputEventAction action) {
                    isSetOnMenuKeyPressed = true;
                }

                @Override
                public void setOnResumeKeyPressed(final OnInputEventAction action) {
                    isSetOnResumeKeyPressed = true;
                }

                @Override
                public void setActionForEvent(final StandardViewEventType type, final OnInputEventAction action) {
                    setStandard.add(type);
                    if (setStandard.containsAll(List.of(StandardViewEventType.START, StandardViewEventType.CLOSE,
                            StandardViewEventType.RESTART))) {
                        isSetActionForEvent = true;
                    }
                }

                @Override
                public void setGenericCommandHandler(final OnGenericCommandAction handler) {
                    issetGenericCommandHandler = true;
                }

            };
        }

    }

    class MockAbstractController extends AbstractControllerImpl {

        private static final float DELTA = 1 / 60f;

        MockAbstractController(final GameModel gameModel, final View view, final GameLoopFactory gameLoopFactory,
                final InputHandlerFactory inputHandlerFactory,
                final ControllerAudioScheduler controllerAudioScheduler) {
            super(gameModel, view, gameLoopFactory, inputHandlerFactory, controllerAudioScheduler);
        }

        @Override
        protected float evaluateDeltaTime() {
            return DELTA;
        }

    }

    class MockAudioScheduler implements ControllerAudioScheduler {

        private boolean isStart;

        private boolean isFromMenuToGame;

        private boolean isFromGameToMenu;

        public boolean isFromMenuToGame() {
            return isFromMenuToGame;
        }

        public boolean isStart() {
            return isStart;
        }

        @Override
        public void fromMenuToGame() throws ImpossibleToReproduceMusicException {
            this.isFromMenuToGame = true;
        }

        @Override
        public void fromGameToMenu() throws ImpossibleToReproduceMusicException {
            this.isFromGameToMenu = true;

        }

        @Override
        public void firstStart() throws ImpossibleToReproduceMusicException {
            this.isStart = true;
        }

        @Override
        public void restartLevelMusic() throws ImpossibleToReproduceMusicException {
            throw new UnsupportedOperationException("Unimplemented method 'restartLevelMusic'");
        }
    }

    class ViewMock extends AbstractObservableWithSet<ViewEvent> implements View {
        private boolean isShow;

        private boolean isVictory;

        boolean isVictory() {
            return isVictory;
        }

        boolean isShow() {
            return isShow;
        }

        @Override
        public void init(final GameResolution resolution) {
            throw new UnsupportedOperationException("Unimplemented method 'init'");
        }

        @Override
        public void show() throws NotStartedViewException {
            this.isShow = true;
        }

        @Override
        public void update(final UpdateInfoDto dto) throws NotStartedViewException, ExecutionWithIllegalThreadException {
            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }

        @Override
        public void changeView(final ViewScene scene) {
            throw new UnsupportedOperationException("Unimplemented method 'changeView'");
        }

        @Override
        public void disposeView() {
            throw new UnsupportedOperationException("Unimplemented method 'disposeView'");
        }

        @Override
        public void victory(final int coins, final int totalCoins) {
            this.isVictory = true;
        }

        @Override
        public void pause() {
            throw new UnsupportedOperationException("Unimplemented method 'pause'");
        }

        @Override
        public void showCommandsError(final String command) {
            throw new UnsupportedOperationException("Unimplemented method 'showCommandsError'");
        }

        @Override
        public void showResolutionOptions() {
            throw new UnsupportedOperationException("Unimplemented method 'showResolutionOptions'");
        }

        @Override
        public void showExecutionError(final String executionError) {
            throw new UnsupportedOperationException("Unimplemented method 'showExecutionError'");
        }

        @Override
        public void showLevels(final List<String> levels) {
            throw new UnsupportedOperationException("Unimplemented method 'showLevels'");
        }

        @Override
        public void showColors(final Map<String, Integer> colors) {
            throw new UnsupportedOperationException("Unimplemented method 'showColors'");
        }

        @Override
        public void appendText(final String text) {
            throw new UnsupportedOperationException("Unimplemented method 'appendText'");
        }
    }
}
