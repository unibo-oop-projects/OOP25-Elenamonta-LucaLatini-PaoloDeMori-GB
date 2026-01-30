package it.unibo.geometrybash.view.core;

/**
 * An immutable container providing global environmental data to the rendering
 * system.
 *
 * <p>
 * The {@code RenderContext} passed to all game object renderers.
 * It encapsulates the necessary information to transform world coordinates into
 * pixels.
 *
 * @param camera         the {@link Camera2D} instance responsible for
 *                       coordinate transformation and scaling
 * @param viewportWidth  the current width of the drawing area in pixels
 * @param viewportHeight the current height of the drawing area in pixels
 */
public record RenderContext(Camera2D camera, int viewportWidth, int viewportHeight) {

    /**
     * Constructor to obtain defensive copy of the Camera2D.
     */
    public RenderContext(final Camera2D camera, final int viewportWidth, final int viewportHeight) {
        this.camera = camera.copy();
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    /**
     * Overridden accessor to return a defensive copy of the camera.
     *
     * @return a defensive copy of the camera
     */
    public Camera2D camera() {
        return this.camera.copy();
    }

    /**
     * Set the camera's offset.
     *
     * @param offset a float represents the camera's offset.
     */
    public void setOffset(final float offset) {
        this.camera.setOffset(offset);
    }
}
