package it.unibo.geometrybash.view.utilities;

/**
 * Supported resolutions fot he game.
 */
public enum GameResolution {
    /**
     * 1920x1080 resolution.
     */
    BIG(96.0f),

    /**
     * 1600x9000 resolution.
     */
    MEDIUM(80.0F),

    /**
     * 1024x768 resolution.
     */
    SMALL(51.2F);

    private final float ppm;

    /**
     * Set the variable ppm with the correct value.
     *
     * @param ppm the pixel per meter value.
     */
    GameResolution(final float ppm) {
        this.ppm = ppm;
    }

    /**
     * retrives the variable ppm.
     *
     * @return the pixel per meter.
     */
    public float getPpm() {
        return ppm;
    }
}
