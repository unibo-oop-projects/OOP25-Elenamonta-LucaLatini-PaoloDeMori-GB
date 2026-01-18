package it.unibo.geometrybash.view.userinteraction;

/**
 * Listener that binds input strategies to UI interaction points.
 *
 * <p>Clients register strategies to customize how input from the view is
 * handled.
 *
 * @see InputListenerStrategy
 */
public interface InputListener {
    /**
     * Set the strategy to execute when a press event is detected.
     *
     * @param strategy the strategy that will handle click input.
     */
    void setOnClickStrategy(InputListenerStrategy strategy);

    /**
     * Set the strategy to execute when a release event is detected.
     *
     * @param strategy the strategy that will handle release input.
     */
    void setOnReleasedStrategy(InputListenerStrategy strategy);
}
