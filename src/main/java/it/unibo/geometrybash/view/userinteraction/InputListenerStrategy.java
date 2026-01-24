package it.unibo.geometrybash.view.userinteraction;

/**
 * Functional strategy used by `InputListener` implementations to handle a
 * low-level input code like a keyboard key code.
 *
 * <p>
 * Typical implementations will translate the numeric `keyCode` into a
 * higher-level action or forward it to the input subsystem.
 */

@FunctionalInterface
public interface InputListenerStrategy {
    /**
     * Handle the provided input key code.
     *
     * @param keyCode numeric code identifying the input.
     */
    void handleInput(int keyCode);
}
