package it.unibo.geometrybash.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.exceptions.FpsNotCalculatedException;
import it.unibo.geometrybash.model.exceptions.InvalidGameLoopConfigurationException;

class TestGameLoopImpl {
    private static final String INTERRUPT_RECEIVED_MESSAGE = "interrupt received";
     private static final long ONE_EXECUTION_TIME = 50L;

    @Test
    void testInvalidInitialization() {
        final GameLoop gL = new GameLoopImpl();
        assertThrows(InvalidGameLoopConfigurationException.class, gL::start);
    }

    @Test
    void testCorrectInitializationAndExecution() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        assertTrue(eC.wasExecuted());
    }

    @Test
    void testCorrectStartAndStop() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        assertTrue(eC.wasExecuted());
        assertDoesNotThrow(gL::stop);

        assertTrue(gL.isTerminatedSafely());
    }

    @Test
    void testCorrectPauseAndResume() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        //Check if the thread correctly executed the function
        assertTrue(eC.wasExecuted());

        //set the variable as false thinking that it will be set as true by the gameloop
        eC.setFalse();
        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        //check if the thread set the variable as true
        assertTrue(eC.wasExecuted);
        assertDoesNotThrow(gL::pause);

        //set the variable as false thinking it won't change since the gameloop is paused
        eC.setFalse();
        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }
        //check if the variable is still false
        assertFalse(eC.wasExecuted);

        assertDoesNotThrow(gL::resume);

        try {
            Thread.sleep(ONE_EXECUTION_TIME);
        } catch (final InterruptedException e) {
            fail(INTERRUPT_RECEIVED_MESSAGE);
        }

        //check if the variable was set as true since the thread resumed
        assertTrue(eC.wasExecuted);

        assertDoesNotThrow(gL::stop);
    }

    @Test
    void testGetFps() {
        final GameLoop gL = new GameLoopImpl();
        final ExampleClass eC = new ExampleClass();
        final short timesToCycle = 5;
        final long oneSecondAndOneMillisecond = 6_001L;
        short a = 0;
        final short maxAverageFrameRate = 65;
        final short minAverageFramerate = 55;
        gL.setUpdateAction(eC::executable);
        assertDoesNotThrow(gL::start);
        assertThrows(FpsNotCalculatedException.class, gL::getFPS);

        for (int i = 0; i < timesToCycle; i++) {
        try {
            Thread.sleep(oneSecondAndOneMillisecond);
            assertDoesNotThrow(gL::getFPS);
            a += gL.getFPS();
            //Chek if the gameloop is around 60fps.
        } catch (InterruptedException | FpsNotCalculatedException e) {
            fail("Unexpected Exception");
        }
        }

        a /= timesToCycle;
        assertTrue(a <= maxAverageFrameRate && a >= minAverageFramerate);

        assertDoesNotThrow(gL::stop);
    }

    class ExampleClass {
        private volatile boolean wasExecuted;

        private void executable() {
            this.wasExecuted = true;
        }

        void setFalse() {
            wasExecuted = false;
        }

        boolean wasExecuted() {
            return this.wasExecuted;
        }
    }
}
