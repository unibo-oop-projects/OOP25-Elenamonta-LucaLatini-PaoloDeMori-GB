package it.unibo.geometrybash.controller.input;

import it.unibo.geometrybash.controller.InputHandler;

/**
 * Factory interface for creating {@link InputHandler} istances.
 *
 * <p>
 * This factory helps keep controllers well encapsulated.
 * Passing a mutable {@link InputHandler} directly to a constructor may expose
 * its internal state.
 * Using this factory allows the controller to create its own handler.
 */
@FunctionalInterface
public interface InputHandlerFactory {

    /**
     * Creates and returns a new instance of {@link InputHandler}.
     *
     * <p>
     * The returned instance is typically a {@code CompositeInputHandler},
     * ready to be configured by the controller.
     *
     * @return a new, independent instance of InputHandler.
     */
    InputHandler createInputHandler();

}
