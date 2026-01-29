package it.unibo.geometrybash.view.utilities;

/**
 * Supported resolutions fot he game.
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
    private final int viewPortX;
    private final int viewPortY;

    /**
     * Set the variable ppm with the correct value.
     *
     * @param ppm the pixel per meter value.
     */
    GameResolution(final float ppm, int x, int y) {
        this.ppm = ppm;
        this.viewPortX = x;
        this.viewPortY = y;
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
    public int getViewPortX() {
        return this.viewPortX;
    }

    /**
     * Retrieves the viewport horizontal dimension.
     * 
     * @return the viewport horizontal dimension.
     *
     */
    public int getViewPortY() {
        return this.viewPortY;
    }
}
