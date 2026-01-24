package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEvent;
import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEventType;

class ModelEventTest {
    private static final String VICTORY_NAME = "Victory";
    private static final String GAMEOVER_NAME = "GameOver";

    @Test
    void testFactories() {
        assertEquals(ModelEvent.createGameOverEvent().getType(), ModelEventType.GAMEOVER);
        assertEquals(ModelEvent.createVictoryEvent().getType(), ModelEventType.VICTORY);
        assertEquals(ModelEvent.createGameOverEvent().getEventName(), GAMEOVER_NAME);
        assertEquals(ModelEvent.createVictoryEvent().getEventName(), VICTORY_NAME);

        assertNotEquals(ModelEvent.createGameOverEvent().getType(), ModelEvent.createVictoryEvent().getType());
    }
}
