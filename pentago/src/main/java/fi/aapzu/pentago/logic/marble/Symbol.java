package fi.aapzu.pentago.logic.marble;

/**
 * Contains the two symbols of the Pentago game. Equivalent for black and white.
 *
 * @author Aapeli
 */
public enum Symbol {
    /**
     * Corresponds white color on the Board.
     */
    O,

    /**
     * Corresponds black color on the Board.
     */
    X;

    /**
     * Returns the opponent of the given Symbol.
     *
     * @param me Symbol the opponent is wanted for
     * @return opponent's Symbol
     */
    public static Symbol getOpponent(Symbol me) {
        return me == X ? O : X;
    }
}
