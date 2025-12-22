package it.unibo.geometrybash.model.core;

/**
 * The {@code update} method is responsible for handling game logic.
 *
 * <p>
 * Gameplay updates are conceptually separated from simulation: {@code Updatable} objects
 * express intentions, react to the outcomes of the simulation, and maintain a clear
 * boundary between game logic and physical processing.
 * </p>
 */
@FunctionalInterface
public interface Updatable {

    /**
     * Updates the logical state of the object for the current frame.
     *
     * @param deltaTime the elapsed time since the previous frame, in seconds
     */
    void update(float deltaTime);
}
