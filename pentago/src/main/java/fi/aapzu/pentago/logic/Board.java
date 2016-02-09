
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The board of the Pentago. Contains Tiles and Marbles.
 * 
 * @author Aapeli
 */
public class Board {
    
    private int sideLength;
    private int tileSideLength;
    private Tile[][] tiles;
    
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
        for(int y = 0; y < sideLength; y++) {
            for(int x = 0; x < sideLength; x++) {
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
    protected Tile getTile(int tileX, int tileY) {
        if(tileX < 0 || tileX > sideLength || tileY < 0 || tileY > sideLength)
            throw new IllegalArgumentException("Invalid coordinates. X: " + tileX + ", Y: " + tileY);
        return getTiles()[tileY][tileX];
    }
    
    /**
     * Return the Tile, in which the given coordinates for a Marble are.
     * 
     * @param marbleX the X coordinate of the Marble
     * @param marbleY the Y coordinate of the Marble
     * @return the Tile
     */
    protected Tile getTileByCoordinates(int marbleX, int marbleY) {
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
        tiles[tileY][tileX].rotate(d);
    }
    
    /**
     * @return tiles
     */
    protected Tile[][] getTiles() {
        return tiles;
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
     * Calls checkLines(length, d) with all the valid Directions
     * (HORIZONTAL, VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL).
     * 
     * @param length the length to be given to checkLines
     * @return the line found or null
     */
    public Map<String, Object> checkLines(int length) {
        if(length < 2 || length > sideLength*tileSideLength) {
            throw new IllegalArgumentException("The length of a line must be between 2 and "+sideLength*tileSideLength);
        }
        Map<String, Object> line = null;
        
        for(Direction d : Direction.getLineDirections()) {
            line = checkLines(length, d);
            if(line != null)
                break;
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
        if(!Arrays.asList(Direction.getLineDirections()).contains(d))
            throw new IllegalArgumentException("The direction is incorrect!");
        
        int firstIndexFrom = 0;
        int firstIndexTo = sideLength * tileSideLength;
        
        if(d == Direction.UPGRADING_DIAGONAL){
            firstIndexFrom = -(sideLength * tileSideLength) + 1;
        } else if(d == Direction.DOWNGRADING_DIAGONAL)
            firstIndexTo = 2*sideLength * tileSideLength - 1;
        
        Map<String, Object> line = new HashMap<>();
        line.put("symbol", null);
        line.put("coordinates", new ArrayList<>());
        int counter = 0;
        Marble lastMarble;
        for(int i = firstIndexFrom; i < firstIndexTo; i++) {
            lastMarble = null;
            for(int j = 0; j < sideLength * tileSideLength; j++) {
                int firstCoord = -1;
                int secondCoord = -1;
                if(d == Direction.HORIZONTAL) {
                    firstCoord = j;
                    secondCoord = i;
                } else if(d == Direction.VERTICAL) {
                    firstCoord = i;
                    secondCoord = j;
                } else if(d == Direction.UPGRADING_DIAGONAL) {
                    firstCoord = j;
                    secondCoord = i + j;
                } else if(d == Direction.DOWNGRADING_DIAGONAL) {
                    firstCoord = i - j;
                    secondCoord = j;
                }
                if(firstCoord >= 0 && firstCoord < sideLength * tileSideLength && secondCoord >= 0 && secondCoord < sideLength * tileSideLength) {
                    Marble m = getMarble(firstCoord,secondCoord);
                    ((ArrayList<Integer[]>)line.get("coordinates")).add(new Integer[]{firstCoord, secondCoord});
                
                    if(lastMarble != null || m == null) {
                        if(m != null && m.equals(lastMarble)) {
                            counter++;
                            if(counter >= length-1) {
                                line.put("symbol", m.getSymbol());
                                return line;
                            }
                        } else {
                            counter = 0;
                            ((ArrayList)line.get("coordinates")).clear();
                        }
                    }

                    lastMarble = m;
                }
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        String result = "";
        for(Tile[] t : tiles) {
            for(int i = 0; i < t.length * t[0].getSideLength(); i++) {
                result += t[i % t.length].rowToString(i / t.length);
                if((i + 1) % t.length == 0)
                    result += "\n";
            }
        }
        return result;
    }
}
