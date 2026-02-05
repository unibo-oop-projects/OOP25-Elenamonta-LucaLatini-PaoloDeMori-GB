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
 * Test for {@link SpeedBoostPowerUp}.
 */
class SpeedBoostPowerUpTest {

    private static final Vector2 POS = new Vector2(10, 10);

    private SpeedBoostPowerUp speedBoostPowerUp;

    @BeforeEach
    void setUp() {
        speedBoostPowerUp = new SpeedBoostPowerUp(POS);
    }

    @Test
    void testCreation() {
        assertEquals(POS, speedBoostPowerUp.getPosition());
        assertEquals(PowerUpType.SPEED_BOOST, speedBoostPowerUp.getPowerUpType());
        assertEquals(SpeedBoostPowerUp.MULTIPLIER, speedBoostPowerUp.getMultiplier());
        assertTrue(speedBoostPowerUp.isActive());
    }

    @Test
    void testCopy() {
        final SpeedBoostPowerUp copy = speedBoostPowerUp.copy();
        assertEquals(speedBoostPowerUp.getPosition(), copy.getPosition());
        assertTrue(copy.isActive());
    }

    @Test
    void testOnCollision() {
        final Player<?> player = new PlayerImpl(POS);
        player.setOnSpecialObjectCollision(obj -> {
        });
        assertEquals(1.0f, player.getSpeedMultiplier());
        speedBoostPowerUp.onCollision(player);
        assertEquals(SpeedBoostPowerUp.MULTIPLIER, player.getSpeedMultiplier());
        assertFalse(speedBoostPowerUp.isActive());
    }
}
