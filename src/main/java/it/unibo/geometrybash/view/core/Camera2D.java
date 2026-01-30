package it.unibo.geometrybash.view.core;

import it.unibo.geometrybash.view.utilities.GameResolution;

/**
 * Handles coordinate transformation from world which uses meters to screen, it
 * uses pixel.
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
     * creates a new 2D camera with the parameter values of the current session.
     *
     * @param resolution the game's resolution.
     * @param offset
     */
    public Camera2D(final GameResolution resolution, final int offset) {
        this.pixelPerMeter = resolution.getPpm();
        this.offsetX = offset;
        this.viewportHeight = resolution.getViewPortHeight();
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
     * Sets the viewport height used for Y-axis inversion, The setter provides the
     * possibility to set the size of the window if you want to resize it in the
     * future while the game is in progress.
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
        final Camera2D copy = new Camera2D(this.pixelPerMeter, this.viewportHeight, this.offsetX);
        return copy;
    }

    /**
     * A private constructor useful for thread security.
     *
     * @param ppm the current pixel per meter value.
     * @param height the current view port height.
     */
    private Camera2D(final float ppm, final int height, final float offset) {
        this.pixelPerMeter = ppm;
        this.viewportHeight = height;
        this.offsetX = offset;
    }
}
