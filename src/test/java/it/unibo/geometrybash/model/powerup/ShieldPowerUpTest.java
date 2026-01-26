package it.unibo.geometrybash.model.powerup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;

/**
 * Test for {@link ShieldPowerUp}.
 */
class ShieldPowerUpTest {

    private static final Vector2 POS = new Vector2(10, 10);

    private ShieldPowerUp shieldPowerUp;

    @BeforeEach
    void setUp() {
        shieldPowerUp = new ShieldPowerUp(POS);
    }

    @Test
    void testCreation() {
        assertEquals(POS, shieldPowerUp.getPosition());
        assertEquals(PowerUpType.SHIELD, shieldPowerUp.getPowerUpType());
        assertTrue(shieldPowerUp.isActive());
    }

    @Test
    void testCopy() {
        final ShieldPowerUp copy = shieldPowerUp.copy();
        assertEquals(shieldPowerUp.getPosition(), copy.getPosition());
        assertTrue(copy.isActive());
    }

    @Test
    void testOnCollision() {
        final Player<?> player = new PlayerImpl(new Vector2(0, 0));
        assertFalse(player.isShielded());
        shieldPowerUp.onCollision(player);
        assertTrue(player.isShielded());
        assertFalse(shieldPowerUp.isActive());
    }
}
