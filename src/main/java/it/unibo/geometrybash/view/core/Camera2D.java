package it.unibo.geometrybash.view.core;

import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * Handles coordinate transformation from world which uses meters to screen, it uses pixel.
 * The Y-axis is inverted since Swing has Y=0 at top but Jbox2D has it at the
 * bottom.
 */
public final class Camera2D {

    /**
     * Default value for pixel per meters.
     */
    private static final float DEFAULT_VALUE = 50.0f;

    private float pixelPerMeter = DEFAULT_VALUE;
    private float offsetX;
    private int viewportHeight;

    /**
     * Create new Camera2D.
     */
    public Camera2D() {
        // Default constructor.
    }

    /**
     * Sets the horizontal camera offset.
     *
     * @param x the offset in meters
     */
    public void setOffset(final float x) {
        this.offsetX = x;
    }

    /**
     * Sets the viewport height used for Y-axis inversion.
     *
     * @param height the viewport height in pixels
     */
    public void setViewportHeight(final int height) {
        this.viewportHeight = height;
    }

    /**
     * Converts a world X coordinate from meters to pixels.
     *
     * @param xMeters the X coordinate in meters
     * @return the corresponding X coordinate in pixels
     */
    public int xToPx(final float xMeters) {
        return Math.toIntExact(Math.round((xMeters - offsetX) * this.pixelPerMeter));
    }

    /**
     * Converts a world Y coordinate from meters to pixels, inverting the Y axis.
     *
     * @param yMeters the Y coordinate in meters
     * @return the corresponding Y coordinate in pixels
     */
    public int yToPx(final float yMeters) {
        return viewportHeight - Math.toIntExact(Math.round(yMeters * this.pixelPerMeter));
    }

    /**
     * Converts a size value from meters to pixels.
     *
     * @param meters the size in meters
     * @return the corresponding size in pixels
     */
    public int sizeToPx(final float meters) {
        return Math.toIntExact(Math.round(meters * this.pixelPerMeter));
    }

    /**
     * Create a defensive copy of the camersa used in the RenderContext.
     *
     * @return a Camera2D copy
     */
    public Camera2D copy() {
        final Camera2D copy = new Camera2D();
        copy.setOffset(this.offsetX);
        copy.setViewportHeight(this.viewportHeight);
        return copy;
    }

    /**
     * Sets the scaling factor based on the chosen resolution.
     *
     * @param res the resolution to adapt to.
     */
    public void setPixelPerMeter(final GameResolution res) {
        this.pixelPerMeter = res.getPpm();
    }

}
