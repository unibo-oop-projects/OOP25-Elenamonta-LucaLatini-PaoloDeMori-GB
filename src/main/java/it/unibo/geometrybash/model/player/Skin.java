package it.unibo.geometrybash.model.player;

/**
 * Represents the visual skin configuration of a player.
 *
 * <p>
 * Defines the appearance-related attributes of a player, such as the colors
 * used for its inner and outer parts.
 * These values represent domain-level configuration data selected by the user
 * and are independent from any rendering or graphics implementation.
 * </p>
 */
public class Skin {

    private static final int DEFAULT_INNER_COLOR = 0xFF0000FF;
    private static final int DEFAULT_OUTER_COLOR = 0xFFFF0000;
    private final int innerColor;
    private final int outerColor;

    /**
     * Creates a {@code Skin} instance with default colors.
     */
    protected Skin() {
        this(DEFAULT_INNER_COLOR, DEFAULT_OUTER_COLOR);
    }

    /**
     * Creates a {@code Skin} instance with the specified colors.
     *
     * @param innerColor the ARGB color value for the inner part of the skin
     * @param outerColor the ARGB color value for the outer part of the skin
     */
    public Skin(final int innerColor, final int outerColor) {
        this.innerColor = innerColor;
        this.outerColor = outerColor;
    }

    /**
     * Returns the inner color of this skin.
     *
     * @return the ARGB color value used for the inner part of the skin
     */
    public int getInnerColor() {
        return this.innerColor;
    }

    /**
     * Returns the outer color of this skin.
     *
     * @return the ARGB color value used for the outer part of the skin
     */
    public int getOuterColor() {
        return this.outerColor;
    }

    /**
     * Creates a new {@code Skin} instance with the specified colors,
     * preserving immutability.
     *
     * @param inner the ARGB color value for the inner part of the skin
     * @param outer the ARGB color value for the outer part of the skin
     * @return a new {@code Skin} instance with the given colors
     */
    public Skin withColors(final int inner, final int outer) {
        return new Skin(inner, outer);
    }
}
