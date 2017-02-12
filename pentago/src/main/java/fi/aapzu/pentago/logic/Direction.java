package fi.aapzu.pentago.logic;

/**
 * An enumeration for the directions in the game.
 *
 * @author Aapeli
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
        return new Direction[]{HORIZONTAL, VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL};
    }

    /**
     * Gives the opposite Direction of a rotating direction. Returns null if the
     * Direction d is not a rotating direction.
     *
     * @param d the direction to getMarble the opposite from
     * @return opposite Direction or null
     */
    public static Direction getOpposite(Direction d) {
        if (d != null) {
            switch (d) {
                case CLOCKWISE:
                    return COUNTER_CLOCKWISE;
                case COUNTER_CLOCKWISE:
                    return CLOCKWISE;
            }
        }
        return null;
    }
}
