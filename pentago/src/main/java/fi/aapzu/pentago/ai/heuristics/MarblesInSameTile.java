package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.*;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

import java.io.IOException;

/**
 * Gives points according to how many marbles the player has in a same tile in a row
 */
public class MarblesInSameTile implements Heuristic {

    private static int one = 0;
    private static int twoD = 10;
    private static int two = 20;
    private static int threeD = 100;
    private static int three = 200;

    private int[][][] verticalHorizontalLines = {
            {{0,0},{1,0},{2,0}}, {{0,1},{1,1},{2,1}},
            {{0,2},{1,2},{2,2}}, {{0,0},{0,1},{0,2}},
            {{1,0},{1,1},{1,2}}, {{2,0},{2,1},{2,2}},
    };

    private int[][][] diagonalLines = {
            {{0,0},{1,1},{2,2}}, {{0,2}, {1,1},{2,0}}
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
            t += diagonal ? threeD : three;
        } else if (inLine == 2 && middleOneFound) {
            t += diagonal ? twoD : two;
        }
        return t;
    }

    private int analyzeTile(Tile tile, Player player) {
        Symbol me = player.getSymbol();
        int t = 0;

        for (int[][] possibleLine : verticalHorizontalLines) {
            t += analyzeLine(tile, me, possibleLine, false);
        }

        for (int[][] possibleLine : diagonalLines) {
            t += analyzeLine(tile, me, possibleLine, true);
        }

        return t;
    }

    @Override
    public int getScore(Pentago game, Player player) {
        Board board = game.getBoard();
        int score = 0;
        for (int y = 0; y < board.getSideLength(); y++) {
            for (int x = 0; x < board.getSideLength(); x++) {
                score += analyzeTile(board.getTile(x, y), player);
            }
        }
        return score;
    }
}
