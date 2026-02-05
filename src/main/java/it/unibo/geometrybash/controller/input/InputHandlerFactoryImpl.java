package it.unibo.geometrybash.controller.input;

import it.unibo.geometrybash.controller.InputHandler;

/**
 * Concrete implementation of {@link InputHandlerFactory}.
 *
 * <p>
 * Provides a standardize way to obtain a {@link CompositeInputHandler}.
 */
public class InputHandlerFactoryImpl implements InputHandlerFactory {

    /**
     * Create new {@link CompositeInputHandler}.
     */
    public InputHandlerFactoryImpl() {
        // Default constructor.
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public InputHandler createInputHandler() {
        return new CompositeInputHandler();
    }

}
