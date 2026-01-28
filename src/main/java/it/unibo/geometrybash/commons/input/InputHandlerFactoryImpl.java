package it.unibo.geometrybash.commons.input;

import it.unibo.geometrybash.controller.InputHandler;
import it.unibo.geometrybash.controller.input.CompositeInputHandler;

/**
 * Concrete implementation of {@link InputHandlerFactory}.
 *
 * <p>
 * Provides a standardize way to obtain a {@link CompositeInputHandler}.
 */
public class InputHandlerFactoryImpl implements InputHandlerFactory {

    /**
     * {@inheritDoc}.
     */
    @Override
    public InputHandler createInputHandler() {
        return new CompositeInputHandler();
    }

}
