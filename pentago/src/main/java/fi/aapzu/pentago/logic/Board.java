package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.Arrays;

/**
 * The board of the Pentago. Contains Tiles and Marbles.
 *
 * @author Aapeli
 */
public class Board {

    private int sideLength;
    private int tileSideLength;
    private Tile[][] tiles;
    private Tile lastRotatedTile;
    private Direction lastRotatedTileDirection;
    private int lastRotatedTileY;
    private int lastRotatedTileX;

    /**
     * Creates the Board, and the Tiles in it.
     *
     * @param sideLength the amount of the Tiles per side
     * @param tileSideLength the amount of the Marbles per side of a Tile
     */
    public Board(int sideLength, int tileSideLength) {
        this.sideLength = sideLength;
        this.tileSideLength = tileSideLength;
        tiles = new Tile[sideLength][sideLength];
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                tiles[y][x] = new Tile(tileSideLength);
            }
        }
    }

    /**
     * Calls the constructor with the default values 2 as sideLength and 3 as
     * tileSideLength.
     */
    public Board() {
        this(2, 3);
    }

    /**
     * Gives the Tile in the given coordinates. Throws IllegalArgumentException
     * if the coordinates are invalid.
     *
     * @param tileX the X coordinate of the Tile
     * @param tileY the Y coordinate of the Tile
     * @return the Tile
     */
    public Tile getTile(int tileX, int tileY) {
        if (!validateTileCoordinates(tileX, tileY)) {
            throw new IllegalArgumentException("Invalid coordinates. X: " + tileX + ", Y: " + tileY);
        }
        return getTiles()[tileY][tileX];
    }

    /**
     * Validates the given tile coordinates.
     *
     * @param tileX x coordinate of the tile
     * @param tileY y coordinate of the tile
     * @return true if coordinates are valid, otherwise false
     */
    public boolean validateTileCoordinates(int tileX, int tileY) {
        return (tileX >= 0 && tileX < sideLength && tileY >= 0 && tileY < sideLength);
    }

    /**
     * Return the Tile, in which the given coordinates for a Marble are.
     *
     * @param marbleX the X coordinate of the Marble
     * @param marbleY the Y coordinate of the Marble
     * @return the Tile
     */
    public Tile getTileByCoordinates(int marbleX, int marbleY) {
        return getTile(marbleX / tileSideLength, marbleY / tileSideLength);
    }

    /**
     * Adds a Marble to the Board to the given place.
     *
     * @param marble the Marble to be set to the Board.
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return true if succeeded, otherwise false
     */
    public boolean addMarble(Marble marble, int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.addMarble(marble, x % tileSideLength, y % tileSideLength);
    }

    /**
     * Gives the Marble in the given place. Returns null if the place is empty.
     * Throws IllegalArgumentException if the coordinates are invalid.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the Marble
     */
    public Marble getMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.get(x % tileSideLength, y % tileSideLength);
    }

    /**
     * Removes and gives the Marble in the given place. Returns null if the
     * place is empty. Throws IllegalArgumentException if the coordinates are
     * invalid.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the removed Marble
     */
    public Marble removeMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.removeMarble(x % tileSideLength, y % tileSideLength);
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getTileSideLength() {
        return tileSideLength;
    }

    /**
     * Rotates the given Tile to the given Direction.
     *
     * @param tileX the X coordinate of the Tile.
     * @param tileY the Y coordinate of the Tile.
     * @param d the Direction
     */
    public void rotateTile(int tileX, int tileY, Direction d) {
        if (!Arrays.asList(Direction.getRotateDirections()).contains(d)) {
            throw new IllegalArgumentException("Invalid direction!");
        }
        Tile t = getTile(tileX, tileY);
        t.rotate(d);
        lastRotatedTile = t;
        lastRotatedTileX = tileX;
        lastRotatedTileY = tileY;
        lastRotatedTileDirection = d;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getLastRotatedTile() {
        return lastRotatedTile;
    }

    /**
     * Returns the last Direction the Tile in the given place was rotated to.
     *
     * @param tileX the X coordinate of the Tile.
     * @param tileY the Y coordinate of the Tile.
     * @return the Direction
     */
    public Direction getLastDirection(int tileX, int tileY) {
        return getTile(tileX, tileY).getLastDirection();
    }

    /**
     * Gives all the Marbles in a single 2d array.
     *
     * @return marbles
     */
    public Marble[][] toMarbleArray() {
        Marble[][] mArr = new Marble[sideLength * tileSideLength][sideLength * tileSideLength];
        for (int y = 0; y < sideLength * tileSideLength; y++) {
            for (int x = 0; x < sideLength * tileSideLength; x++) {
                mArr[y][x] = getMarble(x, y);
            }
        }
        return mArr;
    }

    @Override
    public String toString() {
        String result = "";
        for (Tile[] t : tiles) {
            for (int i = 0; i < t.length * t[0].getSideLength(); i++) {
                result += t[i % t.length].rowToString(i / t.length);
                if (i % t.length == t.length - 1) {
                    result += "\n";
                }
            }
        }
        return result;
    }

    /**
     * Clears the Board.
     */
    public void clear() {
        lastRotatedTile = null;
        for (int y = 0; y < getTiles().length; y++) {
            for (int x = 0; x < getTiles()[0].length; x++) {
                getTiles()[y][x].clear();
            }
        }
    }
    
    /**
     * Tells if the whole Board only has empty squares in it.
     * 
     * @return true or false
     */
    public boolean isEmpty() {
        for (Tile[] row : tiles) {
            for (Tile t : row) {
                if (!t.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Tells if the whole Board has no any empty squares in it.
     * 
     * @return true or false
     */
    public boolean isFull() {
        for (Tile[] row : tiles) {
            for (Tile t : row) {
                if (!t.isFull()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getLastRotatedTileY() {
        return lastRotatedTileY;
    }

    public int getLastRotatedTileX() {
        return lastRotatedTileX;
    }

    public String serialize() {
        String res = "";
        int sideLength = getSideLength() * getTileSideLength();
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                Marble m = getMarble(x, y);
                res += m != null ? m.toString() : " ";
            }
        }
        int lastRotatedTileNumber = getLastRotatedTileY() * getSideLength() + getLastRotatedTileX();
        int lastRotatedTileDirection = Direction.getRotateDirectionAsNumber(getLastDirection(getLastRotatedTileX(), getLastRotatedTileY()));
        res += lastRotatedTileNumber;
        res += lastRotatedTileDirection;
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (sideLength != board.sideLength) return false;
        if (tileSideLength != board.tileSideLength) return false;
        if (lastRotatedTileY != board.lastRotatedTileY) return false;
        if (lastRotatedTileX != board.lastRotatedTileX) return false;
        if (!Arrays.deepEquals(tiles, board.tiles)) return false;
        return lastRotatedTileDirection == board.lastRotatedTileDirection;
    }

    @Override
    public int hashCode() {
        int result = sideLength;
        result = 31 * result + tileSideLength;
        result = 31 * result + Arrays.deepHashCode(tiles);
        result = 31 * result + (lastRotatedTileDirection != null ? lastRotatedTileDirection.hashCode() : 0);
        result = 31 * result + lastRotatedTileY;
        result = 31 * result + lastRotatedTileX;
        return result;
    }
}
