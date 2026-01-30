package it.unibo.geometrybash.model.player;

import org.junit.jupiter.api.BeforeEach;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;

final class MockPlayerPhysics implements PlayerPhysics {

    private static final String NOT_NECESSARY_METHOD = "this method is not tested here";
    private static final float BASE_SPEED = 5.0f;
    private Vector2 velocity;
    private boolean grounded;

    MockPlayerPhysics() {
        this.velocity = new Vector2(BASE_SPEED, 0f);
        this.grounded = true;
    }

    @Override
    public void applyJump() {
        throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
    }

    @Override
    public void setVelocity(float multiplier) {
        velocity = new Vector2(BASE_SPEED * multiplier, velocity.y());
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public void resetBodyTo(Vector2 pos) {
        throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
    }

    @Override
    public Vector2 getPosition(HitBox hB) {
        throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
    }

    @Override
    public void setUserData(Object userData) {
        throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
    }

    @Override
    public boolean isGrounded() {
        return grounded;
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
    public void setActive(boolean activeState) {
        throw new UnsupportedOperationException(NOT_NECESSARY_METHOD);
    }

}

class PlayerImplTest {
    private PlayerImpl player;
    private MockPlayerPhysics physics;

    @BeforeEach
    void setup() {
        this.player = new PlayerImpl(new Vector2(5.0f, 5.0f));
        this.physics = new MockPlayerPhysics();
        this.player.bindPhysics(physics);
    }

    


}
