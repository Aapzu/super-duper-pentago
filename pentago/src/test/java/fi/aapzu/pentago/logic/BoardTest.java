/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
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
  
    @Rule
    public final ExpectedException exception = ExpectedException.none();
        
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
        for (Tile[] tA : board.getTiles()) {
            for (Tile t : tA) {
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
    public void getTileThrowsErrorIfCoordinatesAreInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid coordinates");
        board.getTile(-1,0);
        board.getTile(0,-1);
        board.getTile(0,2);
        board.getTile(2,0);
        board.getTile(2,-1);
        board.getTile(-1,2);
    }
    
    @Test
    public void getTileByCoordinatesReturnsRightTile() {
        Map<Integer, Tile> tiles = new HashMap<>();
        tiles.put(0, board.getTiles()[0][0]);
        tiles.put(1, board.getTiles()[0][1]);
        tiles.put(2, board.getTiles()[1][0]);
        tiles.put(3, board.getTiles()[1][1]);
        
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(0));
            }
        }
        
        for (int y = 0; y < 3; y++) {
            for (int x = 3; x < 6; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(1));
            }
        }
        
        for (int y = 3; y < 6; y++) {
            for (int x = 0; x < 3; x++) {
                assertEquals(board.getTileByCoordinates(x, y), tiles.get(2));
            }
        }
        
        for (int y = 3; y < 6; y++) {
            for (int x = 3; x < 6; x++) {
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
    public void addMarbleThrowsExceptionIfTheCoordinatesAreInvalid() {
        exception.expect(IllegalArgumentException.class);
        board.addMarble(new Marble(Symbol.O), -2, 0);
        exception.expect(IllegalArgumentException.class);
        board.addMarble(new Marble(Symbol.O), 10, 10);
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
    public void removeMarbleThrowsExceptionIfInvalidCoordinates() {
        exception.expect(IllegalArgumentException.class);
        board.removeMarble(-3, 0);
        exception.expect(IllegalArgumentException.class);
        board.removeMarble(10, 10);
    }
    
    @Test
    public void removeMarbleReturnsNullIfMarbleNotFound() {
        assertNull(board.removeMarble(0, 0));
        assertNull(board.removeMarble(2, 1));
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
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        assertEquals("[null, null, [X]][null, null, null]\n" +
                "[null, [O], null][null, [X], null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[[X], null, null][null, null, null]\n" +
                "[null, [O], null][null, [O], null]\n" +
                "[null, null, null][null, null, null]\n", board.toString());
        
        board.rotateTile(0, 1, Direction.COUNTER_CLOCKWISE);
        assertEquals("[null, null, [X]][null, null, null]\n" +
                "[null, [O], null][null, [X], null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[null, null, null][null, null, null]\n" +
                "[null, [O], null][null, [O], null]\n" +
                "[[X], null, null][null, null, null]\n", board.toString());
    }    

    @Test
    public void getLastDirectionReturnsTheRightDirection() {
        assertNull(board.getLastDirection(0, 0));
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        assertEquals(Direction.CLOCKWISE, board.getLastDirection(0, 0));
        board.rotateTile(0, 0, Direction.COUNTER_CLOCKWISE);
        assertEquals(Direction.COUNTER_CLOCKWISE, board.getLastDirection(0, 0));
        board.rotateTile(1, 1, Direction.CLOCKWISE);
        assertEquals(Direction.CLOCKWISE, board.getLastDirection(1, 1));
        assertEquals(Direction.COUNTER_CLOCKWISE, board.getLastDirection(0, 0));
    }
}
