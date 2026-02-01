package it.unibo.geometrybash.controller;

import java.util.Optional;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.controller.gameloop.GameLoop;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopConfigurationException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotOnPauseException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotStartedException;
import it.unibo.geometrybash.controller.input.InputHandlerFactory;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewScene;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.utilities.GameResolution;
import it.unibo.geometrybash.controller.gameloop.GameLoopFactory;

/**
 * Abstract Implementation of the controller interface with an undefined method
 * to evaluate deltatime.
 */
public abstract class AbstractControllerImpl implements Controller {
    /**
     * Logger instance to handle errors and sending debug information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractControllerImpl.class);
    private static final String LEVEL_NAME = "tempLevel";

    private final GameModel gameModel;
    private final View view;
    private final InputHandler inputHandler;
    private GameLoop gameLoop;
    private final GameLoopFactory gameLoopFactory;
    private final GameResolution gameResolution = GameResolution.MEDIUM;

    /**
     * The constructor of the controller with game model, view and input handler
     * creation delegated.
     *
     * @param gameModel           the model of the game
     * @param view                the main view class of the game
     * @param gameLoopFactory     the factory to init the gameloop.
     * @param inputHandlerFactory the factory to init the inputHandler.
     *
     */
    public AbstractControllerImpl(final GameModel gameModel, final View view, final GameLoopFactory gameLoopFactory,
            final InputHandlerFactory inputHandlerFactory) {
        this.gameModel = gameModel;
        this.gameModel.addObserver(this);
        this.view = view;
        this.inputHandler = inputHandlerFactory.createInputHandler();
        this.view.addObserver(inputHandler);
        this.gameLoopFactory = gameLoopFactory;
    }

    /**
     * A method that returns the delta time, it can either be static or evluated.
     *
     * @return the delta time.
     */
    protected abstract float evaluateDeltaTime();

    /**
     * Sets the action to execute on input messages receiver.
     */
    private void initInputHandler() {
        this.inputHandler.setOnMainKeyPressed(this::onJumpAction);
        this.inputHandler.setOnMenuKeyPressed(this::gamePause);
        this.inputHandler.setActionForEvent(StandardViewEventType.START, this::startLevel);
        this.inputHandler.setActionForEvent(StandardViewEventType.RESTART, this::gameRestart);
        this.inputHandler.setActionForEvent(StandardViewEventType.RESUME, this::gameResume);
        this.inputHandler.setActionForEvent(StandardViewEventType.CLOSE, this::onClose);
        this.inputHandler.setGenericCommandHandler(this::onGenericCommand);
    }

    /**
     * Checks if the gameloop exists if it doesn't this function creates it.
     */
    private void gameLoopSetting() {
        if (this.gameLoop == null) {
            this.gameLoop = gameLoopFactory.createGameLoop(this::onEveryFrame);
        }
    }

    /**
     * The action to execute on.
     */
    private void onJumpAction() {
        this.gameModel.jumpSignal();
    }

    private void onClose() {
        safeClosing();
    }

    /**
     * The actions to execute if a generic command represented as a string is
     * received.
     *
     * @param command the string received.
     */
    private void onGenericCommand(final String command) {
        switch (command) {
            case "resolution -big":
                // TODO
                break;
            default:
                break;
        }

    }

    /**
     * The action to execute on every frame refresh.
     */
    private void onEveryFrame() {
        this.gameModel.update(evaluateDeltaTime());
        final UpdateInfoDto dto;
        try {
            dto = gameModel.toDto();
            SwingUtilities.invokeLater(() -> {
                if (dto != null) {
                    callViewUpdate(dto);
                }
            });

        } catch (final ModelExecutionException e) {
            handleError("Error while updating the game", Optional.of(e));
        }

    }

    /**
     * A utility function to handle a critic error.
     *
     * @param message the message to log and to display
     * @param ex      the exception that caused this method execution
     */
    private void handleError(final String message, final Optional<Exception> ex) {

        try {
            gameLoop.stop();
        } catch (final NotStartedException e) {
            LOGGER.info("The safe thread interruption wasn't necessary");
        } finally {
            errorMessage(message, ex);
        }

    }

    /**
     * A method to update the view and handle its exceptions.
     *
     * @param dto the dto used by the view to its update
     */
    private void callViewUpdate(final UpdateInfoDto dto) {
        try {
            this.view.update(dto);
        } catch (final NotStartedViewException | ExecutionWithIllegalThreadException e) {
            handleError("Error while updating the view", Optional.of(e));
        }
    }

    /**
     * The method to show a gui with the error and log it.
     *
     * @param message the message to show in the gui and log.
     * @param e       the exception that caused this one.
     */
    private void errorMessage(final String message, final Optional<Exception> e) {
        view.showCommandsError(message);
        if (e.isPresent()) {
            LOGGER.error(message, e);
        } else {
            LOGGER.error(message);
        }
    }

    /**
     * A utility method to resume the game.
     */
    private void gameResume() {
        try {
            gameModel.resume();
            gameLoopSetting();
            gameLoop.resume();
            view.changeView(ViewScene.IN_GAME);
        } catch (final NotOnPauseException | NotStartedException | InvalidModelMethodInvocationException e) {
            handleError("Error while resuming the game", Optional.of(e));
        }
    }

    /**
     * A utility method to pause the game.
     */
    private void gamePause() {
        try {
            gameLoopSetting();
            gameLoop.pause();
            gameModel.pause();
            view.changeView(ViewScene.PAUSE);
        } catch (final InvalidGameLoopStatusException | InvalidModelMethodInvocationException e) {
            handleError("Error while resuming the thread", Optional.of(e));
        }
    }

    /**
     * A utility method to restart the game.
     */
    private void gameRestart() {
        try {
            gameLoopSetting();
            this.gameModel.restart();
            this.gameLoop.resume();
            this.view.changeView(ViewScene.IN_GAME);
        } catch (final InvalidGameLoopStatusException
                | InvalidModelMethodInvocationException | ModelExecutionException e) {
            handleError("Error while restarting the match", Optional.of(e));
        }
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void update(final ModelEvent event) {
        switch (event.getType()) {
            case VICTORY:
                try {
                    gameLoopSetting();
                    gameLoop.stop();
                } catch (NotStartedException e) {
                    LOGGER.info("Safe thread interrupted safely");

                } finally {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            view.victory(getModel().getPlayer().getCoins(), this.getModel().getLevel().getTotalCoins());
                            view.changeView(ViewScene.START_MENU);

                        } catch (ModelExecutionException e) {
                            LOGGER.error("Impossible to retrieve coins");
                            view.victory(0, 0);
                        }
                    });
                }
                break;
            case GAMEOVER:
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameModel getModel() {
        return this.gameModel;
    }

    /**
     * A utility method that start the game model and the game loop handling their
     * errors.
     */
    private void startLevel() {
        try {
            gameLoopSetting();
            gameModel.start(LEVEL_NAME);
            view.init(gameResolution);
            view.changeView(ViewScene.IN_GAME);
            gameLoop.start();
        } catch (InvalidGameLoopStatusException | InvalidGameLoopConfigurationException | ModelExecutionException
                | InvalidModelMethodInvocationException e) {
            handleError(LEVEL_NAME, Optional.of(e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View getView() {
        return this.view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.initInputHandler();
        try {
            this.view.show();
        } catch (NotStartedViewException e) {
            LOGGER.error("impossible to start the view", e);
        }
    }

    private void safeClosing() {
        try {
            if (this.gameLoop != null) {
                this.gameLoop.stop();
            }
        } catch (final NotStartedException e) {
            LOGGER.info("Safe thread interrupted safely");
        } finally {
            this.view.disposeView();
            System.exit(0);
        }
    }

}
