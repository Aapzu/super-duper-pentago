
package fi.pentago.logic;

import fi.pentago.logic.marble.Marble;

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
    
    public boolean setMarble(Marble marble, int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.setMarble(marble, x % tileSideLength, y % tileSideLength);
    }
    
    public Marble removeMarble(int x, int y) {
        Tile tile = getTileByCoordinates(x, y);
        return tile.removeMarble(x % tileSideLength, y % tileSideLength);
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
