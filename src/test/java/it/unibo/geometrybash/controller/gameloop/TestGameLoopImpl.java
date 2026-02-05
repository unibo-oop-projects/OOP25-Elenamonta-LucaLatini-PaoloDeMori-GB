package it.unibo.geometrybash.controller.gameloop;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.controller.gameloop.exceptions.FpsNotCalculatedException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopConfigurationException;
import it.unibo.geometrybash.controller.gameloop.exceptions.InvalidGameLoopStatusException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotOnPauseException;
import it.unibo.geometrybash.controller.gameloop.exceptions.NotStartedException;

class TestGameLoopImpl {
    private static final String INTERRUPT_RECEIVED_MESSAGE = "interrupt received";
    private static final String ELAPSED_MAX_TIME = "Gameloop slept too much";
    private static final long MAX_WAITING_TIME = 10_000L;
    private static final long SMALL_AMOUNT_OF_TIME = 200L;

    /**
     * Tests that an InvalidGameLoopConfigurationException is thrown if the gameLoop
     * is started without a correct initialization.
     * To correctly initialize a GameLoopImpl instance you have to call the
     * setUpdateAction() method
     * or set an OnUpdateAction through the constructor.
     */
    @Test
    void testInvalidInitialization() {
        final GameLoop gL = new GameLoopImpl();
        assertThrows(InvalidGameLoopConfigurationException.class, gL::start);
    }

    /**
     * Tries to start a gameloop with a correct intilialization.
     * It verifies that a correct exception is thrown if a client tries to start a
     * gameloop that has already been started.
     */
    @Test
    void testCorrectInitializationAndExecution() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        // tests that the exception is correctly thrown
        assertThrows(InvalidGameLoopStatusException.class, gL::start);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertTrue(eC.isValue());
    }

    /**
     * Tries to start a gameloop with a correct intilialization using the
     * constructor.
     */
    @Test
    void testCorrectInitializationWithConstructorAndExecution() {
        final ExampleClass eC = new ExampleClass();
        final GameLoop gL = new GameLoopImpl(eC::executable);
        assertDoesNotThrow(gL::start);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertTrue(eC.isValue());
    }

    /**
     * Tries to start and stop a gameloop with a correct intilialization, and it
     * verifies if the method set is correctly executed.
     * It verifies that a NotStartedException is thrown if a client tries to stop a
     * gameloop that hasn't been started.
     * This test uses an instance of Example class to verify that the gameloop
     * executed the set AcionOnUpdate.
     */
    @Test
    void testCorrectStartAndStop() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertThrows(NotStartedException.class, gL::stop);
        assertDoesNotThrow(gL::start);
        // sleep to let the thread execute the method set at least once.
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertTrue(eC.isValue());
        assertDoesNotThrow(gL::stop);
        assertTrue(gL.isTerminatedSafely());
    }

    /**
     * Tries to start, stop, pause and resume a gameloop with a correct
     * intilialization,
     * and it verifies if the method set is correctly executed.
     * It verifies that a NotStartedException is thrown if a client tries to stop a
     * gameloop that hasn't been started.
     * This test uses an instance of Example class to verify that the gameloop
     * executed the set AcionOnUpdate.
     */
    @Test
    void testCorrectPauseAndResume() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertThrows(NotStartedException.class, gL::pause);
        assertThrows(NotStartedException.class, gL::resume);
        assertDoesNotThrow(gL::start);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertThrows(NotOnPauseException.class, gL::resume);
        // Check if the thread correctly executed the function
        assertTrue(eC.isValue());

        // set the variable as false thinking that it will be set as true by the
        // gameloop
        eC.setFalse();
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        // check if the thread set the variable as true
        assertTrue(eC.isValue());
        assertDoesNotThrow(gL::pause);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertThrows(InvalidGameLoopStatusException.class, gL::pause);

        // set the variable as false thinking it won't change since the gameloop is
        // paused
        eC.setFalse();
        try {
            eC.optimizedWaiting(SMALL_AMOUNT_OF_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        // check if the variable is still false
        assertFalse(eC.isValue());

        assertDoesNotThrow(gL::resume);

        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }

        // check if the variable was set as true since the thread resumed
        assertTrue(eC.isValue());

        assertDoesNotThrow(gL::stop);

        assertDoesNotThrow(gL::start);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertDoesNotThrow(gL::pause);
        try {
            eC.optimizedWaiting(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(ELAPSED_MAX_TIME);
        }
        assertDoesNotThrow(gL::stop);
        assertTrue(gL.isTerminatedSafely());
    }

    /**
     * This test verifies that if a gameLoop is interrupted not safely.
     * The gameloop stops and set that it wasn't terminated safely.
     */
    @Test
    void testCorrectLoopInterruptInPause() {
        final GameLoop gL = new GameLoopImpl();
        final ThreadClass eC = new ThreadClass();
        gL.setUpdateAction(eC::waitForThread);
        assertDoesNotThrow(gL::start);
        // sleeps to let the thread start completely.
        try {
            eC.safeWait(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        assertDoesNotThrow(gL::pause);
        // sleeps to ensure that the thread paused completely.
        try {
            eC.safeWait(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        // interrupts the thread.
        eC.interruptThread();
        // sleeps to ensure that the interrupt handlig was executed correctly.
        try {
            eC.safeWait(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        // check if the terminatedSafely flag was correctly set.
        assertFalse(gL.isTerminatedSafely());
    }

    /**
     * Tests that if the thread is waiting the necessary time to keep a 60fps
     * gameloop,
     * is interrupted,the terminatedSafely flag is correctly set.
     */
    @Test
    void testCorrectLoopInterrupt() {
        final GameLoop gL = new GameLoopImpl();
        final ThreadClass eC = new ThreadClass();
        gL.setUpdateAction(eC::waitForThread);
        assertDoesNotThrow(gL::start);
        // sleep enough time to let the thread call a thread.sleep during the normal
        // execution.
        try {
            eC.safeWait(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        eC.interruptThread();
        try {
            eC.safeWait(MAX_WAITING_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        assertFalse(gL.isTerminatedSafely());
    }

    /**
     * Test that verifies that the thread is running at around 60fps.
     */
    @Test
    void testGetFps() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        // number of times to check the number of fps.
        final short timesToCycle = 3;
        // time to wait for the thread to calculate a new number of fps
        final long oneSecondAndOneMillisecond = 2500L;
        short fpsSum = 0;
        final short maxAverageFrameRate = 75;
        final short minAverageFramerate = 40;
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        // verifies that an exception is thrown if the client tries to get the number of
        // fps before the first evaluation.
        assertThrows(FpsNotCalculatedException.class, gL::getFPS);

        for (int i = 0; i < timesToCycle; i++) {
            try {
                Thread.sleep(oneSecondAndOneMillisecond);
                assertDoesNotThrow(gL::getFPS);
                fpsSum += gL.getFPS();
                // Chek if the gameloop is around 60fps.
            } catch (InterruptedException | FpsNotCalculatedException e) {
                fail("Unexpected Exception");
            }
        }
        // average calculation
        fpsSum /= timesToCycle;
        assertTrue(fpsSum <= maxAverageFrameRate && fpsSum >= minAverageFramerate);

        assertDoesNotThrow(gL::stop);
    }

    /**
     * An example class that handles a boolean flag.
     */
    class ExampleClass {
        private volatile boolean wasExecuted;
        private final Object lock = new Object();
        private volatile boolean value;

        boolean wasMethodExecuted() {
            synchronized (lock) {
                if (this.wasExecuted) {
                    this.wasExecuted = false;
                    lock.notifyAll();
                    return true;
                }
                this.wasExecuted = false;
                return false;
            }
        }

        boolean optimizedWaiting(final long timeOutTime) throws InterruptedException {
            synchronized (lock) {
                final long timeoutTime = System.currentTimeMillis() + timeOutTime;
                while (!wasMethodExecuted()) {
                    final long elapsedTimeout = timeoutTime - System.currentTimeMillis();
                    if (elapsedTimeout <= 0) {
                        return false;
                    }
                    lock.wait(elapsedTimeout);
                }
                return true;
            }
        }

        private void executable() {
            synchronized (lock) {
                this.wasExecuted = true;
                this.value = true;
                lock.notifyAll();
            }
        }

        void setFalse() {
            synchronized (lock) {
                this.wasExecuted = false;
                value = false;
            }
        }

        boolean isValue() {
            synchronized (lock) {
                return this.value;
            }
        }
    }

    /**
     * An example class that take the thread in which is running and offers a method
     * to interrupt it.
     */
    class ThreadClass {
        private volatile Thread currentThread;

        private final Object obj = new Object();

        boolean safeWait(final long timeout) throws InterruptedException {
            synchronized (obj) {
                final long timeoutTime = System.currentTimeMillis() + timeout;
                while (this.currentThread == null) {
                    final long elapsedTimeout = timeoutTime - System.currentTimeMillis();
                    if (elapsedTimeout <= 0) {
                        return false;
                    }
                    obj.wait(elapsedTimeout);
                }
                return true;
            }
        }

        private void waitForThread() {
            synchronized (obj) {
                if (currentThread == null) {
                    this.currentThread = Thread.currentThread();
                    obj.notifyAll();
                }
            }
        }

        private void interruptThread() {
            synchronized (obj) {

                if (currentThread != null) {
                    currentThread.interrupt();
                } else {
                    fail("Impossible to interrupt a thread that doesn't exist");
                }
            }
        }
    }
}
