package it.unibo.geometrybash.view;

/**
 * Interface for view classes that updates using dtos.
 * 
 * @param <T> the type of the dto used to update the class.
 */
@FunctionalInterface
public interface UpdatableWithDto<T> {

    /**
     * Update the class.
     * 
     * @param gameStateDto the dto containing the new informations to update the
     *                     class.
     */
    void update(T gameStateDto);

}
