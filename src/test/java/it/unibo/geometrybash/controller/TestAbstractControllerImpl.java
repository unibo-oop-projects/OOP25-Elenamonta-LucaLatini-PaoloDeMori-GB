package it.unibo.geometrybash.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
import it.unibo.geometrybash.model.MenuModel;
import it.unibo.geometrybash.model.Status;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.physicsengine.impl.jbox2d.JBox2DPhysicsEngineFactory;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewImpl;
import it.unibo.geometrybash.view.ViewScene;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * Class to test the AbstractControllerImpl.
 */
class TestAbstractControllerImpl {

    /**
     * A mock delta time.
     */
    private static final float DELTA = 1 / 60f;

    private final ResourceLoader rL = new ResourceLoaderImpl();

    private final View view = new ViewImpl(rL, new AssetStore(rL));

    private final Object obj = new Object();

    private volatile boolean isNotified;

    /**
     * Tests the StaticDeltaTimeController EvaluateDeltaTime Implementation.
     */
    @Test
    void evaluateDeltaTimeTest() {

        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                view, rL);
        assertEquals(DELTA, abs.evaluateDeltaTime());
    }

    /**
     * Tests the isTheViewSet and isTheModelTest methods.
     */
    @Test
    void controllerInterfacesTest() {
        final GameModelImpl gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final AbstractControllerImpl abs = new StaticDeltaTimeControllerImpl(gM,
                new ViewImpl(rL, new AssetStore(rL)), rL);
        assertEquals(Status.NEVERSTARTED, abs.getModelStatus());
        assertTrue(abs.isTheViewSet());
        assertTrue(abs.isTheModelSet());
    }

    /**
     * Tests the public start methods.
     */
    @Test
    void startTest() {
        final ViewMock viewMock = new ViewMock();
        final MockAudioScheduler cAS = new MockAudioScheduler();
        final GameModel gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final MockInputHandlerFactory inputHandlerFactory = new MockInputHandlerFactory();

        final AbstractControllerImpl controller = new MockAbstractController(gM, viewMock,
                new GameLoopFixedExecutionTimeFactory(), inputHandlerFactory, cAS);

        controller.start();
        // checks if the audioscheduler started
        assertTrue(cAS.isStart);
        // checks if the show method of the view is called.
        assertTrue(viewMock.isShow);
        // checks if the inputhandler was correctly init.
        assertTrue(inputHandlerFactory.isAllTrue());
    }

    /**
     * Private method to notify the threads waiting on the obj lock.
     */
    private void notifyObj() {
        synchronized (obj) {
            this.isNotified = true;
            obj.notifyAll();
        }
    }

    /**
     * Method to test the update method.
     * 
     */
    @Test
    void updateTest() {
        final ViewMock viewMock = new ViewMock(this::notifyObj);
        final MockAudioScheduler cAS = new MockAudioScheduler();
        final GameModel gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final MockInputHandlerFactory inputHandlerFactory = new MockInputHandlerFactory();
        final long maxWaitingTime = 3000L;

        final AbstractControllerImpl controller = new MockAbstractController(gM, viewMock,
                new GameLoopFixedExecutionTimeFactory(), inputHandlerFactory, cAS);
        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (final InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail("An error occured while starting the gamemodel");
        }
        gM.update(DELTA);
        controller.start();
        synchronized (obj) {
            controller.update(ModelEvent.createVictoryEvent());
            try {
                while (!isNotified) {
                    obj.wait(maxWaitingTime);
                }
            } catch (final InterruptedException e) {
                fail("An error occured while waiting for the lock to be notified");
            }
            assertTrue(cAS.isFromGameToMenu);
            assertTrue(viewMock.isVictory());
            assertTrue(viewMock.isViewChanged);
        }

    }

    @Test
    void updateGameOverTest() {
        final ViewMock viewMock = new ViewMock();
        final MockAudioScheduler cAS = new MockAudioScheduler(this::notifyObj);
        final GameModel gM = new GameModelImpl(rL, new JBox2DPhysicsEngineFactory());
        final MockInputHandlerFactory inputHandlerFactory = new MockInputHandlerFactory();
        final long maxWaitingTime = 3000L;

        final AbstractControllerImpl controller = new MockAbstractController(gM, viewMock,
                new GameLoopFixedExecutionTimeFactory(), inputHandlerFactory, cAS);
        try {
            gM.start(MenuModel.LEVELS_NAME_LIST.get(0));
        } catch (final InvalidModelMethodInvocationException | ModelExecutionException e) {
            fail("An error occured while starting the gamemodel");
        }
        gM.update(DELTA);
        controller.start();
        synchronized (obj) {
            controller.update(ModelEvent.createGameOverEvent());
            try {
                while (!isNotified) {
                    obj.wait(maxWaitingTime);
                }
            } catch (final InterruptedException e) {
                fail("An error occured while waiting for the lock to be notified");
            }
            assertTrue(cAS.isRestart);
        }

    }

    /**
     * MockClass for the inputhandler.
     */
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

    /**
     * Mock Class representing an AbstractControllerImpl implementation.
     */
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

    /**
     * Mock Class representing an AudioScheduler implementation.
     */
    class MockAudioScheduler implements ControllerAudioScheduler {

        private boolean isStart;

        private boolean isFromMenuToGame;

        private boolean isFromGameToMenu;

        private boolean isRestart;

        private Runnable toWait;

        MockAudioScheduler() {
            // default constructor.
        }

        MockAudioScheduler(final Runnable r) {
            this.toWait = r;
        }

        public boolean isRestart() {
            return isRestart;
        }

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
            if (this.toWait != null) {
                this.toWait.run();
            }
            this.isRestart = true;
        }
    }

    /**
     * Mock class representing a View implementation.
     */
    class ViewMock extends AbstractObservableWithSet<ViewEvent> implements View {
        private boolean isShow;

        private boolean isVictory;

        private boolean isViewChanged;

        private Runnable toWait;

        ViewMock() {
            // default constructor.
        }

        ViewMock(final Runnable r) {
            this.toWait = r;
        }

        /**
         * Method that returns true if the victory method was called.
         * 
         * @return true if the victory method was called.
         */
        boolean isVictory() {
            return isVictory;
        }

        /**
         * Method that returns true if the show method is called.
         * 
         * @return true if the show method was called
         */
        boolean isShow() {
            return isShow;
        }

        /**
         * Method that returns true if the change view method was called with the
         * correct configuration for the test.
         * 
         * @return true if the changeView method was called
         */
        boolean isViewChanged() {
            return this.isViewChanged;
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
        public void update(final UpdateInfoDto dto)
                throws NotStartedViewException, ExecutionWithIllegalThreadException {
            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }

        @Override
        public void changeView(final ViewScene scene) {
            if (scene.equals(ViewScene.START_MENU)) {
                this.isViewChanged = true;
            }
            /*
             * tries to call the toWait runnable. This stategy is set by the test to notify
             * a lock, to avoid concurrency problems
             */
            if (this.toWait != null) {
                this.toWait.run();
            }
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
