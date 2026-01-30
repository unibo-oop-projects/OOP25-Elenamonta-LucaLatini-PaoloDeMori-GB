package it.unibo.geometrybash.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.commons.UpdateInfoDto;
import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.exceptions.InvalidModelMethodInvocationException;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.level.Level;
import it.unibo.geometrybash.model.level.loader.LevelLoader;
import it.unibo.geometrybash.model.level.loader.LevelLoaderImpl;
import it.unibo.geometrybash.model.level.loader.exception.LoadingFileException;
import it.unibo.geometrybash.model.physicsengine.AbstractGameModelWithPhysicsEngine;
import it.unibo.geometrybash.model.physicsengine.PhysicsEngineFactory;
import it.unibo.geometrybash.model.physicsengine.exception.ModelExecutionException;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;
import it.unibo.geometrybash.model.player.PlayerWithPhysics;

/**
 * An implementatio of the game model using JBox2d.
 */
public final class GameModelImpl extends AbstractGameModelWithPhysicsEngine<Body> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameModelImpl.class);
    private static final String INVALID_STATE_EXCEPTION_MESSAGE = "A method is called while not in the correct state";

    private Status state = Status.NEVERSTARTED;
    private PlayerWithPhysics player;
    private Level level;
    private final LevelLoader levelLoader;
    private final ResourceLoader rLoader;
    private String levelName;
    private final PhysicsEngineFactory<Body> physicsFactory;
    private final List<GameObject<?>> changedStateObjects;
    private final GameStateMapper gameStateMapper;
    private volatile boolean isJumpSignalActive;

    /**
     * The constructor of this gamemodel implementation.
     *
     * @param rLoader the resource laoder.
     * @param pEF     the factory to create jbox2d entities.
     */
    public GameModelImpl(final ResourceLoader rLoader, final PhysicsEngineFactory<Body> pEF) {
        this.rLoader = rLoader;
        this.levelLoader = new LevelLoaderImpl(this::resetStateObjects);
        this.physicsFactory = pEF;
        this.changedStateObjects = new ArrayList<>();
        this.gameStateMapper = new GameStateMapper();
    }

    /**
     * Method called when the player dies.
     *
     * <p>
     * Linked to the game execution through a functional interface in the player.
     * </p>
     */
    private void onPlayerDeath() {
        notifyObservers(ModelEvent.createGameOverEvent());
        changedStateObjects.forEach(i -> i.setActive(true));
        changedStateObjects.clear();
    }

    /**
     * Notify the observer that the player fullfil the victory condition.
     */
    private void onPlayerWin() {
        notifyObservers(ModelEvent.createVictoryEvent());
    }

    /**
     * Method to pass to the selected objects in the level loader for handle state
     * changes.
     *
     * @param gO the object calling this method that lived a state change.
     */
    private void resetStateObjects(final GameObject<?> gO) {
        this.changedStateObjects.add(gO);
    }

    /**
     * Private method to throw and log a default error if a method is called while
     * the finite state machine is in an invalid state.
     *
     * @throws InvalidModelMethodInvocationException if a method is called while
     *                                               the finite state machine is in
     *                                               an invalid state.
     */
    private void invalidStateThrower() throws InvalidModelMethodInvocationException {
        final InvalidModelMethodInvocationException ex = new InvalidModelMethodInvocationException(
                INVALID_STATE_EXCEPTION_MESSAGE);
        LOGGER.error(INVALID_STATE_EXCEPTION_MESSAGE, ex);
        throw ex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final String levName) throws InvalidModelMethodInvocationException,
            ModelExecutionException {
        switch (state) {
            case NEVERSTARTED:
                this.levelName = levName;
                try (InputStream levelIS = rLoader.openStream("it/unibo/geometrybash/level/" + levName + ".json")) {
                    this.setPhysicsEngine(physicsFactory);
                    final String exceptionInReadingFileMessage = "Error while creating the "
                            + "level from file in the model's start method";
                    try {
                        level = levelLoader.levelLoaderFromInputStream(levelIS);
                    } catch (final LoadingFileException e) {
                        LOGGER.error(exceptionInReadingFileMessage, e);
                        throw new ModelExecutionException(exceptionInReadingFileMessage, e);
                    }

                    final Vector2 playerStartPosition = level.getPlayerStartPosition();
                    player = new PlayerImpl(playerStartPosition);
                    player.setOnDeath(this::onPlayerDeath);
                    this.getPhysicsEngine().addPlayer(player);
                    this.addUpdatableGameObjects((PlayerImpl) player);

                    level.getAllGameObject().stream().forEach(i -> this.getPhysicsEngine().addGameObject(i));

                    this.state = Status.PLAYING;
                } catch (final IOException e) {
                    final String errorOnInputStream = "Error while using the inputstream";
                    LOGGER.error(errorOnInputStream, e);
                    throw new ModelExecutionException(errorOnInputStream, e);
                }
                break;
            default:
                this.invalidStateThrower();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() throws InvalidModelMethodInvocationException {
        switch (state) {
            case PLAYING:
                this.state = Status.ONPAUSE;
                break;
            default:
                this.invalidStateThrower();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() throws InvalidModelMethodInvocationException {
        switch (state) {
            case ONPAUSE:
                this.state = Status.PLAYING;
                break;
            default:
                this.invalidStateThrower();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restart() throws InvalidModelMethodInvocationException, ModelExecutionException {
        this.state = Status.NEVERSTARTED;
        clearUpdatableList();
        this.changedStateObjects.clear();
        start(levelName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void jumpSignal() {
        this.isJumpSignalActive = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player<HitBox> getPlayer() throws ModelExecutionException {
        if (this.player != null) {
            return this.player.copy();
        }
        final String errorOnInputStream = "Trying to obtain a player never set";
        LOGGER.error(errorOnInputStream);
        throw new ModelExecutionException(errorOnInputStream);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Level getLevel() {
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Status getStatus() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterGameObjectsUpdate(final float deltaTime) {
        if (player != null && level != null && this.level.playerWin(this.player.getPosition())) {
            this.onPlayerWin();
        }

        this.getPhysicsEngine().updatePhysicsEngine(deltaTime);
        respawnPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isUpdatable() {
        switch (state) {
            case PLAYING:
                return true;
            default:
                return false;
        }
    }

    @Override
    public UpdateInfoDto toDto() throws ModelExecutionException {
        return new UpdateInfoDto(this.gameStateMapper.toDto(this));
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * In this implementation of {@link GameModel} i use this method to call the
     * jump signal in a thread
     * safe wai.
     * </p>
     */
    @Override
    protected void beforeGameObjectsUpdate(final float deltaTime) {
        if (this.isJumpSignalActive && this.player != null) {
            this.player.jump();
            this.isJumpSignalActive = false;
        }
    }

    @Override
    public void respawnPlayer() {
        if (player != null && level != null) {
            player.respawn(this.level.getPlayerStartPosition());
        }
    }

}
