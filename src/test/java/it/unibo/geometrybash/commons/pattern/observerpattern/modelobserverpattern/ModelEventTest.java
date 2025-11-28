package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEventType;

class ModelEventTest {
    private static final String UPDATE_NAME = "Update";
    private static final String GAMEOVER_NAME = "GameOver";

    @Test
    void testFactories() {
        assertEquals(ModelEvent.createGameOverEvent().getType(), ModelEventType.GAMEOVER);
        assertEquals(ModelEvent.createUpdateEvent().getType(), ModelEventType.UPDATE);
        assertEquals(ModelEvent.createGameOverEvent().getEventName(), GAMEOVER_NAME);
        assertEquals(ModelEvent.createUpdateEvent().getEventName(), UPDATE_NAME);

        assertNotEquals(ModelEvent.createGameOverEvent().getType(), ModelEvent.createUpdateEvent().getType());
    }
}
