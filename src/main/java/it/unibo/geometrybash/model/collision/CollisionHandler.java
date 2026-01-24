package it.unibo.geometrybash.model.collision;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;
import org.jbox2d.collision.Manifold;
import org.jbox2d.callbacks.ContactImpulse;

import it.unibo.geometrybash.model.core.GameObject;
import it.unibo.geometrybash.model.player.Player;

/**
 * Detects collisions between game objects using the JBox2D physics engine.
 *
 * <p>
 * Implements {@link ContactListener} to receive notifications when two fixtures begin or end contact.
 * </p>
 */
public class CollisionHandler implements ContactListener {

    /**
     * Creates a new CollisionHandler.
     */
    public CollisionHandler() {
        // Default constructor
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beginContact(final Contact contact) {
        processContact(contact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endContact(final Contact contact) { }

    private void processContact(final Contact contact) {
        final GameObject<?> a = getGameObject(contact.getFixtureA());
        final GameObject<?> b = getGameObject(contact.getFixtureB());

        if (a == null || b == null) {
            return;
        }
        handleCollision(a, b);
        handleCollision(b, a);
    }

    private GameObject<?> getGameObject(final Fixture fixture) {
        final Object userData = fixture.getBody().getUserData();
        return userData instanceof GameObject gameObject ? gameObject : null;
    }

    private void handleCollision(final GameObject<?> source, final GameObject<?> other) {
        if (source instanceof Collidable collidable && other instanceof Player player) {
            collidable.onCollision(player);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preSolve(final Contact contact, final Manifold oldManifold) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postSolve(final Contact contact, final ContactImpulse impulse) {
    }
}
