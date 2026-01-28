package it.unibo.geometrybash.controller;

import it.unibo.geometrybash.controller.gameloop.GameLoopFactory;
import it.unibo.geometrybash.model.GameModel;
import it.unibo.geometrybash.view.View;

/**
 * An AbstractControllerImpl implementation that uses a static delta time.
 */
public class StaticDeltaTimeControllerImpl extends AbstractControllerImpl {
    /**
     * The static delta time.
     */
    private static final float DELTA_TIME = 1 / 60;

    /**
     * The constructor of this class.
     * 
     * @param gameModel       the gamemodel to use as the "brain" of this videogame.
     * @param view            the view component to show the gui of this videogame.
     * @param gameLoopFactory the factory to init the gameloop.
     */
    public StaticDeltaTimeControllerImpl(final GameModel gameModel, final View view,
            final GameLoopFactory gameLoopFactory) {
        super(gameModel, view, gameLoopFactory);
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
