package it.unibo.geometrybash.model.physicsengine.impl.jbox2d;

import org.jbox2d.dynamics.World;

import it.unibo.geometrybash.model.geometry.Vector2;

/**
 * The values use for the simulation of the physics engine.
 */
public final class JBox2DValues {

    /**
     * The default friction value.
     */
    public static final float STANDARD_FRICTION = 0.0f;
    /**
     * The default restitution value.
     */
    public static final float STANDARD_RESTITUTION = 0.0f;

    /**
     * The standard density set for the player.
     */
    public static final float PLAYER_STANDARD_DENSITY = 1.0f;

    /**
     * the standard friction of the player.
     * 
     * <p>
     * Now set to -0.1f for smooth sliding.
     * </p>
     */
    public static final float PLAYER_STANDARD_FRICTION = -0.1f;

    /**
     * The gravity of the {@link World}.
     */
    public static final Vector2 GRAVITY = new Vector2(0f, -25.0f);

    /**
     * The Velocity Iterations to use during the
     * {@link World#step(float, int, int)}.
     * 
     * <p>
     * The velocity phase in jbox2d engine evaluates the forces to apply to
     * the bodies to meve correctly. THis values represents the number of iterations
     * of that phase.
     * </p>
     */
    public static final int VELOCITY_ITERATIONS = 8;

    /**
     * The Position Iterations to use during the
     * {@link World#step(float, int, int)}.
     * 
     * 
     * <p>
     * The position phase in jbox2d engine evaluates the exact position of the
     * player. This values represents the number of iterations
     * of that phase.
     * </p>
     */
    public static final int POSITION_ITERATIONS = 3;

    private JBox2DValues() {
        // This class can't be instanciated.
    }

}
