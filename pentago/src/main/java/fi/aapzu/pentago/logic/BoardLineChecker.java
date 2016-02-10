/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a class for checking for lines on a Board.
 * 
 * @author Aapeli
 */
public class BoardLineChecker {
    
    private Board board;
    
    public BoardLineChecker(Board board) {
        this.board = board;
    }
    
    /**
     * Calls checkLines(length, d) with all the valid Directions
     * (HORIZONTAL, VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL).
     * 
     * @param length the length to be given to checkLines
     * @return the line found or null
     */
    public Map<String, Object> checkLines(int length) {
        if (length < 2 || length > board.getSideLength() * board.getTileSideLength()) {
            throw new IllegalArgumentException("The length of a line must be between 2 and " + board.getSideLength() * board.getTileSideLength());
        }
        Map<String, Object> line = null;
        
        for (Direction d : Direction.getLineDirections()) {
            line = checkLines(length, d);
            if (line != null) {
                break;
            }
        }
        
        return line;
    }
    
    /**
     * Checks if there are the given amount of same symbol on the board in the given direction.
     * If a line was found, returns a map with the symbol and the coordinates of the line.
     * 
     * @param length the required amount of the same symbols in a row
     * @param d the Direction to be looked at
     * @return the line or null
     */
    protected Map<String, Object> checkLines(int length, Direction d) {
        if (!Arrays.asList(Direction.getLineDirections()).contains(d)) {
            throw new IllegalArgumentException("The direction is incorrect!");
        }
        int firstIndexFrom = 0;
        int firstIndexTo = board.getSideLength() * board.getTileSideLength();
        
        if (d == Direction.UPGRADING_DIAGONAL) {
            firstIndexFrom = -(board.getSideLength() * board.getTileSideLength()) + 1;
        } else if (d == Direction.DOWNGRADING_DIAGONAL) {
            firstIndexTo = 2 * board.getSideLength() * board.getTileSideLength() - 1;
        }
        
        Map<String, Object> line = new HashMap<>();
        line.put("symbol", null);
        line.put("coordinates", new ArrayList<>());
        int counter = 0;
        Marble lastMarble;
        for (int i = firstIndexFrom; i < firstIndexTo; i++) {
            lastMarble = null;
            for (int j = 0; j < board.getSideLength() * board.getTileSideLength(); j++) {
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
                if (firstCoord >= 0 && firstCoord < board.getSideLength() * board.getTileSideLength() && secondCoord >= 0 && secondCoord < board.getSideLength() * board.getTileSideLength()) {
                    Marble m = board.getMarble(firstCoord, secondCoord);
                    ((ArrayList<Integer[]>) line.get("coordinates")).add(new Integer[]{firstCoord, secondCoord});
                
                    if (lastMarble != null || m == null) {
                        if (m != null && m.equals(lastMarble)) {
                            counter++;
                        } else {
                            counter = 0;
                            ((ArrayList) line.get("coordinates")).clear();
                        }
                    }
                    if (counter >= length - 1) {
                        line.put("symbol", m.getSymbol());
                        return line;
                    }

                    lastMarble = m;
                }
            }
        }
        return null;
    }
}
