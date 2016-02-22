/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.Arrays;

/**
 * This is a class for checking for lines on a Board.
 *
 * @author Aapeli
 */
public class BoardLineChecker {

    private Board board;

    /**
     * Creates a new BoardLineChecker for the given Board.
     *
     * @param board
     */
    public BoardLineChecker(Board board) {
        this.board = board;
    }

    /**
     * Calls checkLines(length, d) with all the valid Directions (HORIZONTAL,
     * VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL).
     *
     * @param length the length to be given to checkLines
     * @return the line found or null
     */
    public Line checkLines(int length) {
        if (length < 2 || length > board.getSideLength() * board.getTileSideLength()) {
            throw new IllegalArgumentException("The length of a line must be between 2 and " + board.getSideLength() * board.getTileSideLength());
        }
        Line line = null;

        for (Direction d : Direction.getLineDirections()) {
            line = checkLines(length, d);
            if (line != null) {
                break;
            }
        }

        return line;
    }

    /**
     * Checks if there are the given amount of same symbol on the board in the
     * given direction. If a line was found, returns a map with the symbol and
     * the coordinates of the line.
     *
     * @param length the required amount of the same symbols in a row
     * @param d the Direction to be looked at
     * @return the line or null
     */
    protected Line checkLines(int length, Direction d) {
        int wholeLength = board.getSideLength() * board.getTileSideLength();
        Line line = new Line();
        if (!Arrays.asList(Direction.getLineDirections()).contains(d)) {
            throw new IllegalArgumentException("The direction is incorrect!");
        }
        int firstIndexFrom = 0;
        int firstIndexTo = wholeLength;

        if (d == Direction.UPGRADING_DIAGONAL) {
            firstIndexFrom = -wholeLength + 1;
        } else if (d == Direction.DOWNGRADING_DIAGONAL) {
            firstIndexTo = 2 * wholeLength - 1;
        }

        Marble lastMarble;
        for (int i = firstIndexFrom; i < firstIndexTo; i++) {
            line.clear();
            lastMarble = null;
            for (int j = 0; j < wholeLength; j++) {
                int firstCoord = -1;
                int secondCoord = -1;
                if (d == Direction.HORIZONTAL) {
                    firstCoord = j;
                    secondCoord = i;
                } else if (d == Direction.VERTICAL) {
                    firstCoord = i;
                    secondCoord = j;
                } else if (d == Direction.UPGRADING_DIAGONAL) {
                    firstCoord = j;
                    secondCoord = i + j;
                } else if (d == Direction.DOWNGRADING_DIAGONAL) {
                    firstCoord = i - j;
                    secondCoord = j;
                }
                if (firstCoord >= 0 && firstCoord < wholeLength && secondCoord >= 0 && secondCoord < wholeLength) {
                    Marble m = board.getMarble(firstCoord, secondCoord);
                    if(m == null || (!m.equals(lastMarble) && lastMarble != null)) {
                        line.clear();
                    }
                    if(m != null) {
                        line.addCoordinate(new Integer[]{firstCoord, secondCoord});
                        if (line.length() >= length) {
                            line.setSymbol(m.getSymbol());
                            return line;
                        }
                    }
                    lastMarble = m;
                }
            }
        }
        return null;
    }
    
    public Board getBoard() {
        return board;
    }
}
