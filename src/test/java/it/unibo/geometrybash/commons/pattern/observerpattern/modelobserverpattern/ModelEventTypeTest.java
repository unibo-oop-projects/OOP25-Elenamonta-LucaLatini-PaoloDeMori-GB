package it.unibo.geometrybash.commons.pattern.observerpattern.modelobserverpattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.commons.pattern.observerpattern.modelobserver.ModelEventType;

class ModelEventTypeTest {
    private static final String UPDATE_NAME = "Update";
    private static final String GAMEOVER_NAME = "GameOver";

    /*
        Check that the name is set and get  correctly
    */
    @Test
    void testGetName() {
        assertEquals(ModelEventType.UPDATE.getName(), UPDATE_NAME);
        assertEquals(ModelEventType.GAMEOVER.getName(), GAMEOVER_NAME);
        assertNotEquals(ModelEventType.GAMEOVER.getName(), UPDATE_NAME); //check that the names are different
    }
}
