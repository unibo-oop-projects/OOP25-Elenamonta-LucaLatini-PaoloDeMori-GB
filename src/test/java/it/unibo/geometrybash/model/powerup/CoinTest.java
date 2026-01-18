package it.unibo.geometrybash.model.powerup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * Test for {@link Coin}.
 */
class CoinTest {

    @Test
    void testCreation() {
        final int vectorStandardY = 200;
        final int vectorStandardX = 100;
        final Coin coin = new Coin(new Vector2(vectorStandardX, vectorStandardY));
        assertEquals(new Vector2(vectorStandardX, vectorStandardY), coin.getPosition());
        assertEquals(PowerUpType.COIN, coin.getPowerUpType());
        assertTrue(coin.isActive());
        assertEquals("coin", coin.getPowerUpType().getName());
    }

    @Test
    void testIsTemporary() {
        final Coin coin = new Coin(new Vector2(0, 0));
        assertFalse(coin.isTemporary());
    }

    @Test
    void testGetValue() {
        final Coin coin = new Coin(new Vector2(0, 0));
        assertEquals(1, coin.getValue());
    }

    @Test
    void testHitBoxIsSquare() {
        final Coin coin = new Coin(new Vector2(0, 0));
        assertEquals(4, coin.getHitBox().getVertices().size());
    }

    @Test
    void testHitBoxSize() {
        final Coin coin = new Coin(new Vector2(0, 0));
        assertEquals(Coin.SIZE, coin.getHitBox().getHeight());
        assertEquals(Coin.SIZE, coin.getHitBox().getWidth());
    }

    @Test
    void testCopyCreatesNewInstance() {
        final Coin coin = new Coin(new Vector2(50, 75));
        final Coin copyCoin = coin.copy();
        assertNotSame(coin, copyCoin);
        assertEquals(coin.getPosition(), copyCoin.getPosition());
        assertTrue(copyCoin.isActive());
    }

}
