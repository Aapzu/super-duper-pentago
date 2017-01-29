package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.ai.heuristics.Heuristics;
import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * The core element of AI. Forms a game tree and tries to find the best possible move at the moment for the Bot.
 */
public class AlphaBetaPruning {

    private final Pentago game;
    private final Heuristics heuristics;

    /**
     * @param game
     */
    public AlphaBetaPruning(Pentago game) {
        this.game = game;
        this.heuristics = new Heuristics();
    }

    /**
     * Calculates the best move.
     *
     * @param movesAhead how many moves ahead is calculated before implying heuristics
     * @return move the best move
     */
    public Move getBest(int movesAhead) {
        Pentago bestGameYet = game;
        int bestGameScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (Pentago game : PossibleGamesWithOneMove.get(game)) {
            int score = maxValue(game, alpha, beta, 1, movesAhead);
            if (score > bestGameScore) {
                bestGameYet = game;
                bestGameScore = score;
            }
        }
        return bestGameYet.getLastMove();
    }

    private int maxValue(Pentago gameState, int alpha, int beta, int depth, int maxDepth) {
        Pentago[] possibleGames = PossibleGamesWithOneMove.get(gameState);
        if (depth >= maxDepth || possibleGames.length == 0) {
            return heuristics.getScore(gameState, game.whoseTurn());
        }
        int value = Integer.MIN_VALUE;
        for (Pentago newGameState : possibleGames) {
            value = Math.max(value, minValue(newGameState, alpha, beta, depth++, maxDepth));
            if (value > beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private int minValue(Pentago gameState, int alpha, int beta, int depth, int maxDepth) {
        Pentago[] possibleGames = PossibleGamesWithOneMove.get(gameState);
        if (depth >= maxDepth || possibleGames.length == 0) {
            return heuristics.getScore(gameState, game.whoseTurn());
        }
        int value = Integer.MAX_VALUE;
        for(Pentago newGameState : possibleGames) {
            value = Math.min(value, maxValue(newGameState, alpha, beta, depth++, maxDepth));
            if (value < alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }
}
