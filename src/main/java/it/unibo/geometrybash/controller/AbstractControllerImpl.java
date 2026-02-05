package it.unibo.geometrybash.controller;

import java.util.Optional;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.input.StandardViewEventType;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.controller.gameloop.GameLoop;
import it.unibo.geometrybash.controller.gameloop.GameLoopFactory;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopConfigurationException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotOnPauseException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotStartedException;
import it.unibo.geometrybash.controller.input.InputHandlerFactory;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.model.MenuModel;
import it.unibo.geometrybash.model.Status;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.view.View;
import it.unibo.geometrybash.view.ViewScene;
import it.unibo.geometrybash.view.exceptions.ExecutionWithIllegalThreadException;
import it.unibo.geometrybash.view.exceptions.NotStartedViewException;
import it.unibo.geometrybash.view.menus.MainMenuView;
import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * Abstract Implementation of the controller interface with an undefined method
 * to evaluate deltatime.
 */
public abstract class AbstractControllerImpl implements Controller {
    /**
     * Logger instance to handle errors and sending debug information.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractControllerImpl.class);
    private static final String ON_HISTORY_DESCRIPTION = "LIST OF FUNCTIONING COMMANDS USED:";
    private static final String ON_LEVEL_CHANGED = "Level correctly set! :)";

    private static final String ON_CORRECT_RESOLUTION_CHANGE = "\"Correctly set size.\"";
    private static final String ON_CORRECT_COLOR_UPDATE = "\"Correctly updated color.\"";
    private static final String HISTORY_COMMAND = "history";

    private static final String ON_MUSIC_ERROR = "The game is running without music, "
            + "because an error occured while loading the audiofiles";

    private static final String DEBUG_MESSAGE = "For debug porpuses->";

    private final GameModel gameModel;
    private final View view;
    private final InputHandler inputHandler;
    private GameLoop gameLoop;
    private final GameLoopFactory gameLoopFactory;
    private GameResolution gameResolution = GameResolution.MEDIUM;
    private final ControllerAudioScheduler audioScheduler;
    private final MenuModel menuModel;
    private String levelName = MenuModel.LEVELS_NAME_LIST.get(0);

    /**
     * The constructor of the controller with game model, view and input handler
     * creation delegated.
     *
     * @param gameModel                the model of the game
     * @param view                     the main view class of the game
     * @param gameLoopFactory          the factory to init the gameloop.
     * @param inputHandlerFactory      the factory to init the inputHandler.
     * @param controllerAudioScheduler the object used to handle the music.
     * 
     */

    @SuppressFBWarnings(value = "EI2", justification = ""
            + "I'm delegating the creation of many classes to improve the reusability of this class."
            + "Im using interfaces to give the parameter's classes default behaviour ")
    public AbstractControllerImpl(final GameModel gameModel, final View view, final GameLoopFactory gameLoopFactory,
            final InputHandlerFactory inputHandlerFactory, final ControllerAudioScheduler controllerAudioScheduler) {
        this.gameModel = gameModel;
        this.gameModel.addObserver(this);
        this.view = view;
        this.inputHandler = inputHandlerFactory.createInputHandler();
        this.view.addObserver(inputHandler);
        this.gameLoopFactory = gameLoopFactory;
        this.audioScheduler = controllerAudioScheduler;
        this.menuModel = new MenuModel();
    }

    /**
     * A method that returns the delta time, it can either be static or evaluated.
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
        this.inputHandler.setOnResumeKeyPressed(this::gameResume);
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
        if (onColorCommand(command)) {
            return;
        }

        if (onResizeCommand(command)) {
            return;
        }

        if (onHistoryCommand(command)) {
            return;
        }
        if (onSetLevelCommand(command)) {
            return;
        }
        if (onLevelsCommand(command)) {
            return;
        }
        if (onColorsCommand(command)) {
            return;
        }

        view.showCommandsError(command);
    }

    /**
     * Private method to evaluate a genericcommand, if the command is the correct
     * command to
     * change the player color, this method changes it and return true, otherwise
     * return false.
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         change the player color false otherwise.
     */
    private boolean onColorCommand(final String command) {
        if (GenericCommands.checkSetPlayerColorCommand(command, c -> this.gameModel.setPlayerInnerColor(c), // NOPMD
                // suppressed because using this.gameModel::setPlayerInnerColor doesn't
                // explicitly handle the cast int->integer
                c -> this.gameModel.setPlayerOuterColor(c))) { // NOPMD
            // suppressed because using this.gameModel::setPlayerOuterColor doesn't
            // explicitly handle the cast int->integer
            this.menuModel.addCommand(command);
            this.view.appendText(ON_CORRECT_COLOR_UPDATE);
            return true;
        }
        return false;
    }

    /**
     * Private method to evaluate a genericcommand, if the command is the correct
     * command to
     * change the GamePanel size, this method changes it and return true, otherwise
     * return false.
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         change the game panel resolution false otherwise.
     */
    private boolean onResizeCommand(final String command) {
        final Optional<GameResolution> resolution = GenericCommands.checkResolutionCommand(command);
        if (resolution.isPresent()) {
            this.gameResolution = resolution.get();
            this.view.appendText(ON_CORRECT_RESOLUTION_CHANGE);
            this.menuModel.addCommand(command);
            if (this.gameModel.getStatus().equals(Status.ONPAUSE)) {
                this.view.init(gameResolution);
            }
            return true;
        }
        return false;
    }

    /**
     * Private method to evaluate a genericcommand, if the command is the correct
     * command to
     * retrieve the History of size, this method changes it and return true,
     * otherwise
     * return false.
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         change the game panel resolution false otherwise.
     */
    private boolean onHistoryCommand(final String command) {
        if (HISTORY_COMMAND.equals(command)) {
            view.appendText(ON_HISTORY_DESCRIPTION);
            menuModel.getHistory().stream().forEach(this.view::appendText);
            menuModel.addCommand(command);
            return true;
        }
        return false;
    }

    /**
     * Private method to evaluate a genericcommand, if the command is the correct
     * command to
     * retrieve the list of levels, this method shows it in the view and return
     * true,
     * otherwise
     * return false.
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         show the levels list false otherwise.
     */
    private boolean onLevelsCommand(final String command) {
        if (MainMenuView.CMD_LEVELS.equals(command)) {
            view.showLevels(MenuModel.LEVELS_NAME_LIST);
            menuModel.addCommand(command);
            return true;
        }
        return false;
    }

    /**
     * Private method to evaluate a generic command, if the command is the correct
     * command to
     * retrieve the map of colors, this method shows it in the view and return
     * true,
     * otherwise
     * return false.
     * 
     * <p>
     * THis method shows a map that links color names with their hexadecimal rgba
     * representation.
     * </p>
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         show the colors map false otherwise.
     */
    private boolean onColorsCommand(final String command) {
        if (MainMenuView.CMD_COLORS.equals(command)) {
            view.showColors(MenuModel.AVAILABLE_COLORS);
            menuModel.addCommand(command);
            return true;
        }
        return false;
    }

    /**
     * Private method to evaluate a genericcommand, if the command is the correct
     * command to
     * change the level, this method changes it and return true,
     * otherwise
     * return false.
     * 
     * @param command the received command
     * @return true if the command is is the correct
     *         command to
     *         change the level false otherwise.
     */
    private boolean onSetLevelCommand(final String command) {
        final Optional<Integer> value = GenericCommands.checkSelectLevelCommand(command);
        if (value.isPresent() && MenuModel.LEVELS_NAME_LIST.size() >= value.get()
                && value.get() >= 0
                && this.gameModel.getStatus().equals(Status.NEVERSTARTED)) {
            this.levelName = MenuModel.LEVELS_NAME_LIST.get(value.get());
            menuModel.addCommand(command);
            view.appendText(ON_LEVEL_CHANGED + "level's name -> " + levelName);
            return true;
        }

        return false;
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
            SwingUtilities.invokeLater(() -> errorMessage(message, ex));
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
        view.showExecutionError(message);
        view.changeView(ViewScene.START_MENU);
        if (e.isPresent()) {
            LOGGER.error(message, e);
        } else {
            LOGGER.error(message);
        }
    }

    /**
     * Handle the possible exception thrown by the {@link audioScheduler}.
     */
    private void fromMenuToGameMusic() {
        try {
            this.audioScheduler.fromMenuToGame();
        } catch (final ImpossibleToReproduceMusicException e) {
            try {
                this.audioScheduler.firstStart();
                this.audioScheduler.fromMenuToGame();
            } catch (final ImpossibleToReproduceMusicException ex) {
                LOGGER.info(ON_MUSIC_ERROR);
                LOGGER.debug(DEBUG_MESSAGE, e);
            }
        }
    }

    /**
     * Handle the possible exception thrown by the {@link audioScheduler}.
     */
    private void fromGameToMenuMusic() {
        try {
            this.audioScheduler.fromGameToMenu();
        } catch (final ImpossibleToReproduceMusicException e) {
            try {
                this.audioScheduler.firstStart();
                this.audioScheduler.fromGameToMenu();
            } catch (final ImpossibleToReproduceMusicException ex) {
                LOGGER.info(ON_MUSIC_ERROR);
                LOGGER.debug(DEBUG_MESSAGE, e);
            }
        }
    }

    /**
     * Handle the possible exception thrown by the {@link audioScheduler}.
     */
    private void restartMusic() {
        try {
            this.audioScheduler.restartLevelMusic();
        } catch (final ImpossibleToReproduceMusicException e) {
            try {
                this.audioScheduler.firstStart();
                this.audioScheduler.restartLevelMusic();
            } catch (final ImpossibleToReproduceMusicException ex) {
                LOGGER.info(ON_MUSIC_ERROR);
                LOGGER.debug(DEBUG_MESSAGE, e);
            }
        }
    }

    /**
     * A utility method to resume the game.
     */
    private void gameResume() {
        try {
            if (this.gameModel.getStatus().equals(Status.ONPAUSE)) {
                fromMenuToGameMusic();
                gameModel.resume();
                gameLoopSetting();
                gameLoop.resume();
                view.changeView(ViewScene.IN_GAME);
            } else {
                this.view.showExecutionError(
                        "Please to start the game type \"start\","
                                + " \"resume\" is a command to use while on pause to resume the game");
            }
        } catch (final NotOnPauseException | NotStartedException | InvalidModelMethodInvocationException e) {
            handleError("Error while resuming the game", Optional.of(e));
        }
    }

    /**
     * A utility method to pause the game.
     */
    private void gamePause() {
        try {
            this.fromGameToMenuMusic();
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
            if (this.gameModel.getStatus().equals(Status.ONPAUSE)) {
                this.fromMenuToGameMusic();
                gameLoopSetting();
                this.gameModel.restart();
                this.gameLoop.resume();
                this.view.changeView(ViewScene.IN_GAME);
            } else {
                this.view.showExecutionError(
                        "Please to start the game type \"start\", "
                                + "\"restart\" is a command to use while on pause to restart the game");
            }
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
                } catch (final NotStartedException e) {
                    LOGGER.info("Safe thread interrupted safely");

                } finally {
                    this.fromGameToMenuMusic();
                    SwingUtilities.invokeLater(() -> {
                        try {
                            view.victory(this.gameModel.getPlayer().getCoins(),
                                    this.gameModel.getLevel().getTotalCoins());
                            view.changeView(ViewScene.START_MENU);

                        } catch (final ModelExecutionException e) {
                            LOGGER.error("Impossible to retrieve coins");
                            view.victory(0, 0);
                        }
                    });
                }
                break;
            case GAMEOVER:
                this.restartMusic();
                break;
        }
    }

    /**
     * A utility method that start the game model and the game loop handling their
     * errors.
     */
    private void startLevel() {
        try {
            if (this.gameModel.getStatus().equals(Status.NEVERSTARTED)) {
                this.fromMenuToGameMusic();
                gameLoopSetting();
                gameModel.start(levelName);
                view.init(gameResolution);
                view.changeView(ViewScene.IN_GAME);
                gameLoop.start();
            } else {
                this.view.showExecutionError(
                        "Please to restart the game type \"restart\", if you want to resume it just type \"resume\"");
            }
        } catch (InvalidGameLoopStatusException | InvalidGameLoopConfigurationException | ModelExecutionException
                | InvalidModelMethodInvocationException e) {
            handleError(levelName, Optional.of(e));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        try {
            this.audioScheduler.firstStart();
        } catch (final ImpossibleToReproduceMusicException e) {
            LOGGER.info("Impossible to init the music loader. the game starts without the music");
            LOGGER.debug(DEBUG_MESSAGE, e);
        }
        this.initInputHandler();
        try {
            this.view.show();
        } catch (final NotStartedViewException e) {
            LOGGER.error("impossible to start the view", e);
        }
    }

    @SuppressFBWarnings(value = "Dm", justification = ""
            + "Since the view uses a personalized close action i'm using System.exit(0) here "
            + "to allow the disposing of the awt thread.")
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Status getModelStatus() {
        return this.gameModel.getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTheModelSet() {
        return this.gameModel != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTheViewSet() {
        return this.view != null;
    }

}
