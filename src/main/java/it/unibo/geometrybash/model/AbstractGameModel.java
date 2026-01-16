package it.unibo.geometrybash.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import it.unibo.geometrybash.model.core.Updatable;

/**
 * An abstract implementation of the gamemode that works by updating a list of
 * updatables.
 * 
 * <p>
 * This Abstract Implementation of the gamemodel builds a framework to create a
 * gamemodel that updates updatable gameobjects and after that,
 * executes a custom action.
 */
public abstract class AbstractGameModel implements GameModel {
    /**
     * The list of updatables gameobjects to update.
     */
    private final List<Updatable> updatables = new LinkedList<>();
    /**
     * The list of elements to add,necessary to make the adding process safe.
     * Without this structure if during an update a client adds an element in the
     * updatables list,
     * it will lead to a crash.
     */
    private final List<Updatable> toAdd = new LinkedList<>();
    /**
     * The list of elements to remove,necessary to make the adding process safe.
     * Without this structure if during an update a client removes an element in the
     * updatables list,
     * it will lead to a crash.
     */
    private final List<Updatable> toRemove = new LinkedList<>();

    /**
     * The constructor of this class.
     * 
     * <p>
     * This constructor accepts a list of updatables and add them to the updatables
     * list.
     * 
     * @param updatables the updatable game objects to add
     */
    public AbstractGameModel(final List<Updatable> updatables) {
        if (updatables != null) {
            this.updatables.addAll(updatables);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * This implementation when an update request is sent to the gamemodel,
     * updates all the updatables in the list and executes a custom set of actions
     * after that.
     * Since if a list is updated while it is being iterated an error is thrown,
     * this class handle the addition and the removal with to additional lists.
     */
    @Override
    public final void update(final float deltaTime) {

        this.updatables.addAll(toAdd);
        toAdd.clear();
        this.updatables.removeAll(toRemove);
        toRemove.clear();

        if (isUpdatable()) {
                if (!updatables.isEmpty()) {
                    updatables.forEach(i -> i.update(deltaTime));
                }
                this.afterGameObjectsUpdate();
        }
    }

    /**
     * The actions to implement after updating all the updatables.
     */
    protected abstract void afterGameObjectsUpdate();

    /**
     * Add an updatable game object to the list of updatables to update when an
     * update signal is received.
     * 
     * <p>
     * This implementation uses an additional list for safe addition.
     * 
     * @param updatable The updatable to add.
     */
    protected void addUpdatableGameObjects(final Updatable updatable) {
        this.toAdd.add(updatable);
    }

    /**
     * Remove an updatable game object to the list of updatables to update when an
     * update signal is received.
     * 
     * <p>
     * This implementation uses an additional list for safe removal.
     * 
     * @param updatable The updatable to remove.
     */
    protected void removeUpdatableGameObjects(final Updatable updatable) {
        this.toRemove.add(updatable);
    }

    /**
     * A method that represents if it is possible to update the model.
     * 
     * @return true if it's possible to update the model, false otherwise.
     */
    protected abstract boolean isUpdatable();

    /**
     * Removes all the elements in the list that contains the updatables. 
     */
    protected void clearUpdatableList() {
        this.updatables.clear();
    }

    /**
     * Removes all the elements in the lists that contain the updatables waiting to be removed or to be added. 
     */
    protected void clearToLists() {
        this.clearToAddList();
        this.clearToRemoveList();
    }

    /**
     * Returns an unmodifiable representation of the updatables list.
     * 
     * @return An unmodifiable list containing the updatables.
     */
    protected List<Updatable> getUpdatables() {
        return Collections.unmodifiableList(updatables);
    }

    /**
     * Removes all the elements in the lists that contain the updatables to waiting to be added. 
     */
    private void clearToAddList() {
        this.toAdd.clear();
    }

    /**
     * Removes all the elements in the lists that contain the updatables to waiting to be removed. 
     */
    private void clearToRemoveList() {
        this.toRemove.clear();
    }
}
