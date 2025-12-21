package it.unibo.geometrybash.model.player;

import it.unibo.geometrybash.model.collision.Collidable;
import it.unibo.geometrybash.model.collision.CollisionEvent;
import it.unibo.geometrybash.model.core.AbstractGameObject;
import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.geometry.HitBox;
import it.unibo.geometrybash.model.geometry.Vector2;

public class PlayerImpl extends AbstractGameObject implements Player, Collidable{

    protected PlayerImpl(Vector2 position, HitBox hitBox) {
        super(position, hitBox);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void onCollision(CollisionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onCollision'");
    }

    @Override
    public void respawn(Vector2 position) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'respawn'");
    }

    @Override
    public void jump() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jump'");
    }

    @Override
    public PlayerState getState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getState'");
    }

    @Override
    public float getForwardSpeed() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getForwardSpeed'");
    }

    @Override
    public void setForwardSpeed(float speed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setForwardSpeed'");
    }

    @Override
    public Skin getSkin() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSkin'");
    }

    @Override
    public void setSkin(Skin skin) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSkin'");
    }

    @Override
    public GameObject copy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }
}
