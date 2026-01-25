package it.unibo.geometrybash.model.player;

import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;

/**
 * Marks a game entity that can be bound to a physics component.
 *
 * <p>
 * This interface allows the physical representation of a Player
 * to be bound to its logical model without exposing physics-engine-specific
 * details within the model layer.
 * </p>
 */
@FunctionalInterface
public interface Bindable {
    /**
     * Binds the given physics component to this entity.
     *
     * @param physics the physics component associated with this entity
     * @throws IllegalStateException if the entity is already bound to a physics component
     * @throws NullPointerException  if {@code physics} is {@code null}
     */
    void bindPhysics(PlayerPhysics physics);
}
