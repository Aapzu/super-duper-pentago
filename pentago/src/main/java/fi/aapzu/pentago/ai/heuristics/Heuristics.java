package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.Board;


import java.io.IOException;
import java.util.Properties;

/**
 * A collection of other heuristics, returning the sum of all of them
 */
public class Heuristics implements Heuristic {

    private Heuristic[] collection;

    /**
     * Defines the Heuristics used in collection
     */
    public Heuristics() {
        collection = new Heuristic[]{
            new MarblesInSameTile(),
            new PossibleLines()
        };
    }

    /**
     * Calculates a sum of the heuristics' getScore() methods defined in constructor
     *
     * @param game
     * @param player the score is calculated for
     * @return points
     */
    public int getScore(Pentago game, Player player) {
        int points = 0;
        for (Heuristic h : collection) {
            points += h.getScore(game, player);
        }
        return points;
    }
}
