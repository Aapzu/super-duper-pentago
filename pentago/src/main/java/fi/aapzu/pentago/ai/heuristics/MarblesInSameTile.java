package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Tile;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * Gives points according to how many marbles the player has in a same tile in a row.
 */
public class MarblesInSameTile implements Heuristic {

    private static final int TWO_D = 10;
    private static final int TWO = 20;
    private static final int THREE_D = 100;
    private static final int THREE = 200;

    private static final int[][][] VERTICAL_HORIZONTAL_LINES = {
            {{0, 0}, {1, 0}, {2, 0}}, {{0, 1}, {1, 1}, {2, 1}},
            {{0, 2}, {1, 2}, {2, 2}}, {{0, 0}, {0, 1}, {0, 2}},
            {{1, 0}, {1, 1}, {1, 2}}, {{2, 0}, {2, 1}, {2, 2}},
    };

    private static final int[][][] DIAGONAL_LINES = {
            {{0, 0}, {1, 1}, {2, 2}}, {{0, 2}, {1, 1}, {2, 0}}
    };

    private int analyzeLine(Tile tile, Symbol me, int[][] possibleLine, boolean diagonal) {
        int t = 0;
        Marble marble;
        int inLine = 0;
        boolean middleOneFound = false;
        for (int j = 0; j < 3; j++) {
            marble = tile.getMarble(possibleLine[j][0], possibleLine[j][1]);
            if (marble != null && marble.getSymbol().equals(me)) {
                inLine++;
                if (j == 1) {
                    middleOneFound = true;
                }
            }
        }
        if (inLine == 3) { // Found a 3-marble-long line
            t += diagonal ? THREE_D : THREE;
        } else if (inLine == 2 && middleOneFound) {
            t += diagonal ? TWO_D : TWO;
        }
        return t;
    }

    private int analyzeTile(Tile tile, int playerIndex) {
        Symbol me = Player.getSymbolForPlayerNumber(playerIndex);
        int t = 0;

        for (int[][] possibleLine : VERTICAL_HORIZONTAL_LINES) {
            t += analyzeLine(tile, me, possibleLine, false);
        }

        for (int[][] possibleLine : DIAGONAL_LINES) {
            t += analyzeLine(tile, me, possibleLine, true);
        }

        return t;
    }

    @Override
    public int getScore(Pentago game, int playerIndex) {
        Board board = game.getBoard();
        int score = 0;
        for (int y = 0; y < board.getSideLength(); y++) {
            for (int x = 0; x < board.getSideLength(); x++) {
                score += analyzeTile(board.getTile(x, y), playerIndex);
            }
        }
        return score;
    }
}
