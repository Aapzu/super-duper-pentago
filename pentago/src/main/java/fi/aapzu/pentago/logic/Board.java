
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
        
        line = checkLinesHorizontally(length);
        if(line != null)
            return line;
        
        line = checkLinesVertically(length);
        if(line != null)
            return line;
        
        line = checkLinesDiagonally(length);
        if(line != null)
            return line;
        
        return null;
    }
    
    private Map<String, Object> checkLinesHorizontally(int length) {
        Map<String, Object> line = new HashMap<String, Object>();
        line.put("symbol", null);
        line.put("coordinates", new ArrayList<Integer[]>());
        int counter = 0;
        Marble lastMarble;
        for(int y = 0; y < sideLength * tileSideLength; y++) {
            lastMarble = null;
            for(int x = 0; x < sideLength * tileSideLength; x++) {
                Marble m = getMarble(x,y);
                ((ArrayList<Integer[]>)line.get("coordinates")).add(new Integer[]{x, y});
                if(lastMarble != null)
                    if(m != null && lastMarble.equals(m)) {
                        counter++;
                        if(counter >= length-1) {
                            line.put("symbol", m.getSymbol());
                            return line;
                        }
                    } else {
                        counter = 0;
                        ((ArrayList)line.get("coordinates")).clear();
                    }
                lastMarble = m;
            }
        }
        return null;
    }
    
    private Map<String, Object> checkLinesVertically(int length) {
        Map<String, Object> line = new HashMap<String, Object>();
        line.put("symbol", null);
        line.put("coordinates", new ArrayList<Integer[]>());
        int counter = 0;
        Marble lastMarble;
        for(int x = 0; x < sideLength * tileSideLength; x++) {
            lastMarble = null;
            for(int y = 0; y < sideLength * tileSideLength; y++) {
                Marble m = getMarble(x,y);
                ((ArrayList<Integer[]>)line.get("coordinates")).add(new Integer[]{x, y});
                if(lastMarble != null)
                    if(m != null && lastMarble.equals(m)) {
                        counter++;
                        if(counter >= length-1) {
                            line.put("symbol", m.getSymbol());
                            return line;
                        }
                    } else {
                        counter = 0;
                        ((ArrayList)line.get("coordinates")).clear();
                    }
                lastMarble = m;
            }
        }
        return null;
    }
    
    // TODO: Combine these ->
    private Map<String, Object> checkLinesDiagonally(int length) {
        Map<String, Object> line = new HashMap<String, Object>();
        line.put("symbol", null);
        line.put("coordinates", new ArrayList<Integer[]>());
        int counter = 0;
        Marble lastMarble;
        for(int x = -(sideLength * tileSideLength) + 1; x < sideLength * tileSideLength; x++) {
            lastMarble = null;
            for(int y = 0; y < sideLength * tileSideLength; y++) {
                if(x+y >= 0 && x+y < sideLength * tileSideLength) {
                    Marble m = getMarble(x+y,y);
                    ((ArrayList<Integer[]>)line.get("coordinates")).add(new Integer[]{x+y, y});
                    if(lastMarble != null)
                        if(m != null && lastMarble.equals(m)) {
                            counter++;
                            if(counter >= length-1) {
                                line.put("symbol", m.getSymbol());
                                return line;
                            }
                        } else{
                            counter = 0;
                            ((ArrayList)line.get("coordinates")).clear();
                        }
                    lastMarble = m;
                }
            }
        }
        for(int y = -(sideLength * tileSideLength) + 1; y < sideLength * tileSideLength; y++) {
            lastMarble = null;
            for(int x = 0; x < sideLength * tileSideLength; x++) {
                if(x+y >= 0 && x+y < sideLength * tileSideLength) {
                    Marble m = getMarble(x,x+y);
                    ((ArrayList<Integer[]>)line.get("coordinates")).add(new Integer[]{x, x+y});
                    if(lastMarble != null)
                        if(m != null && lastMarble.equals(m)) {
                            counter++;
                            if(counter >= length-1) {
                                line.put("symbol", m.getSymbol());
                                return line;
                            }
                        } else{
                            counter = 0;
                            ((ArrayList)line.get("coordinates")).clear();
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
