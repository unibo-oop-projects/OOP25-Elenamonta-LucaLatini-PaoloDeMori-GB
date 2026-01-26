package it.unibo.geometrybash.model.obstacle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;

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

    @Test
    void testOnCollision() {
        final Spike spike = new Spike(new Vector2(0, 0));
        final boolean[] player1Died = {false};
        final Player<?> playerWithNoShield = new PlayerImpl(new Vector2(0, 0)) {
            @Override
            public void die() {
                player1Died[0] = true;
            }

            @Override
            public boolean isShielded() {
                return false;
            }
        };

        spike.onCollision(playerWithNoShield);
        assertTrue(player1Died[0]);
        assertTrue(spike.isActive());

        final Spike spike2 = new Spike(new Vector2(0, 0));
        final boolean[] player2Died = {false};
        final Player<?> playerWithShield = new PlayerImpl(new Vector2(0, 0)) {
            @Override
            public void die() {
                player2Died[0] = true;
            }

            @Override
            public boolean isShielded() {
                return true;
            }

            @Override
            public void onSpikeCollision(final Spike s) {
                if (this.isShielded()) {
                    s.setActive(false);
                }
            }
        };

        spike2.onCollision(playerWithShield);
        assertFalse(player2Died[0]);
        assertFalse(spike2.isActive());
    }
}
