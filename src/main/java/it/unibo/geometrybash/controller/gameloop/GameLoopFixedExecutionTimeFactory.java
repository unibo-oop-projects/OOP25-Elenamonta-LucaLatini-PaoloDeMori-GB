package it.unibo.geometrybash.controller.gameloop;

/**
 * A factory to create an instance of a gameloop that executes personalizable
 * actions.
 * 
 * <p>
 * This implementation creates an instance of a gamellop that uses native java
 * locks and executes a cycle every 1/60 second.
 * </p>
 */
public class GameLoopFixedExecutionTimeFactory implements GameLoopFactory {

    /**
     * Default constructor.
     */
    public GameLoopFixedExecutionTimeFactory() {
        // default constructor.
    }

    /**
     * {@inheritDoc}
     * 
     * 
     * <p>
     * Creates an instance of a gamellop that uses native java
     * locks and executes a cycle every 1/60 second.
     * </p>
     */
    @Override
    public GameLoop createGameLoop() {
        return new GameLoopImpl();
    }

    /**
     * {@inheritDoc}
     * 
     * 
     * <p>
     * Creates an instance of a gamellop that uses native java
     * locks and executes a cycle every 1/60 second.
     * </p>
     */
    @Override
    public GameLoop createGameLoop(final ActionOnLoopChange action) {
        return new GameLoopImpl(action);
    }

}
