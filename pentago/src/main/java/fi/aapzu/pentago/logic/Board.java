
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board {
    
    private int sideLength;
    private int tileSideLength;
    private Tile[][] tiles;
    
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
    
    public Board() {
        this(2, 3);
    }
    
    protected Tile getTile(int tileX, int tileY) {
        if(tileX < 0 || tileX > sideLength || tileY < 0 || tileY > sideLength)
            throw new IllegalArgumentException("Invalid coordinates. X: " + tileX + ", Y: " + tileY);
        return getTiles()[tileY][tileX];
    }
    
    protected Tile getTileByCoordinates(int marbleX, int marbleY) {
        return getTile(marbleX / tileSideLength, marbleY / tileSideLength);
    }
    
    public boolean addMarble(Marble marble, int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.setMarble(marble, x % tileSideLength, y % tileSideLength);
    }
    
    public Marble getMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.get(x % tileSideLength, y % tileSideLength);
    }
    
    public Marble removeMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.removeMarble(x % tileSideLength, y % tileSideLength);
    }
    
    public int getSideLength() {
        return sideLength;
    }
    
    public void rotateTile(int tileX, int tileY, Direction d) {
        tiles[tileY][tileX].rotate(d);
    }
    
    protected Tile[][] getTiles() {
        return tiles;
    }
    
    public Direction getLastDirection(int tileX, int tileY) {
        return getTile(tileX, tileY).getLastDirection();
    }
    
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
