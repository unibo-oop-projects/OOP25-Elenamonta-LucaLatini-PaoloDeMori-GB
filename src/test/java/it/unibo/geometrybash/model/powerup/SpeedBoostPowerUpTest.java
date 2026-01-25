package it.unibo.geometrybash.model.powerup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;

/**
 * Test for {@link SpeedBoostPowerUp}.
 */
class SpeedBoostPowerUpTest {

    private static final Vector2 POS = new Vector2(10, 10);

    @Test
    void testCreation() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(POS);
        assertEquals(POS, speedBoostPowerUp.getPosition());
        assertEquals(PowerUpType.SPEED_BOOST, speedBoostPowerUp.getPowerUpType());
        assertEquals(SpeedBoostPowerUp.MULTIPLIER, speedBoostPowerUp.getMultiplier());
        assertTrue(speedBoostPowerUp.isActive());
    }

    @Test
    void testCopy() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(POS);
        final SpeedBoostPowerUp copy = speedBoostPowerUp.copy();
        assertEquals(speedBoostPowerUp.getPosition(), copy.getPosition());
        assertTrue(copy.isActive());
    }

    @Test
    void testOnCollision() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(POS);
        final Player<?> player = new PlayerImpl(POS, null);
        assertEquals(1.0f, player.getSpeedMultiplier());
        speedBoostPowerUp.onCollision(player);
        assertEquals(SpeedBoostPowerUp.MULTIPLIER, player.getSpeedMultiplier());
        assertFalse(speedBoostPowerUp.isActive());
    }
}
