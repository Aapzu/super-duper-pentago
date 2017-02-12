package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;

/**
 * A COLLECTION of other heuristics, returning the sum of all of them.
 */
public class Heuristics {

    private static final Heuristic[] COLLECTION = new Heuristic[]{
        new MarblesInSameTile(),
        new PossibleLines()
    };


    /**
     * Calculates a sum of the heuristics' getScore() methods defined in constructor.
     *
     * @param game        the state of the Pentago game
     * @param playerIndex the player the score is calculated for
     * @return points
     */
    public static int getScore(Pentago game, int playerIndex) {
        int points = 0;
        for (Heuristic h : COLLECTION) {
            points += h.getScore(game, playerIndex);
        }
        return points;
    }
}
