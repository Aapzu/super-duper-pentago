package fi.aapzu.pentago.logic;

/**
 * An enumeration for the directions in the game.
 *
 */
public enum Direction {

    /**
     * Rotating direction clockwise.
     */
    CLOCKWISE,
    /**
     * Rotating direction counter clockwise.
     */
    COUNTER_CLOCKWISE,
    /**
     * Line direction from up to down or vice versa.
     */
    VERTICAL,
    /**
     * Line direction from left to right or vice versa.
     */
    HORIZONTAL,
    /**
     * Line direction from lower-left to upper-right.
     */
    UPGRADING_DIAGONAL,
    /**
     * Line direction from upper-left to lower-right.
     */
    DOWNGRADING_DIAGONAL;

    public Direction opposite;

    /**
     * Gives the directions in which the tiles are allowed to be rotated.
     *
     * @return Array of Directions
     */
    public static Direction[] getRotateDirections() {
        return new Direction[]{CLOCKWISE, COUNTER_CLOCKWISE};
    }

    /**
     * Gives the directions in which a Line can be formed.
     *
     * @return Array of Directions
     */
    public static Direction[] getLineDirections() {
        return new Direction[]{ HORIZONTAL, VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL };
    }

    static {
        CLOCKWISE.opposite = COUNTER_CLOCKWISE;
        COUNTER_CLOCKWISE.opposite = CLOCKWISE;
    }

}
