package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.BodyFactory;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;

class PlayerPhysicsTest {

    private static final float EPS = 1e-5f;
    private Player<HitBox> player;
    private Body playerBody;
    private PlayerPhysicsImpl physics;

    @BeforeEach
    void initPhysics() {
        this.player = new PlayerImpl(new Vector2(0f, 0f));
        final World world = new World(new Vec2(JBox2DValues.GRAVITY.x(), JBox2DValues.GRAVITY.y()));
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        this.playerBody = bF.createPlayer(player);
        this.physics = new PlayerPhysicsImpl(playerBody);
    }

    @Test
    void testThrowsWhenBodyIsNull() {
        assertThrows(NullPointerException.class, () -> new MockPlayerPhysicsImpl(null));
    }

    @Test
    void testVelocityWhenPlayerJump() {
        this.playerBody.setLinearVelocity(new Vec2(JBox2DValues.BASE_SPEED, 0f));
        final Vec2 velocityBeforeJump = playerBody.getLinearVelocity().clone();
        this.physics.applyJump();
        final Vec2 velocityAfterJump = playerBody.getLinearVelocity();
        assertEquals(velocityBeforeJump.x, velocityAfterJump.x, EPS);
        assertEquals(velocityBeforeJump.y, velocityAfterJump.y, EPS);
    }

    @Test
    void testImpulseWhenPlayerJump() {
        this.physics.onGroundContactBegin();
        this.playerBody.setLinearVelocity(new Vec2(JBox2DValues.BASE_SPEED, 0f));
        physics.applyJump();
        final Vec2 velocityAfterJump = playerBody.getLinearVelocity();
        assertEquals(JBox2DValues.BASE_SPEED, velocityAfterJump.x, EPS);
        assertTrue(velocityAfterJump.y > 0f);
    }

    @Test
    void testCorrectlyResetBodyPosition() {
        final Vec2 initPosition = new Vec2(0f, 0f);
        final Vec2 initVelocity = new Vec2(JBox2DValues.BASE_SPEED, 0f);
        this.physics.resetBodyTo(new Vector2(initPosition.x, initPosition.y));
        assertEquals(initPosition, this.playerBody.getPosition());
        assertEquals(initVelocity, this.playerBody.getLinearVelocity());
    }

    @Test
    void testPlayerWoldCoordinate() {
        final float xCoord = 5.0f;
        final float yCoord = 8.0f;
        this.physics.resetBodyTo(new Vector2(xCoord, yCoord));
        final Vec2 worldPosition = this.playerBody.getPosition();
        final Vector2 gamePosition = this.physics.getPosition(this.player.getHitBox());
        final float halfWidth = player.getHitBox().getWidth() / 2f;
        final float halfHeight = player.getHitBox().getHeight() / 2f;
        assertEquals(worldPosition.x, gamePosition.x() + halfWidth);
        assertEquals(worldPosition.y, gamePosition.y() + halfHeight);

    }

    @Test
    void testUserData() {
        this.physics.setUserData(player);
        assertSame(playerBody.getUserData(), player);
    }

    final class MockPlayerPhysicsImpl extends PlayerPhysicsImpl {
        MockPlayerPhysicsImpl(final Body body) {
            super(body);
        }
    }
}
