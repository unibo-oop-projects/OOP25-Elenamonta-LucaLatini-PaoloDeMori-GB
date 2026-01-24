package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.junit.jupiter.api.Test;

import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.ObstacleFactory;
import it.unibo.geometrybash.model.obstacle.ObstacleType;
import it.unibo.geometrybash.model.physicsengine.BodyFactory;
import it.unibo.geometrybash.model.powerup.PowerUp;
import it.unibo.geometrybash.model.powerup.PowerUpFactory;
import it.unibo.geometrybash.model.powerup.PowerUpType;

class JBox2DPhysicsEngineImplTest {

    @Test
    void testBodyFactory() {
        final Vec2 gravity = new Vec2(0f, -9.8f);
        final BodyFactory<Body> bF = new BodyFactoryImpl(new World(gravity));

        final Obstacle oBs = ObstacleFactory.create(ObstacleType.SPIKE, new Vector2(1f, 1f));

        final Body spikeInPE = bF.createObstacle(oBs);
        assertSame(spikeInPE.getUserData(), oBs);

        final PowerUp<?> pU = PowerUpFactory.create(PowerUpType.COIN, new Vector2(1f, 1f));
        final Body coin = bF.createPowerUp(pU);
        assertSame(coin.getUserData(), pU);
    }

}
