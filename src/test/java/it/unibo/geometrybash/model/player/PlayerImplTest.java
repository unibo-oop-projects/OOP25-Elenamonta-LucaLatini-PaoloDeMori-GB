package it.unibo.geometrybash.model.player;
import org.junit.jupiter.api.BeforeEach;

import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;
import it.unibo.geometrybash.model.physicsengine.PlayerPhysics;

final class MockPlayerPhysics implements PlayerPhysics {

    @Override
    public void applyJump() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'applyJump'");
    }

    @Override
    public void setVelocity(float multiplier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVelocity'");
    }

    @Override
    public Vector2 getVelocity() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVelocity'");
    }

    @Override
    public void resetBodyTo(Vector2 pos) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetBodyTo'");
    }

    @Override
    public Vector2 getPosition(HitBox hB) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public void setUserData(Object userData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserData'");
    }

    @Override
    public boolean isGrounded() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isGrounded'");
    }

    @Override
    public void onGroundContactBegin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onGroundContactBegin'");
    }

    @Override
    public void onGroundContactEnd() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onGroundContactEnd'");
    }

    @Override
    public void setActive(boolean activeState) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setActive'");
    }

}

class PlayerImplTest {
    private PlayerImpl player;
    private MockPlayerPhysics physics;

    @BeforeEach
    void setup() {
        this.player = new PlayerImpl(new Vector2(5.0f, 5.0f));
    }

}
