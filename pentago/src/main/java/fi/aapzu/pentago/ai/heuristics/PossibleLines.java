package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

public class PossibleLines implements Heuristic {

    private final int initialPoints = 10;
    private final int pointFactor = 10;

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

    private final int[][] heuristicArray = new int[6][6];

    private void initHeuristicArray() {
        for (int y = 1; y < 6; y++) {
            heuristicArray[y][0] = initialPoints * (int) Math.pow(pointFactor, y - 1);
        }
        for (int x = 0; x < 6; x++) {
            heuristicArray[0][x] = -initialPoints * (int) Math.pow(pointFactor, x - 1);
        }
    }

    PossibleLines() {
        initHeuristicArray();
    }

    @Override
    public int getScore(Pentago game, int playerIndex) {
        Symbol me = Player.getSymbolForPlayerNumber(playerIndex);
        Symbol opponent = Symbol.getOpponent(me);
        Marble marble;
        int my;
        int opponents;
        int t = 0;

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
            if (my == 5) {
                return Integer.MAX_VALUE; // Won the game
            }
            t += heuristicArray[my][opponents];
        }
        return t;
    }
}
