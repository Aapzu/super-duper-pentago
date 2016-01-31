/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Tile;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


public class BoardTest {
    
    Board board;
        
    public BoardTest() {}
    
    @Before
    public void setUp() {
        board = new Board();
    }
    
    @After
    public void tearDown() {
        board = null;
    }
    
    @Test
    public void constructorSetsTheValuesCorrectly() {
        board = new Board(5, 10);
        assertEquals(board.getSideLength(), 5);
        assertEquals(board.getTiles().length, 5);
        assertEquals(board.getTiles()[0].length, 5);
        for(Tile[] tA : board.getTiles()) {
            for(Tile t : tA) {
                assertNotNull(t);
                assertEquals(t.getTile().length, 10);
                assertEquals(t.getTile()[0].length, 10);
            }
        }
   
    }

    @Test
    public void constructorWithoutParametersMustUseRightDefaultValues() {
        assertEquals(board.getSideLength(), 2);
        assertEquals(board.getTiles()[0][0].getSideLength(), 3);
    }
    
    @Test
    public void getTileByCoordinatesReturnsRightTile() {
        Map<Integer, Tile> tiles = new HashMap<>();
        tiles.put(0, board.getTiles()[0][0]);
        tiles.put(1, board.getTiles()[0][1]);
        tiles.put(2, board.getTiles()[1][0]);
        tiles.put(3, board.getTiles()[1][1]);
        
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(0));
            }
        }
        
        for(int y = 0; y < 3; y++) {
            for(int x = 3; x < 6; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(1));
            }
        }
        
        for(int y = 3; y < 6; y++) {
            for(int x = 0; x < 3; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(2));
            }
        }
        
        for(int y = 3; y < 6; y++) {
            for(int x = 3; x < 6; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(3));
            }
        }
    }
    
    @Test
    public void addMarbleAddsMarbleToRightPlace() {
        Marble m = new Marble(Symbol.X);
        board.addMarble(m, 0, 0);
        assertEquals(board.getTileByCoordinates(0, 0)
                        .getTile()[0][0], m);
        m = new Marble(Symbol.O);
        board.addMarble(m, 5, 1);
        assertEquals(board.getTileByCoordinates(5, 1)
                        .getTile()[1][2], m);
        m = new Marble(Symbol.X);
        board.addMarble(m, 1, 4);
        assertEquals(board.getTileByCoordinates(1, 4)
                        .getTile()[1][1],m);
        m = new Marble(Symbol.O);
        board.addMarble(m, 4, 4);
        assertEquals(board.getTileByCoordinates(4, 4)
                        .getTile()[1][1],m);
    }
    
    @Test
    public void addMarbleReturnsTrueIfTheCoordinatesAreRight() {
        assertTrue(board.addMarble(new Marble(Symbol.O), 0, 0));
    }
    
    @Test
    public void addMarbleReturnsFalseIfTheCoordinatesAreFalse() {
        assertFalse(board.addMarble(new Marble(Symbol.O), -2, 0));
        assertFalse(board.addMarble(new Marble(Symbol.O), 10, 10));
    }
    
    @Test
    public void removeMarbleRemovesMarbleFromRightPlace() {
        board.addMarble(new Marble(Symbol.X), 0, 0);
        board.removeMarble(0, 0);
        assertEquals(board.getTileByCoordinates(0, 0)
                        .getTile()[0][0], null);
        board.addMarble(new Marble(Symbol.O), 5, 1);
        board.removeMarble(5, 1);
        assertEquals(board.getTileByCoordinates(5, 1)
                        .getTile()[1][2], null);
    }
    
    @Test
    public void removeMarbleReturnsRightMarble() {
        Marble m = new Marble(Symbol.X);
        board.addMarble(m, 0, 0);
        assertEquals(board.removeMarble(0, 0), m);
    }
    
    @Test
    public void removeMarbleReturnsNullIfMarbleNotFound() {
        assertNull(board.removeMarble(0, 0));
        assertNull(board.removeMarble(-3, 0));
        assertNull(board.removeMarble(10, 10));
    }
    
    @Test
    public void toStringReturnsRightKindOfString() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        board.addMarble(x, 0, 0);
        board.addMarble(o, 1, 1);
        board.addMarble(x, 4, 1);
        board.addMarble(x, 0, 3);
        board.addMarble(o, 1, 4);
        board.addMarble(o, 4, 4);
        
        assertEquals("[[X], null, null][null, null, null]\n" +
                "[null, [O], null][null, [X], null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[[X], null, null][null, null, null]\n" +
                "[null, [O], null][null, [O], null]\n" +
                "[null, null, null][null, null, null]\n", board.toString());
    }
    
    @Test
    public void rotateTileWorks() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        board.addMarble(x, 0, 0);
        board.addMarble(o, 1, 1);
        board.addMarble(x, 0, 3);
        board.addMarble(o, 1, 4);
        board.addMarble(x, 4, 1);
        board.addMarble(o, 4, 4);
        board.rotateClockWise(0, 0);
        assertEquals("[null, null, [X]][null, null, null]\n" +
                "[null, [O], null][null, [X], null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[[X], null, null][null, null, null]\n" +
                "[null, [O], null][null, [O], null]\n" +
                "[null, null, null][null, null, null]\n", board.toString());
        
        board.rotateCounterClockWise(0, 1);
        assertEquals("[null, null, [X]][null, null, null]\n" +
                "[null, [O], null][null, [X], null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[null, [O], null][null, [O], null]\n" +
                "[[X], null, null][null, null, null]\n", board.toString());
    }
    
}
