package it.unibo.geometrybash.model.player;

/**
 * Function called if the player dies.
 */
@FunctionalInterface
public interface OnDeathExecute {
    /**
     * Method called if the player dies.
     */
    void onDeath();
}
