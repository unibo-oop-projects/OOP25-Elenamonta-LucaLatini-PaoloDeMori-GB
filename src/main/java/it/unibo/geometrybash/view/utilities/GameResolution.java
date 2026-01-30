package it.unibo.geometrybash.view.utilities;

/**
 * Supported resolutions for the game.
 */
public enum GameResolution {
    /**
     * 1920x1080 resolution.
     */
    BIG(96.0f, 1_920, 1_080),

    /**
     * 1600x9000 resolution.
     */
    MEDIUM(80.0F, 1_600, 900),

    /**
     * 1024x768 resolution.
     */
    SMALL(51.2F, 1_024, 768);

    private final float ppm;
    private final int viewPortWidth;
    private final int viewPortHeight;

    /**
     * Set the variable ppm with the correct value.
     *
     * @param ppm the pixel per meter value.
     * @param x   the width of the viewport.
     * @param y   the height of the viewport.
     */
    GameResolution(final float ppm, final int x, final int y) {
        this.ppm = ppm;
        this.viewPortWidth = x;
        this.viewPortHeight = y;
    }

    /**
     * retrives the variable ppm.
     *
     * @return the pixel per meter.
     */
    public float getPpm() {
        return ppm;
    }

    /**
     * Retrieves the viewport horizontal dimension.
     *
     * @return the viewport horizontal dimension.
     */
    public int getViewPortWidth() {
        return this.viewPortWidth;
    }

    /**
     * Retrieves the viewport horizontal dimension.
     *
     * @return the viewport horizontal dimension.
     *
     */
    public int getViewPortHeight() {
        return this.viewPortHeight;
    }
}
