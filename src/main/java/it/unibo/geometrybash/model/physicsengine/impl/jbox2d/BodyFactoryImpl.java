package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.CircleHitBox;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Shape;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.obstacle.Obstacle;
import it.unibo.geometrybash.model.obstacle.Spike;
import it.unibo.geometrybash.model.physicsengine.BodyFactory;
import it.unibo.geometrybash.model.physicsengine.exception.InvalidPhysicsEngineConfiguration;
import it.unibo.geometrybash.model.player.Player;
import it.unibo.geometrybash.model.powerup.PowerUp;

/**
 * A {@link BodyFactory} implementation.
 */
public class BodyFactoryImpl implements BodyFactory<Body> {
    /**
     * The default friction value.
     */
    private static final float STANDARD_FRICTION = JBox2DValues.STANDARD_FRICTION;
    /**
     * The default restitution value.
     */
    private static final float STANDARD_RESTITUTION = JBox2DValues.STANDARD_RESTITUTION;

    private static final float PLAYER_STANDARD_DENSITY = JBox2DValues.PLAYER_STANDARD_DENSITY;

    private static final float PLAYER_STANDARD_FRICTION = JBox2DValues.PLAYER_STANDARD_FRICTION;

    private final World world;

    /**
     * The constructor of this class.
     * 
     * @param world the {@link World} used to create the bodies
     */
    protected BodyFactoryImpl(final World world) {
        this.world = world;
    }

    /**
     * A private method to convert the position of a polygonal object from the
     * bottom left vertex to the geometric center.
     * 
     * @param leftCenter the model version of the position representing the bottom
     *                   left vertex.
     * @param hB         the hitbox of the geometric figure.
     * @return the geometric center.
     */
    private Vector2 modelCenterToJBox2dCenterConverter(final Vector2 leftCenter, final HitBox hB) {
        return new Vector2(
                leftCenter.x() + (hB.getWidth() / 2f),
                leftCenter.y() + (hB.getHeight() / 2f));
    }

    /**
     * Creates a JBox2d Body instance.
     * 
     * @param pos      the world position of the body,it indicates the center.
     * @param bodyType the {@link BodyType} oh the body
     * @param data     the {@link GameObject} set as user data and uses for
     *                 collision handling.
     * @return the body just created
     */
    private Body createBody(final Vector2 pos, final BodyType bodyType, final GameObject<?> data) {
        final BodyDef bDef = new BodyDef();
        bDef.type = bodyType;
        bDef.position.set(pos.x(), pos.y());

        final Body body = world.createBody(bDef);
        body.setFixedRotation(true);
        body.setUserData(data);
        return body;
    }

    /**
     * Creates the Fixture Definition of a polygonal body to create.
     * 
     * <p>
     * The fixture definition is used by the body to set its most important physics
     * attributes
     * as the shape, the friction and the restitution.
     * </p>
     * 
     * @param hB        the hitbox of the {@link GameObject}
     * @param isASensor true if the body to create is a sensor false otherwise.
     * @return the Fixture definition just created.
     */
    private FixtureDef createPoligonalFixtureDefinition(final HitBox hB, final boolean isASensor) {
        final PolygonShape shapeBox2d = new PolygonShape();

        final float halfWidth = hB.getWidth() / 2f;
        final float halfHeight = hB.getHeight() / 2f;

        final Vec2[] verts = hB.getVertices().stream()
                .map(v -> new Vec2(v.x() - halfWidth, v.y() - halfHeight))
                .toArray(Vec2[]::new);
        shapeBox2d.set(verts, verts.length);
        final FixtureDef fDef = new FixtureDef();
        fDef.shape = shapeBox2d;
        fDef.isSensor = isASensor;
        fDef.restitution = STANDARD_RESTITUTION;
        fDef.friction = STANDARD_FRICTION;
        return fDef;
    }

    /**
     * Creates the Fixture Definition of a circular body to create.
     * 
     * <p>
     * The fixture definition is used by the body to set its most important physics
     * attributes
     * as the shape, the friction and the restitution.
     * </p>
     * 
     * @param hB        the hitbox of the {@link GameObject}
     * @param isASensor true if the body to create is a sensor false otherwise.
     * @return the Fixture definition just created.
     */
    private FixtureDef createCircularFixtureDefinition(final CircleHitBox hB, final boolean isASensor) {
        final CircleShape shapeBox2d = new CircleShape();
        shapeBox2d.m_radius = hB.getRadius();
        final FixtureDef fDef = new FixtureDef();
        fDef.shape = shapeBox2d;
        fDef.isSensor = isASensor;
        fDef.restitution = STANDARD_RESTITUTION;
        fDef.friction = STANDARD_FRICTION;
        return fDef;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Body createObstacle(final Obstacle obj) {
        final Shape sH = obj.getHitBox();
        if (sH instanceof CircleHitBox) {
            throw new InvalidPhysicsEngineConfiguration("Circular obstacle dont exist");
        } else if (sH instanceof HitBox) {
            boolean isAsensor = false;
            if (obj instanceof Spike) {
                isAsensor = true;
            }
            final HitBox hB = (HitBox) sH;
            final Vector2 obstaclePosition = obj.getPosition();
            final Vector2 centerPos = modelCenterToJBox2dCenterConverter(obstaclePosition, hB);
            final Body body = createBody(centerPos, BodyType.STATIC, obj);
            final FixtureDef fDef = createPoligonalFixtureDefinition(hB, isAsensor);
            body.createFixture(fDef);

            return body;
        } else {
            throw new InvalidPhysicsEngineConfiguration("Impossible to create a new obstacle in jbox2d"
                    + "physical engine with an invalid shape");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Body createPowerUp(final PowerUp<? extends Shape> obj) {
        final Shape sH = obj.getHitBox();
        if (sH instanceof CircleHitBox) {
            final CircleHitBox hB = (CircleHitBox) sH;
            final Body body = createBody(obj.getPosition(), BodyType.STATIC, obj);
            final FixtureDef fDef = createCircularFixtureDefinition(hB, true);
            body.createFixture(fDef);

            return body;
        } else if (sH instanceof HitBox) {
            final HitBox hB = (HitBox) sH;
            final Vector2 powerUpPosition = obj.getPosition();
            final Vector2 centerPos = modelCenterToJBox2dCenterConverter(powerUpPosition, hB);
            final Body body = createBody(centerPos, BodyType.STATIC, obj);
            final FixtureDef fDef = createPoligonalFixtureDefinition(hB, true);
            body.createFixture(fDef);

            return body;
        } else {
            throw new InvalidPhysicsEngineConfiguration("Impossible to create a new powerup in jbox2d physical engine");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Body createPlayer(final Player<? extends Shape> p) {
        final Shape sH = p.getHitBox();
        if (sH instanceof CircleHitBox) {
            throw new InvalidPhysicsEngineConfiguration("Circular player dont exist");
        } else if (sH instanceof HitBox) {
            final HitBox hB = (HitBox) sH;
            final Vector2 playerPosition = p.getPosition();
            final Vector2 centerPos = modelCenterToJBox2dCenterConverter(playerPosition, hB);
            final Body body = createBody(centerPos, BodyType.DYNAMIC, p);
            final FixtureDef fDef = createPoligonalFixtureDefinition(hB, false);
            fDef.density = PLAYER_STANDARD_DENSITY;
            fDef.friction = PLAYER_STANDARD_FRICTION;
            body.createFixture(fDef);
            body.setBullet(true);

            return body;
        } else {
            throw new InvalidPhysicsEngineConfiguration("Impossible to create a new player in jbox2d physical engine");
        }
    }
}
