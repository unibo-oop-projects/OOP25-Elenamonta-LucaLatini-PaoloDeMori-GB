package it.unibo.geometrybash.model.geometry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestHitBox {

    private static final int STD_WIDTH = 10;
    private static final int STD_HEIGHT = 5;
    private static final int NEGATIVE_WIDTH = 10;
    private static final int NEGATIVE_HEIGHT = 10;
    private static final int ZERO = 0;

    private HitBox stdHitBox;
    private HitBox negativeHitBox;
    private HitBox sameCoordHitBox;

    @BeforeEach
    void setUpCases() {
        // CHECKSTYLE: MagicNumber OFF
        // Suppressing Checkstyle magic number warnings here because these numbers are
        // concrete test examples.
        stdHitBox = new HitBox(List.of(
                new Vector2(0, 0),
                new Vector2(10, 0),
                new Vector2(10, 5)));

        negativeHitBox = new HitBox(List.of(
                new Vector2(-10, -5),
                new Vector2(0, -5),
                new Vector2(0, 5)));

        sameCoordHitBox = new HitBox(List.of(
                new Vector2(3, 3),
                new Vector2(3, 3),
                new Vector2(3, 3)));
        // CHECKSTYLE: MagicNumber ON
    }

    @Test
    void testConstructorException() {
        assertThrows(IllegalArgumentException.class, () -> new HitBox(List.of(
                new Vector2(0, 0))));
    }

    @Test
    void standardHitBoxComputesCorrectWidth() {
        assertEquals(STD_WIDTH, stdHitBox.getWidth());
    }

    @Test
    void standardHitBoxComputesCorrectHeight() {
        assertEquals(STD_HEIGHT, stdHitBox.getHeight());
    }

    @Test
    void negativeHitBoxComputesCorrectWidth() {
        assertEquals(NEGATIVE_WIDTH, negativeHitBox.getWidth());
    }

    @Test
    void negativeHitBoxComputesCorrectHeight() {
        assertEquals(NEGATIVE_HEIGHT, negativeHitBox.getHeight());
    }

    @Test
    void sameHitBoxComputesCorrectWidth() {
        assertEquals(ZERO, sameCoordHitBox.getWidth());
    }

    @Test
    void sameHitBoxComputesCorrectHeight() {
        assertEquals(ZERO, sameCoordHitBox.getHeight());
    }
}
