
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
     * Calls the constructor with the default values 2 as sideLength and 3 as tileSideLength.
     */
    public Board() {
        this(2, 3);
    }
    
    /**
     * Gives the Tile in the given coordinates.
     * Throws IllegalArgumentException if the coordinates are invalid.
     * 
     * @param tileX the X coordinate of the Tile
     * @param tileY the Y coordinate of the Tile
     * @return the Tile
     */
    public Tile getTile(int tileX, int tileY) {
        if (tileX < 0 || tileX > sideLength || tileY < 0 || tileY > sideLength) {
            throw new IllegalArgumentException("Invalid coordinates. X: " + tileX + ", Y: " + tileY);
        }
        return getTiles()[tileY][tileX];
    }
    
    public boolean validateTileCoordinates(int tileX, int tileY) {
        try {
            getTile(tileX, tileY);
            return true;
        } catch (Exception e) {
            return false;
        }
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
        return tile.setMarble(marble, x % tileSideLength, y % tileSideLength);
    }
    
    /**
     * Gives the Marble in the given place.
     * Returns null if the place is empty.
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
     * Removes and gives the Marble in the given place.
     * Returns null if the place is empty.
     * Throws IllegalArgumentException if the coordinates are invalid.
     * 
     * @param x the X coordinate
     * @param y the Y coordinate
     * @return the removed Marble
     */
    public Marble removeMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.removeMarble(x % tileSideLength, y % tileSideLength);
    }
    
    /**
     * @return sideLength
     */
    public int getSideLength() {
        return sideLength;
    }
    
    /**
     * @return tileSideLength
     */
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
    }
    
    /**
     * @return tiles
     */
    protected Tile[][] getTiles() {
        return tiles;
    }
    
    /**
     * @return lastRotatedTile;
     */
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
    
    public Marble[][] toMarbleArray() {
        Marble[][] mArr = new Marble[sideLength * tileSideLength][sideLength * tileSideLength];
        for(int tileY = 0; tileY < tiles.length; tileY++) {
            for(int tileX = 0; tileX < tiles[0].length; tileX++) {
                for(int marbleX = 0; marbleX < tiles[tileY][tileX].getTile().length; marbleX++) {
                    for(int marbleY = 0; marbleY < tiles[tileY][tileX].getTile().length; marbleY++) {
                        int y = tileY * tileSideLength + marbleY;
                        int x = tileX * tileSideLength + marbleX;
                        mArr[y][x] = tiles[tileY][tileX].getTile()[marbleY][marbleX];
                    }
                }
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
                if ((i + 1) % t.length == 0) {
                    result += "\n";
                }
            }
        }
        return result;
    }
}
