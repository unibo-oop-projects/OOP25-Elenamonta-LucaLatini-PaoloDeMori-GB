package it.unibo.geometrybash.model.obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Tests for {@link Spike}.
 */
class SpikeTest {

    @Test
    void testCreation() {
        final int vectorStandardY = 200;
        final int vectorStandardX = 100;
        final Spike spike = new Spike(new Vector2(vectorStandardX, vectorStandardY));
        assertEquals(new Vector2(vectorStandardX, vectorStandardY), spike.getPosition());
        assertEquals(ObstacleType.SPIKE, spike.getObstacleType());
        assertTrue(spike.isActive());
        assertEquals("spike", spike.getObstacleType().getName());
    }

    @Test
    void testIsDeadly() {
        final Spike spike = new Spike(new Vector2(0, 0));
        assertTrue(spike.isDeadly());
    }

    @Test
    void testHitBoxIsTriangle() {
        final Spike spike = new Spike(new Vector2(0, 0));
        assertEquals(3, spike.getHitBox().getVertices().size());
    }

    @Test
    void testHitBoxSize() {
        final Spike spike = new Spike(new Vector2(0, 0));
        assertEquals(Spike.SIZE, spike.getHitBox().getHeight());
        assertEquals(Spike.SIZE, spike.getHitBox().getWidth());
    }

    @Test
    void testCopyCreatesNewInstance() {
        final Spike spike = new Spike(new Vector2(50, 75));
        final Spike copySpike = spike.copy();
        assertNotSame(spike, copySpike);
        assertEquals(spike.getPosition(), copySpike.getPosition());
        assertTrue(copySpike.isActive());
    }
}
