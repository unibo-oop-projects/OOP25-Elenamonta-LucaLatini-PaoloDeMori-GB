package it.unibo.geometrybash.controller.gameloop;

/**
 * A factory to create an instance of a gameloop that executes personalizable
 * actions.
 * 
 * @see GameLoop
 */
public interface GameLoopFactory {
    /**
     * Creates the instance of the gameLoop.
     * 
     * @return the instance of the gameLoop
     * @see GameLoop
     */
    GameLoop createGameLoop();

    /**
     * Creates the instance of the gameLoop.
     * 
     * @param action the action to execute on every cycle.
     * @return the instance of the gameLoop
     * @see GameLoop
     */
    GameLoop createGameLoop(ActionOnLoopChange action);

}
