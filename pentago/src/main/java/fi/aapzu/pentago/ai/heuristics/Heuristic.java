package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;

/**
 * A heuristic used by Bot
 */
public interface Heuristic {

    /**
     * Calculates a value for the gameState for the player
     *
     * @param gameState
     * @param player
     * @return score
     */
    int getScore(Pentago gameState, Player player);
}
