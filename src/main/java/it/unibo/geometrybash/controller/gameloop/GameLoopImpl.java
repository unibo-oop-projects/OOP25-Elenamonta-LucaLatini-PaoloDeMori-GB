package it.unibo.geometrybash.controller.gameloop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.geometrybash.controller.gameloop.exceptions.FpsNotCalculatedException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopConfigurationException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotOnPauseException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotStartedException;

/**
 * An implementation of the GameLoop interface.
 * 
 * <p>This implementation realizes a GameLoop with a stable 60fps.
 * This class uses {@link java.lang.Thread} to create a dedicated thread,
 * {@link java.lang.Object#wait()} and {@link java.lang.Object#notifyAll()} methods for the safe access to variables 
 * and to set the thread on Pause.
 * 
 * @see java.lang.Thread
 * @see java.lang.Object
 * @see java.lang.Object#wait()
 * @see java.lang.Object#notifyAll()
 */
public class GameLoopImpl implements GameLoop {

    /**
     * The amount of nanoseconds in a second.
     */
    private static final long NANO_SECONDS_IN_A_SECOND = 1_000_000_000L;

    /**
     * The amount of nanoseconds in a millisecond.
     */
    private static final long NANO_SECONDS_IN_A_MILLISECOND = 1_000_000L;

    /**
     * The amount of frames that the class tries to maintain while executing the loop.
     */
    private static final short GOAL_FRAMES_PER_SECOND = 60;
    /**
     * The number of nanoseconds that a step of the cycle needs to have to maintain a fixed frame rate.
     */
    private static final long EXPECTED_NANOSECONDS_PER_FRAME = NANO_SECONDS_IN_A_SECOND / GOAL_FRAMES_PER_SECOND;

    /**
     * An instance of the Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLoopImpl.class);

    /**
     * The thread is not defined here and it is not final, to allow an easy handling of the start method.
     * 
     * <p>Since the method {@link java.lang.Thread#start()} can be called only once per instance,
     * This class creates a new instance of a {@link java.lang.Thread} every time it starts a thread.
     */
    private Thread gameLoopThread;

    private volatile boolean isPaused;
    private volatile boolean isStarted;
    private volatile boolean isTerminatedSafely;

    /**
     * The Object that uses {@link java.lang.Object} implemented lock ,used for the concurrent access to the pause variable.
     */
    private final Object lock = new Object();

    /**
     * The variable that contains the functional interface that represents the action to execute every cycle.
     */
    private ActionOnLoopChange step;
    /**
     * The amount of fps evaluated in the last 60s.
     */
    private volatile short fps;

    /**
     * a flag that is set on false until the first Fps calculation.
     */
    private volatile boolean isFirstFpsCalculationDone;

    /**
     * A constructor for a GameLoop implementation that uses {@link java.lang.Thread} and keeps stable 60fps.
     * 
     * <P>This Constructor initializes the instance but does not assign the action to accomplish every loop cycle.
     */
    public GameLoopImpl() {
        super();
        isPaused = false;
        isStarted = false;
    }

    /** 
     * A constructor for a GameLoop implementation that uses {@link java.lang.Thread} and keeps stable 60fps.
     * 
     * <p>This Constructor initializes completely the instance.
     * 
     * @param action a functional interface that represents the action to execute continuously.
     */
    public GameLoopImpl(final ActionOnLoopChange action) {
        this();
        this.setUpdateAction(action);
    }

    /**
     * The internal implementation of the game loop that uses a {@link java.lang.Thread},
     *  to create an approximately 60fps gameloop.
     */
    private void loop() {
        long firstTime;
        long elapsedTime;
        long timeToWait;
        long secondTimeCounter = System.nanoTime();
        short fpsEvaluating = 0;
        LOGGER.debug("A Loop started");

            while (isStarted) {
                /*
                *Check if at least one second has passed from the gameloop start.
                *It's necessary to give always a coherent fps value if required.
                */
                fpsEvaluating++;
                firstTime = System.nanoTime();
                if (firstTime - secondTimeCounter > NANO_SECONDS_IN_A_SECOND) {
                    fps = fpsEvaluating;
                    isFirstFpsCalculationDone = true;
                    fpsEvaluating = 0;
                    secondTimeCounter = System.nanoTime();
                }

                /*
                *Pause section,
                * the thread uses the default lock mechanism of java.lang.Object lock to wait until it receives a signal.
                */
                synchronized (lock) {
                    while (isPaused) {
                        try {
                            lock.wait();
                            firstTime = System.nanoTime();
                            secondTimeCounter = System.nanoTime();
                            fps = 0;
                            fpsEvaluating = 0;
                        } catch (final InterruptedException e) {
                            if (!isStarted) {
                            LOGGER.info("The thread was safely interrupted to allow its termination");
                            isTerminatedSafely = true;
                            return;
                            } else {
                                isStarted = false;
                                LOGGER.error("The thread was unexpectedly interrupted.");
                                isTerminatedSafely = false;
                                return;
                            }
                        }
                    }
                }

                //Executes the designated action.
                step.executeAction();
                elapsedTime = System.nanoTime() - firstTime;

                /*
                 *The following section is necessary to keep the gameloop on about 60fps, 
                 *if it is going faster it calls a Thread.sleep.
                 */
                if (elapsedTime < EXPECTED_NANOSECONDS_PER_FRAME) {
                    timeToWait = EXPECTED_NANOSECONDS_PER_FRAME - elapsedTime;

                    final long timeToWaitMillis = timeToWait / NANO_SECONDS_IN_A_MILLISECOND;
                    final int timeToWaitNanos = (int) (timeToWait % NANO_SECONDS_IN_A_MILLISECOND);

                    try {
                        /*
                         * Implementing this method it was obvious that the nanoseconds lost, 
                         * using Thread.sleep(long millis) weren't negligible.
                         *  That amount of time made the gameloop go at at least 4/5 fps more per second.
                         * That's why the following section uses Thread.sleep.(long millis, int nanos)
                         */
                        Thread.sleep(timeToWaitMillis, timeToWaitNanos);
                    } catch (final InterruptedException e) {
                        if (!isStarted) {
                            LOGGER.info("The game loop thread was safely interrupted to allow its termination");
                            isTerminatedSafely = true;
                            return;
                            } else {
                                isStarted = false;
                                LOGGER.error("The game loop thread was unexpectedly interrupted.");
                                isTerminatedSafely = false;
                                return;
                            }
                    }
                }
            }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>It uses a synchronized block for thread-safe access to variables.
     */
    @Override
    public void start() throws InvalidGameLoopStatusException, InvalidGameLoopConfigurationException {
        synchronized (lock) {
        if (step == null) {
            throw new InvalidGameLoopConfigurationException();
        }
            if (isStarted) {
                throw new InvalidGameLoopStatusException();
            }
            isStarted = true;
            gameLoopThread = new Thread(this::loop, "GameLoop");
            gameLoopThread.start();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>It uses a synchronized block for thread-safe access to variables.
     */
    @Override
    public void pause() throws NotStartedException, InvalidGameLoopStatusException {
        synchronized (lock) {
            if (!isStarted) {
                throw new NotStartedException();
            }
            if (isPaused) {
               throw new InvalidGameLoopStatusException();
            }
            isPaused = true;
            LOGGER.debug("A Thread Game Loop is Paused");
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>It uses a synchronized block for thread-safe access to variables
     * and {@link java.lang.Object#notifyAll()} to handle the pause mechanism.
     */
    @Override
    public void resume() throws NotOnPauseException, NotStartedException {
        synchronized (lock) {
            if (!isStarted) {
                throw new NotStartedException();
            }
            if (!isPaused) {
               throw new NotOnPauseException();
            }
            lock.notifyAll();
            LOGGER.debug("A Thread Game Loop is Resumed");
            isPaused = false;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>It uses a synchronized block for thread-safe acces to variables
     */
    @Override
    public void stop() throws NotStartedException {
        synchronized (lock) {
            if (!isStarted) {
                throw new NotStartedException();
            }
            isStarted = false;
            this.isTerminatedSafely = true;
            lock.notifyAll();
            gameLoopThread.interrupt();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setUpdateAction(final ActionOnLoopChange action) {
        this.step = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public short getFPS() throws FpsNotCalculatedException {
        if (!isFirstFpsCalculationDone) {
            throw new FpsNotCalculatedException();
        }
        return this.fps;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>It uses a volatile variable.
     */
    @Override
    public boolean isTerminatedSafely() {
        return this.isTerminatedSafely;
    }
}
