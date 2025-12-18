package it.unibo.geometrybash.model.collision;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.collision.Manifold;
import org.jbox2d.callbacks.ContactImpulse;

import it.unibo.geometrybash.model.core.GameObject;

/**
 * Detects collisions between game objects using the JBox2D physics engine.
 *
 * <p>
 * Implements {@link ContactListener} to receive notifications when two fixtures
 * begin or end contact. For each collision, a {@link CollisionEvent} is created
 * and dispatched to objects implementing {@link Collidable}.
 * </p>
 */
public class CollisionHandler implements ContactListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void beginContact(final Contact contact) {
        processContact(contact, CollisionPhase.BEGIN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endContact(final Contact contact) {
        processContact(contact, CollisionPhase.END);
    }

    private void processContact(final Contact contact, final CollisionPhase phase) {
        final GameObject a = (GameObject) contact.getFixtureA().getUserData();
        final GameObject b = (GameObject) contact.getFixtureB().getUserData();

        if (a instanceof Collidable) {
            ((Collidable) a).onCollision(new CollisionEvent(a, b, phase));
        }
        if (b instanceof Collidable) {
            ((Collidable) b).onCollision(new CollisionEvent(b, a, phase));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) { }
}
