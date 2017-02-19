package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

public class PossibleLines implements Heuristic {

    private static final int INITIAL_POINTS = 10;
    private static final int POINT_FACTOR = 10;

    private static final int[][][] ALL_POSSIBLE_LINES = {
        {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}}, {{0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}},
        {{1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}}, {{1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}},
        {{2, 0}, {2, 1}, {2, 2}, {2, 3}, {2, 4}}, {{2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}},
        {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {3, 4}}, {{3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}},
        {{4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4}}, {{4, 1}, {4, 2}, {4, 3}, {4, 4}, {4, 5}},
        {{5, 0}, {5, 1}, {5, 2}, {5, 3}, {5, 4}}, {{5, 1}, {5, 2}, {5, 3}, {5, 4}, {5, 5}},
        {{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}}, {{1, 0}, {2, 0}, {3, 0}, {4, 0}, {5, 0}},
        {{0, 1}, {1, 1}, {2, 1}, {3, 1}, {4, 1}}, {{1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}},
        {{0, 2}, {1, 2}, {2, 2}, {3, 2}, {4, 2}}, {{1, 2}, {2, 2}, {3, 2}, {4, 2}, {5, 2}},
        {{0, 3}, {1, 3}, {2, 3}, {3, 3}, {4, 3}}, {{1, 3}, {2, 3}, {3, 3}, {4, 3}, {5, 3}},
        {{0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}}, {{1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}},
        {{0, 5}, {1, 5}, {2, 5}, {3, 5}, {4, 5}}, {{1, 5}, {2, 5}, {3, 5}, {4, 5}, {5, 5}},
        {{0, 0}, {1, 1}, {2, 2}, {3, 3}, {4, 4}}, {{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}},
        {{0, 5}, {1, 4}, {2, 3}, {3, 2}, {4, 1}}, {{1, 4}, {2, 3}, {3, 2}, {4, 1}, {5, 0}},
        {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}}, {{1, 0}, {2, 1}, {3, 2}, {4, 3}, {5, 4}},
        {{0, 4}, {1, 3}, {2, 2}, {3, 1}, {4, 0}}, {{1, 5}, {2, 4}, {3, 3}, {4, 2}, {5, 1}}
    };

    private static final int[][] HEURISTIC_ARRAY = new int[][]{
            {0,                   -10,   -100,  -1000, -10000,  Integer.MIN_VALUE},
            {10,                    0,      0,      0,      0,      0},
            {100,                   0,      0,      0,      0,      0},
            {1000,                  0,      0,      0,      0,      0},
            {10000,                 0,      0,      0,      0,      0},
            {Integer.MAX_VALUE,     0,      0,      0,      0,      0}
    };

    @Override
    public int getScore(Pentago game, int playerIndex) {
        Symbol me = Player.getSymbolForPlayerNumber(playerIndex);
        Symbol opponent = Symbol.getOpponent(me);
        Marble marble;

        int my, opponents;
        long t = 0L;
        for (int[][] possibleLine : ALL_POSSIBLE_LINES) {
            my = opponents = 0;
            for (int j = 0; j < 5; j++) {
                marble = game.getBoard().getMarble(possibleLine[j][0], possibleLine[j][1]);
                if (marble != null && marble.getSymbol().equals(me)) {
                    my++;
                } else if (marble != null && marble.getSymbol().equals(opponent)) {
                    opponents++;
                }
            }
            t += HEURISTIC_ARRAY[my][opponents];
            t = Math.min(Integer.MAX_VALUE, t);
        }
        return Math.toIntExact(t);
    }
}
