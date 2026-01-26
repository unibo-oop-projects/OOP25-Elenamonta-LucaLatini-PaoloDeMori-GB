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
 * Test for {@link Block}.
 */
class BlockTest {

    @Test
    void testCreation() {
        final int vectorStandardY = 200;
        final int vectorStandardX = 100;
        final Block block = new Block(new Vector2(vectorStandardX, vectorStandardY));
        assertEquals(new Vector2(vectorStandardX, vectorStandardY), block.getPosition());
        assertEquals(ObstacleType.BLOCK, block.getObstacleType());
        assertTrue(block.isActive());
        assertEquals("block", block.getObstacleType().getName());
    }

    @Test
    void testHitBoxIsSquare() {
        final Block block = new Block(new Vector2(0, 0));
        assertEquals(4, block.getHitBox().getVertices().size());
    }

    @Test
    void testHitBoxSize() {
        final Block block = new Block(new Vector2(0, 0));
        assertEquals(Block.SIZE, block.getHitBox().getHeight());
        assertEquals(Block.SIZE, block.getHitBox().getWidth());
    }

    @Test
    void testCopyCreatesNewInstance() {
        final Block block = new Block(new Vector2(50, 75));
        final Block copyBlock = block.copy();
        assertNotSame(block, copyBlock);
        assertEquals(block.getPosition(), copyBlock.getPosition());
        assertTrue(copyBlock.isActive());
    }

    @Test
    void testOnCollision() {
        final Block block = new Block(new Vector2(0, 0));
        final boolean[] player1Died = {false};
        final Player<?> playerSafe = new PlayerImpl(new Vector2(0, 0.9f)) {
            @Override
            public void die() {
                player1Died[0] = true;
            }
        };
        block.onCollision(playerSafe);
        assertFalse(player1Died[0]);

        final boolean[] player2Died = {false};
        final Player<?> playerDied = new PlayerImpl(new Vector2(0, 0.8f)) {
            @Override
            public void die() {
                player2Died[0] = true;
            }
        };
        block.onCollision(playerDied);
        assertTrue(player2Died[0]);
    }
}
