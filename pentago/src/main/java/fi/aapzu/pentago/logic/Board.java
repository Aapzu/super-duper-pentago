package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.util.Serializable;
import fi.aapzu.pentago.util.iterator.BoardTileIterator;

import java.util.Arrays;

/**
 * The board of the Pentago. Contains Tiles and Marbles.
 *
 * @author Aapeli
 */
public class Board implements Serializable {

    private final int sideLength;
    private final int tileSideLength;
    private final int totalSideLength;

    private Tile[][] tiles;

    private Tile lastRotatedTile;
    private int lastRotatedTileY;
    private int lastRotatedTileX;
    private Direction lastRotatedTileDirection;

    private Marble lastMarble;
    private int lastMarbleY;
    private int lastMarbleX;

    /**
     * Copy-constructor to clone the Board.
     *
     * @param other Board to be cloned
     */
    public Board(Board other) {
        this(other.getSideLength(), other.getTileSideLength());

        Tile[][] newTiles = new Tile[getSideLength()][getSideLength()];
        for (int tileY = 0; tileY < getSideLength(); tileY++) {
            for (int tileX = 0; tileX < getSideLength(); tileX++) {
                newTiles[tileY][tileX] = new Tile(other.getTiles()[tileY][tileX]);
            }
        }
        setTiles(newTiles);
        setLastRotatedTile(other.getLastRotatedTile());
        setLastRotatedTileY(other.getLastRotatedTileY());
        setLastRotatedTileX(other.getLastRotatedTileX());
        setLastRotatedTileDirection(other.getLastRotatedTileDirection());
        setLastMarbleX(other.getLastMarbleX());
        setLastMarbleY(other.getLastMarbleY());
        setLastMarble(other.getLastMarble());
    }

    /**
     * Creates the Board, and the Tiles in it.
     *
     * @param sideLength     the amount of the Tiles per side
     * @param tileSideLength the amount of the Marbles per side of a Tile
     */
    public Board(int sideLength, int tileSideLength) {
        this.sideLength = sideLength;
        this.tileSideLength = tileSideLength;
        this.totalSideLength = this.sideLength * this.tileSideLength;
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
    Tile getTileByCoordinates(int marbleX, int marbleY) {
        return getTile(marbleX / tileSideLength, marbleY / tileSideLength);
    }

    /**
     * Adds a Marble to the Board to the given place.
     *
     * @param marble the Marble to be set to the Board.
     * @param x      the X coordinate
     * @param y      the Y coordinate
     * @return true if succeeded, otherwise false
     */
    public boolean addMarble(Marble marble, int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        setLastMarble(marble);
        setLastMarbleX(x);
        setLastMarbleY(y);
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
        return tile.getMarble(x % tileSideLength, y % tileSideLength);
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
    Marble removeMarble(int x, int y) {
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
     * @param d     the Direction
     */
    public void rotateTile(int tileX, int tileY, Direction d) {
        if (!Arrays.asList(Direction.getRotateDirections()).contains(d)) {
            throw new IllegalArgumentException("Invalid direction!");
        }
        Tile t = getTile(tileX, tileY);
        t.rotate(d);
        setLastRotatedTile(t);
        setLastRotatedTileX(tileX);
        setLastRotatedTileY(tileY);
        setLastRotatedTileDirection(d);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getLastRotatedTile() {
        return lastRotatedTile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;
        return sideLength == board.sideLength &&
            tileSideLength == board.tileSideLength &&
            lastRotatedTileY == board.lastRotatedTileY &&
            lastRotatedTileX == board.lastRotatedTileX &&
            ((lastRotatedTile == null && board.getLastRotatedTile() == null) ||
                lastRotatedTile.equals(board.getLastRotatedTile())) &&
            ((lastMarble == null && board.getLastMarble() == null) ||
                lastMarble.equals(board.getLastMarble())) &&
            lastMarbleX == board.getLastMarbleX() &&
            lastMarbleY == board.getLastMarbleY() &&
            Arrays.deepEquals(tiles, board.tiles) &&
            lastRotatedTileDirection == board.lastRotatedTileDirection;
    }

    /**
     * Gives the Marbles on the Board in a single 2d array
     *
     * @return array of Marbles
     */
    Marble[][] getMarbleArray() {
        Marble[][] marbles = new Marble[getTotalSideLength()][getTotalSideLength()];
        for (int y = 0; y < getTotalSideLength(); y++) {
            for (int x = 0; x < getTileSideLength(); x++) {
                marbles[y][x] = getMarble(x, y);
            }
        }
        return marbles;
    }

    public Direction getLastRotatedTileDirection() {
        return lastRotatedTileDirection;
    }

    public int getLastRotatedTileY() {
        return lastRotatedTileY;
    }

    public int getLastRotatedTileX() {
        return lastRotatedTileX;
    }

    public int getLastMarbleY() {
        return lastMarbleY;
    }

    public int getLastMarbleX() {
        return lastMarbleX;
    }

    public Marble getLastMarble() {
        return lastMarble;
    }

    void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    private void setLastRotatedTile(Tile lastRotatedTile) {
        this.lastRotatedTile = lastRotatedTile;
    }

    private void setLastRotatedTileDirection(Direction lastRotatedTileDirection) {
        this.lastRotatedTileDirection = lastRotatedTileDirection;
    }

    private void setLastRotatedTileY(int lastRotatedTileY) {
        this.lastRotatedTileY = lastRotatedTileY;
    }

    private void setLastRotatedTileX(int lastRotatedTileX) {
        this.lastRotatedTileX = lastRotatedTileX;
    }

    private void setLastMarbleY(int lastMarbleY) {
        this.lastMarbleY = lastMarbleY;
    }

    private void setLastMarbleX(int lastMarbleX) {
        this.lastMarbleX = lastMarbleX;
    }

    private void setLastMarble(Marble lastMarble) {
        this.lastMarble = lastMarble;
    }

    @Override
    public String serialize() {
        String s = "";
        BoardTileIterator it = new BoardTileIterator(this);
        while (it.hasNext()) {
            s += it.next().serialize();
        }
        s += getLastMarbleX();
        s += getLastMarbleY();
        s += getLastRotatedTileX();
        s += getLastRotatedTileY();
        s += getLastRotatedTileDirection() == Direction.CLOCKWISE ? 1 : (getLastRotatedTileDirection() == Direction.COUNTER_CLOCKWISE ? 2 : 0);
        return s;
    }

    @Override
    public boolean deserialize(String s) {
        if (s.length() == 41) {
            setLastRotatedTileDirection(s.charAt(40) != '0' ? (s.charAt(40) == '1' ? Direction.CLOCKWISE : Direction.COUNTER_CLOCKWISE) : null);
            setLastRotatedTileY(Integer.parseInt(Character.toString(s.charAt(39))));
            setLastRotatedTileX(Integer.parseInt(Character.toString(s.charAt(38))));
            setLastMarbleX(Integer.parseInt(Character.toString(s.charAt(36))));
            setLastMarbleY(Integer.parseInt(Character.toString(s.charAt(37))));
            BoardTileIterator it = new BoardTileIterator(this);
            while (it.hasNext()) {
                Tile t = it.next();
                int n = it.getY() * getSideLength() + it.getX();
                int i1 = n * getTileSideLength() * getTileSideLength();
                int i2 = i1 + getTileSideLength() * getTileSideLength();
                if (!t.deserialize(s.substring(i1, i2))) {
                    return false;
                }
            }
            setLastRotatedTile(getTile(getLastRotatedTileX(), getLastRotatedTileY()));
            setLastMarble(getMarble(getLastMarbleX(), getLastMarbleY()));
            return true;
        }
        return false;
    }

    public int getTotalSideLength() {
        return totalSideLength;
    }
}
