package it.unibo.geometrybash.commons.pattern.observerpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObservable;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelObserver;

/**
 * AbstractObservableWithSet Test.
 */
class TestAbstractObservableWithSet {
    private final ModelEvent testGameOverEvent = ModelEvent.createGameOverEvent();
    private ModelEvent testEventVar = ModelEvent.createVictoryEvent();

    /**
     * Test AbstractObservableWithSetTest using {@link ModelObserver}.
     */
    @Test 
    void testWithModel() {
        final ModelObserver obs = (ModelEvent e) -> {
            testEventVar = e;
        };
        final ModelObservableImplementation moi = new ModelObservableImplementation();
        moi.addObserver(obs);
        assertNotEquals(testGameOverEvent, testEventVar);
        moi.notifyObservers(testGameOverEvent);
        assertEquals(testGameOverEvent, testEventVar);
    }

    class ModelObservableImplementation extends AbstractObservableWithSet<ModelEvent> implements ModelObservable {
    }
}
