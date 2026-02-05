package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.core.Collidable;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;
import it.unibo.geometrybash.model.physicsengine.BodyFactory;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.player.PlayerImpl;
import it.unibo.geometrybash.model.player.PlayerWithPhysics;
import it.unibo.geometrybash.model.powerup.PowerUp;
import it.unibo.geometrybash.model.powerup.PowerUpFactory;
import it.unibo.geometrybash.model.powerup.PowerUpType;

class CollisionHandlerTest {

    private static final float DT = 1f / 60f;
    private World world;
    private PlayerWithPhysics player;
    private Vector2 initPosition;
    private MockPlayerPhysics physics;

    @BeforeEach
    void initCollision() {
        this.physics = new MockPlayerPhysics();
        this.world = new World(new Vec2(JBox2DValues.GRAVITY.x(), JBox2DValues.GRAVITY.y()));
        final CollisionHandler handler = new CollisionHandler();
        world.setContactListener(handler);
        this.initPosition = new Vector2(0f, 0f);
        this.player = new PlayerImpl(initPosition);
        this.player.bindPhysics(this.physics);
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        bF.createPlayer(player);
    }

    private Body createBody(final Vector2 pos, final GameObject<?> data) {
        final BodyDef bDef = new BodyDef();
        bDef.type = BodyType.STATIC;
        bDef.position.set(pos.x(), pos.y());
        final Body body = this.world.createBody(bDef);
        body.setUserData(data);
        final Shape hB = data.getHitBox();
        final PolygonShape shape = new PolygonShape();
        shape.setAsBox(hB.getWidth() / 2f, hB.getHeight() / 2f);
        final FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0f;
        fDef.friction = 0f;
        fDef.restitution = 0f;
        body.createFixture(fDef);
        return body;
    }

    @Test
    void testBeginContact() {
        final MockCollidableObject collidable = new MockCollidableObject(initPosition);
        createBody(initPosition, collidable);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertTrue(collidable.hasCollided());
    }

    @Test
    void testEndContact() {
        final MockCollidableObject collidable = new MockCollidableObject(initPosition);
        createBody(new Vector2(100f, 100f), collidable);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertFalse(collidable.hasCollided());
    }

    @Test
    void testCollisionWithCoin() {
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        final PowerUp<?> coin = PowerUpFactory.create(PowerUpType.COIN, initPosition);
        bF.createPowerUp(coin);
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertEquals(player.getCoins(), 1);
    }

    @Test
    void testCollisionWithSpeedBoost() {
        final float initialMultiplied = player.getSpeedMultiplier();
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        final PowerUp<?> speedBoost = PowerUpFactory.create(PowerUpType.SPEED_BOOST, initPosition);
        bF.createPowerUp(speedBoost);
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertTrue(player.getSpeedMultiplier() > 1.0f);
        assertNotEquals(player.getSpeedMultiplier(), initialMultiplied);
    }

    @Test
    void testCollisionWithShield() {
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.player.setOnDeath(() -> {
        });
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        final PowerUp<?> shield = PowerUpFactory.create(PowerUpType.SHIELD, initPosition);
        bF.createPowerUp(shield);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);

        final Obstacle spike = ObstacleFactory.create(ObstacleType.SPIKE, initPosition);
        bF.createObstacle(spike);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertFalse(this.player.isDead());
    }

    @Test
    void testCollisionWithSpike() {
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.player.setOnDeath(() -> {
        });
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        final Obstacle spike = ObstacleFactory.create(ObstacleType.SPIKE, initPosition);
        bF.createObstacle(spike);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        assertTrue(this.player.isDead());
    }

    @Test
    void testCollisionWithBlock() {
        this.player.setOnSpecialObjectCollision(obj -> {
        });
        this.player.setOnDeath(() -> {
        });
        final int contactBeforeCollision = this.physics.getGroundContacts();
        final BodyFactory<Body> bF = new BodyFactoryImpl(world);
        final Obstacle block = ObstacleFactory.create(ObstacleType.BLOCK, initPosition);
        bF.createObstacle(block);
        this.world.step(DT, JBox2DValues.VELOCITY_ITERATIONS, JBox2DValues.POSITION_ITERATIONS);
        final int expectedContactAfterCollision = contactBeforeCollision + 1;
        assertEquals(expectedContactAfterCollision, this.physics.getGroundContacts());
    }

    final class MockCollidableObject extends AbstractGameObject<Shape> implements Collidable {

        private static final String NOT_NECESSARY_METHOD = "this method is not tested here";
        private boolean isContact;

        protected MockCollidableObject(final Vector2 position) {
            super(position);
            this.hitBox = new HitBox(new ArrayList<>(List.of(
                    new Vector2(0, 0),
                    new Vector2(1, 0),
                    new Vector2(1, 1))));
            this.isContact = false;
        }

        @Override
        public void onCollision(final Player<?> collidingPlayer) {
            this.isContact = true;
        }

        @Override
        public GameObject<Shape> copy() {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        public boolean hasCollided() {
            return this.isContact;
        }
    }

    final class MockPlayerPhysics implements PlayerPhysics {

        private static final String NOT_NECESSARY_METHOD = "this method is not tested here";
        private static final float BASE_SPEED = 5.0f;
        private Vector2 velocity;
        private boolean grounded;
        private Vector2 position;
        private int groundContacts;

        MockPlayerPhysics() {
            this.velocity = new Vector2(BASE_SPEED, 0f);
            this.grounded = true;
            this.position = initPosition;
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
            this.groundContacts++;
        }

        @Override
        public void onGroundContactEnd() {
            this.groundContacts = Math.max(0, this.groundContacts - 1);
        }

        @Override
        public void setActive(final boolean activeState) {
            throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
        }

        public int getGroundContacts() {
            return this.groundContacts;
        }
    }
}
