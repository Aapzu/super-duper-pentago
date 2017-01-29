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
    Tile() {
        this(3);
    }

    /**
     * Creates the Tile with the given sideLength (for the Marbles). Throws
     * IllegalArgumentException if the sideLength is negative or 0. Sets the
     * lastDirection to null.
     *
     * @param sideLength the amount of Marbles per side
     */
    Tile(int sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The sideLength of a Tile must be truly positive!");
        }
        this.sideLength = sideLength;
        tile = new Marble[sideLength][sideLength];
        lastDirection = null;
    }

    /**
     * Copy-constructor to clone the Tile
     *
     * @param other the Tile to be cloned
     */
    Tile(Tile other) {
        setSideLength(other.getSideLength());
        setLastDirection(other.getLastDirection());
        Marble[][] newTile = new Marble[getSideLength()][getSideLength()];
        for (int y = 0; y < getSideLength(); y++) {
            for (int x = 0; x < getSideLength(); x++) {
                newTile[y][x] = other.getTile()[y][x] != null ? new Marble(other.getTile()[y][x]) : null;
            }
        }
        setTile(other.getTile());
    }

    private boolean validateCoordinates(int x, int y) {
        return x >= 0 && x < tile[0].length && y >= 0 && y < tile.length;
    }

    /**
     * Gives the Marble in the given coordinates. Returns null if the
     * coordinates are invalid or there is no Marble in the specified place.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the Marble or null
     */
    public Marble getMarble(int x, int y) {
        if (validateCoordinates(x, y)) {
            return getTile()[y][x];
        } else {
            return null;
        }
    }

    /**
     * Sets the given Marble to the given place. Returns false if the
     * coordinates are invalid. Throws PentagoGameRuleException if there is
     * already a Marble in the specified place.
     *
     * @param marble the Marble to be added
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return true if succeeded, otherwise false
     */
    protected boolean addMarble(Marble marble, int x, int y) {
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
     * Removes and gives the marble in the specified place. If the coordinates
     * are invalid, or the place is empty, returns null.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the removed Marble or null
     */
    protected Marble removeMarble(int x, int y) {
        if (validateCoordinates(x, y)) {
            Marble marble = tile[y][x];
            addMarble(null, x, y);
            return marble;
        } else {
            return null;
        }
    }

    /**
     * Rotates the Tile to the given Direction. Throws exception if the
     * Direction is not CLOCKWISE or COUNTER_CLOCKWISE. Sets the Direction for
     * the lastDirection.
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
    String rowToString(int rowNumber) {
        String row = "";
        for (Marble m : tile[rowNumber]) {
            row += m != null ? m.toString() : "[ ]";
        }
        return row;
    }

    int getSideLength() {
        return sideLength;
    }

    void setSideLength(int sideLength) {
        this.sideLength = sideLength;
    }

    protected Marble[][] getTile() {
        return tile;
    }

    public void setTile(Marble[][] tile) {
        this.tile = tile;
    }

    Direction getLastDirection() {
        return lastDirection;
    }

    private void setLastDirection(Direction d) {
        lastDirection = d;
    }

    /**
     * Removes every Marble.
     */
    protected void clear() {
        for (int y = 0; y < getTile().length; y++) {
            for (int x = 0; x < getTile()[0].length; x++) {
                addMarble(null, x, y);
            }
        }
    }

    /**
     * Tells if the Tile has only empty squares in it.
     *
     * @return true or false
     */
    protected boolean isEmpty() {
        for (Marble[] row : tile) {
            for (Marble m : row) {
                if (m != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tells if the Tile has no any empty squares in it.
     *
     * @return true or false
     */
    boolean isFull() {
        for (Marble[] row : tile) {
            for (Marble m : row) {
                if (m == null) {
                    return false;
                }
            }
        }
        return true;
    }

    Marble[][] toMarbleArray() {
        return tile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Tile)) {
            return false;
        }
        Tile that = (Tile)obj;
        return Arrays.equals(that.getTile(), getTile()) && that.getLastDirection() == getLastDirection();
    }

    @Override
    public int hashCode() {
        int result = sideLength;
        result = 31 * result + Arrays.deepHashCode(tile);
        return result;
    }
}
