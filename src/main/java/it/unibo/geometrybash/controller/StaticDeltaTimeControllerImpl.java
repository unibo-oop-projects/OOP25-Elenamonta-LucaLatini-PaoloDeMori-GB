package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.input.InputHandlerFactoryImpl;
import it.unibo.geometrybash.controller.gameloop.GameLoopFixedExecutionTimeFactory;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.view.View;

/**
 * An AbstractControllerImpl implementation that uses a static delta time.
 */
public class StaticDeltaTimeControllerImpl extends AbstractControllerImpl {
    /**
     * The static delta time.
     */
    private static final float DELTA_TIME = 1f / 60f;

    /**
     * The constructor of this class.
     * 
     * @param gameModel del to use as the "brain" of this
     * 
     * @param view      omponent to show the gui of this
     * 
     */
    public StaticDeltaTimeControllerImpl(final GameModel gameModel, final View view) {
        super(gameModel, view, new GameLoopFixedExecutionTimeFactory(), new InputHandlerFactoryImpl());
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation returns a static delta time.
     * </p>
     */
    @Override
    protected float evaluateDeltaTime() {
        return DELTA_TIME;
    }

}
