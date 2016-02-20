
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import java.util.Arrays;

/**
 * One of the main instruments in the game. Can contain Marbles and be rotated.
 * 
 * @author Aapeli
 */
public class Tile {
    
    private int sideLength;
    private Marble[][] tile;
    private Direction lastDirection;
    
    /**
     * Calls the constructor with the default sideLength 3.
     */
    public Tile() {
        this(3);
    }
    
    /**
     * Creates the Tile with the given sideLength (for the Marbles).
     * Throws IllegalArgumentException if the sideLength is negative or 0.
     * Sets the lastDirection to null.
     * 
     * @param sideLength
     */
    public Tile(int sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The sideLength of a Tile must be truly positive!");
        }
        this.sideLength = sideLength;
        tile = new Marble[sideLength][sideLength];
        lastDirection = null;
    }
    
    private boolean validateCoordinates(int x, int y) {
        return  x >= 0 && x < tile[0].length && y >= 0 && y < tile.length;
    }
    
    /**
     * Gives the Marble in the given coordinates. 
     * Returns null if the coordinates are invalid or there is no Marble in the specified place.
     * 
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the Marble or null
     */
    protected Marble get(int x, int y) {
        if (validateCoordinates(x, y)) {
            return getTile()[y][x];
        } else {
            return null;
        }
    }
    
    /**
     * Sets the given Marble to the given place.
     * Returns false if the coordinates are invalid.
     * Throws PentagoGameRuleException if there is already a Marble in the specified place.
     * 
     * @param marble
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return true if succeeded, otherwise false
     */
    protected boolean setMarble(Marble marble, int x, int y) {
        if (validateCoordinates(x, y)) {
            // Return null when trying to set a marble on top of another one
            if (marble != null && tile[y][x] != null) {
                throw new PentagoGameRuleException("The square is not empty!");
            }
            tile[y][x] = marble;
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Removes and gives the marble in the specified place.
     * If the coordinates are invalid, or the place is empty, returns null.
     * 
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the removed Marble or null
     */
    protected Marble removeMarble(int x, int y) {
        if (validateCoordinates(x, y)) {
            Marble marble = tile[y][x];
            setMarble(null, x, y);
            return marble;
        } else {
            return null;
        }
    }
    
    /**
     * Rotates the Tile to the given Direction.
     * Throws exception if the Direction is not CLOCKWISE or COUNTER_CLOCKWISE.
     * Sets the Direction for the lastDirection.
     * 
     * @param d the Direction for the Tile to be rotated
     */
    protected void rotate(Direction d) {
        if (!Arrays.asList(Direction.getRotateDirections()).contains(d)) {
            throw new IllegalArgumentException("Invalid direction!");
        }
        setLastDirection(d);
        Marble[][] rotatedTile = new Marble[tile.length][tile[0].length];
        for (int y = 0; y < tile.length; y++) {
            for (int x = 0; x < tile[0].length; x++) {
                int newY = -1;
                int newX = -1;
                if (d == Direction.CLOCKWISE) {
                    newY = x;
                    newX = sideLength - y - 1;
                } else if (d == Direction.COUNTER_CLOCKWISE) {
                    newY = sideLength - x - 1;
                    newX = y;
                }
                rotatedTile[newY][newX] = tile[y][x];
            }
        }
        tile = rotatedTile;
    }
    
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < tile.length; i++) {
            result += rowToString(i);
            if (i != tile.length - 1) {
                result += "\n";
            }
        }
        return result;
    }
    
    /**
     * @param rowNumber the row to be toStringed
     * @return the String representation of only one row
     */
    protected String rowToString(int rowNumber) {
        return Arrays.toString(tile[rowNumber]);
    }
    
    /**
     * @return sideLength
     */
    protected int getSideLength() {
        return sideLength;
    }
    
    /**
     * @return tile
     */
    protected Marble[][] getTile() {
        return tile;
    }
    
    /**
     * @return lastDirection
     */
    protected Direction getLastDirection() {
        return lastDirection;
    }

    private void setLastDirection(Direction d) {
        lastDirection = d;
    }

    /**
     * Removes every Marble.
     */
    protected void clear() {
        for(int y = 0; y < getTile().length; y++) {
            for(int x = 0; x < getTile()[0].length; x++) {
                setMarble(null, x, y);
            }
        }
    }
}
