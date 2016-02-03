
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import java.util.ArrayList;
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
    
    protected Tile getTileByCoordinates(int x, int y) {
        return tiles[y / tileSideLength][x / tileSideLength];
    }
    
    public boolean addMarble(Marble marble, int x, int y) {
        try {
            Tile tile = getTileByCoordinates(x, y);
            return tile.setMarble(marble, x % tileSideLength, y % tileSideLength);
        } catch(ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    public Marble getMarble(int x, int y) {
        try {
            Tile tile = getTileByCoordinates(x, y);
            return tile.get(x % tileSideLength, y % tileSideLength);
        } catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public Marble removeMarble(int x, int y) {
        try {
            Tile tile = getTileByCoordinates(x, y);
            return tile.removeMarble(x % tileSideLength, y % tileSideLength);
        } catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public int getSideLength() {
        return sideLength;
    }
    
    public void rotateClockWise(int tileX, int tileY) {
        tiles[tileY][tileX].rotateClockWise();
    }
    
    public void rotateCounterClockWise(int tileX, int tileY) {
        tiles[tileY][tileX].rotateCounterClockWise();
    }
    
    protected Tile[][] getTiles() {
        return tiles;
    }
    
    public boolean booleanCheckLines(int length) {
        return checkLines(length) != null;
    }
    
    public Map<String, Object> checkLines(int length) {
        if(length < 2 || length > sideLength*tileSideLength) {
            throw new RuntimeException("The length of a line must be between 2 and "+sideLength*tileSideLength);
        }
        Map<String, Object> line;
        
        line = checkLinesHorizontally(length); // Direction: –
        if(line == null) {
            line = checkLinesVertically(length); // Direction: |
            if(line == null) {
                line = checkLinesDiagonally(length); // Direction: \
                if(line == null) {
                    line = checkLinesDiagonally2(length); // Direction: /
                }
            }
        }        
        return line;
    }
    
    protected Map<String, Object> checkLines(int length, int direction) {
        if(direction < 0 || direction > 3)
            throw new RuntimeException("The direction is incorrect!");
        
        int firstIndexFrom = 0;
        int firstIndexTo = sideLength * tileSideLength;
        if(direction == 2){
            firstIndexFrom = -(sideLength * tileSideLength) + 1;
        } else if( direction == 3)
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
                if(direction == 0) { // Horizontal
                    firstCoord = j;
                    secondCoord = i;
                } else if(direction == 1) { // Vertical
                    firstCoord = i;
                    secondCoord = j;
                } else if(direction == 2) { // Diagonal like \
                    firstCoord = j;
                    secondCoord = i + j;
                } else if(direction == 3) { // Diagonal like /
                    firstCoord = i - j;
                    secondCoord = j;
                }
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
        return null;
    }
    
    private Map<String, Object> checkLinesHorizontally(int length) {
        return checkLines(5, 0);
    }
    
    private Map<String, Object> checkLinesVertically(int length) {
        return checkLines(5, 1);
    }
    
    private Map<String, Object> checkLinesDiagonally(int length) {
        return checkLines(5,2);
    }
    
    private Map<String, Object> checkLinesDiagonally2(int length) {
        return checkLines(5,3);
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
