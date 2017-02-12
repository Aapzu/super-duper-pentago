package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;

/**
 * A heuristic used by Bot.
 */
public interface Heuristic {

    /**
     * Calculates a value for the gameState for the player.
     *
     * @param gameState   state of the Pentago game
     * @param playerIndex index of the Player for whom the score is calculated
     * @return score
     */
    int getScore(Pentago gameState, int playerIndex);
}
