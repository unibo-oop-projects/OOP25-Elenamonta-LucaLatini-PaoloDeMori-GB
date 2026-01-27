package it.unibo.geometrybash.model.core;

/**
 * Function called if a contact that modifies the state happens.
 */
@FunctionalInterface
public interface OnStateModifiedContact {
    /**
     * Activate the function.
     *
     * @param obj the object whose state changed
     */
    void activateObject(GameObject<?> obj);
}
