package it.unibo.geometrybash.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;
import it.unibo.geometrybash.model.powerup.Coin;
import it.unibo.geometrybash.model.powerup.ShieldPowerUp;

class PlayerImplTest {

    private static final float INIT_POSITION = 5.0f;
    private static final double TWO_PI = Math.PI * 2.0;
    private static final float SIZE = 1.0f;
    private static final float DT = 1 / 60f;
    private static final double EPS = 1e-6;
    private static final int NUMBER_VERTICES_SQUARE = 4;
    private static final int NUMBER_VERTICES_EXAGON = 6;
    private static final int[] POLYGONS = {3, 4, 5, 6, 8, 12 };
    private PlayerImpl player;
    private MockPlayerPhysics physics;
    private HitBox hitBox;
    private double stepAngle;

    @BeforeEach
    void initStandardPlayer() {
        createPlayerWithGenericPolygon(NUMBER_VERTICES_SQUARE);
    }

    private void createPlayerWithGenericPolygon(final int numOfVertices) {
        this.hitBox = regularPolygonHitBox(numOfVertices);
        this.physics = new MockPlayerPhysics();
        this.player = new PlayerImpl(this.physics.getPosition(this.hitBox), this.hitBox);
        this.player.bindPhysics(physics);
        this.player.setOnDeath(() -> {
        });
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.stepAngle = TWO_PI / this.hitBox.getVertices().size();
    }

    /**
     * Support function to create a regular polygon with a given number of vertices
     * and size.
     *
     * @param n number of vertices of the polygon
     * @return representing a regular polygon
     */
    private static HitBox regularPolygonHitBox(final int n) {
        final var vertices = IntStream.range(0, n)
                .mapToObj(i -> {
                    final double a = 2.0 * Math.PI * i / n;
                    return new Vector2((float) (SIZE * Math.cos(a)), (float) (SIZE * Math.sin(a)));
                })
                .toList();
        return new HitBox(vertices);
    }

    @Test
    void testBindPhysicsOnlyOnce() {
        assertThrows(IllegalStateException.class, () -> player.bindPhysics(new MockPlayerPhysics()));
    }

    @Test
    void testBindPhysicsNullThrows() {
        final PlayerImpl p = new PlayerImpl(new Vector2(INIT_POSITION, INIT_POSITION));
        assertThrows(NullPointerException.class, () -> p.bindPhysics(null));
    }

    @Test
    void testSnapsWhenPlayerIsOnAir() {
        for (final int n : POLYGONS) {
            createPlayerWithGenericPolygon(n);
            this.physics.setGrounded(false);
            final double before = this.player.getAngularRotation();
            this.player.update(DT);
            final double after = this.player.getAngularRotation();
            final double expectedDelta = Math.toRadians(720.0) * DT;
            assertEquals(before + expectedDelta, after, EPS, "Failed for n=" + n);
        }
    }

    @Test
    void testRotationSnapsToNearestStepOnGround() {
        for (final int n : POLYGONS) {
            createPlayerWithGenericPolygon(n);
            this.physics.setGrounded(false);
            // Drive the rotation to a value strictly greater than half of stepAngle,
            // ensuring that the nearest-multiple snapping selects the upper multiple.
            final float dtToHalfStep = (float) (0.6 * this.stepAngle / Math.toRadians(720.0));
            this.player.update(dtToHalfStep);
            this.physics.setGrounded(true);
            this.player.update(0.0f);
            final double angle = this.player.getAngularRotation();
            final double k = Math.rint(angle / this.stepAngle);
            final double snapped = k * this.stepAngle;
            assertEquals(snapped, angle, EPS, "Failed for n=" + n);
        }
    }

    @Test
    void testGroundedRotationWhenIsInPlaying() {
        for (final int n : POLYGONS) {
            createPlayerWithGenericPolygon(n);
            this.physics.setGrounded(false);
            final double omega = Math.toRadians(720.0);
            final float dtToUnaligned = (float) (0.6 * this.stepAngle / omega);
            this.player.update(dtToUnaligned);
            this.physics.setGrounded(true);
            this.player.update(DT);
            final double snappedAngle = this.player.getAngularRotation();
            for (int i = 0; i < 10; i++) {
                this.player.update(DT);
                assertEquals(snappedAngle, this.player.getAngularRotation(), EPS);
            }
            final double k = Math.rint(snappedAngle / this.stepAngle);
            assertEquals(k * this.stepAngle, snappedAngle, EPS);
        }
    }

    @Test
    void testAngularRotationIsAlwaysNormalized() {
        createPlayerWithGenericPolygon(NUMBER_VERTICES_EXAGON);
        physics.setGrounded(false);
        player.update(2.0f);
        final double angle = player.getAngularRotation();
        assertTrue(angle >= 0 && angle < TWO_PI);
    }

    @Test
    void testRespawnAfterDeath() {
        this.player.setOnDeath(() -> {
        });
        this.player.die();
        final Vector2 newPos = new Vector2(INIT_POSITION, INIT_POSITION);
        this.player.respawn(newPos);
        assertFalse(player.isDead());
        assertEquals(newPos, this.physics.getPosition(hitBox));
    }

    @Test
    void testResetPowerUpAndCoinsWhenPlayerDie() {
        final GameObject<?> coin = new Coin(new Vector2(INIT_POSITION, INIT_POSITION));
        player.addCoin(coin, 10);
        player.die();
        assertEquals(0, player.getCoins());
        assertTrue(player.isDead());
    }

    @Test
    void testDieCallsOnDeathCallback() {
        final boolean[] called = {false};
        player.setOnDeath(() -> called[0] = true);
        player.die();
        assertTrue(called[0]);
    }

    @Test
    void testOnSpecialObjectCollisionIsCalledOnShield() {
        final int[] called = {0};
        player.setOnSpecialObjectCollision(o -> called[0]++);
        final GameObject<?> shield = new ShieldPowerUp(new Vector2(INIT_POSITION, INIT_POSITION));
        player.onShieldCollected(shield);
        assertEquals(1, called[0]);
    }

    final class MockPlayerPhysics implements PlayerPhysics {

        private static final String NOT_NECESSARY_METHOD = "this method is not tested here";
        private static final float BASE_SPEED = 5.0f;
        private Vector2 velocity;
        private boolean grounded;
        private Vector2 position;

        MockPlayerPhysics() {
            this.velocity = new Vector2(BASE_SPEED, 0f);
            this.grounded = true;
            this.position = new Vector2(0f, 0f);
        }

        public void setGrounded(final boolean grounded) {
            this.grounded = grounded;
        }

        public void setPosition(final Vector2 newPosition) {
            this.position = newPosition;
        }

        @Override
        public void applyJump() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void setVelocity(final float multiplier) {
            this.velocity = new Vector2(BASE_SPEED * multiplier, velocity.y());
        }

        @Override
        public Vector2 getVelocity() {
            return velocity;
        }

        @Override
        public void resetBodyTo(final Vector2 pos) {
            this.position = pos;
        }

        @Override
        public Vector2 getPosition(final HitBox hB) {
            return this.position;
        }

        @Override
        public void setUserData(final Object userData) {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public boolean isGrounded() {
            return this.grounded;
        }

        @Override
        public void onGroundContactBegin() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void onGroundContactEnd() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        @Override
        public void setActive(final boolean activeState) {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }
    }
}
