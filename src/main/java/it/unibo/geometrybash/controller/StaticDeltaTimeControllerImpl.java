package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.commons.assets.ResourceLoader;
import it.unibo.geometrybash.controller.gameloop.GameLoopFixedExecutionTimeFactory;
import it.unibo.geometrybash.controller.input.InputHandlerFactoryImpl;
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
     * @param gameModel      component responsible of executing and evaluating the
     *                       logic of this game.
     *
     * @param view           component responsible of showing the gui of this game.
     * 
     * @param resourceLoader the object responsible to retrieve resources.
     * 
     *
     */
    public StaticDeltaTimeControllerImpl(final GameModel gameModel, final View view,
            final ResourceLoader resourceLoader) {
        super(gameModel, view, new GameLoopFixedExecutionTimeFactory(), new InputHandlerFactoryImpl(), resourceLoader);
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
