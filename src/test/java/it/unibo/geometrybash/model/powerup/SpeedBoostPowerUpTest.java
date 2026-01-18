package it.unibo.geometrybash.model.powerup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Test for {@link SpeedBoostPowerUp}.
 */
class SpeedBoostPowerUpTest {

    @Test
    void testCreation() {
        final int vectorStandardY = 200;
        final int vectorStandardX = 100;
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(
                new Vector2(vectorStandardX, vectorStandardY));
        assertEquals(new Vector2(vectorStandardX, vectorStandardY), speedBoostPowerUp.getPosition());
        assertEquals(PowerUpType.SPEED_BOOST, speedBoostPowerUp.getPowerUpType());
        assertTrue(speedBoostPowerUp.isActive());
        assertEquals("speedboost", speedBoostPowerUp.getPowerUpType().getName());
    }

    @Test
    void testIsTemporary() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(0, 0));
        assertTrue(speedBoostPowerUp.isTemporary());
    }

    @Test
    void testDuration() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(0, 0));
        assertEquals(3.0f, speedBoostPowerUp.getDuration());
    }

    @Test
    void testHitBoxIsSquare() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(0, 0));
        assertEquals(4, speedBoostPowerUp.getHitBox().getVertices().size());
    }

    @Test
    void testHitBoxSize() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(0, 0));
        assertEquals(SpeedBoostPowerUp.SIZE, speedBoostPowerUp.getHitBox().getHeight());
        assertEquals(SpeedBoostPowerUp.SIZE, speedBoostPowerUp.getHitBox().getWidth());
    }

    @Test
    void testCopyCreatesNewInstance() {
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(50, 75));
        final SpeedBoostPowerUp copySpeedBoostPowerUp = speedBoostPowerUp.copy();
        assertNotSame(speedBoostPowerUp, copySpeedBoostPowerUp);
        assertEquals(speedBoostPowerUp.getPosition(), copySpeedBoostPowerUp.getPosition());
        assertTrue(copySpeedBoostPowerUp.isActive());
    }

    @Test
    void testGetMultiplier() {
        final float speed = 1.5f;
        final SpeedBoostPowerUp speedBoostPowerUp = new SpeedBoostPowerUp(new Vector2(0, 0));
        assertEquals(speed, speedBoostPowerUp.getMultiplier());
    }
}
