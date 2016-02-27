
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;


public class TileTest {
              
    Tile tile;
     
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    public TileTest() {}
    
    @Before
    public void setUp() {
        tile = new Tile();
    }
    
    @After
    public void tearDown() {
        tile = null;
    }

    @Test
    public void constructorWorksCorrectly() {
        tile = new Tile(5);
        assertEquals(tile.getSideLength(), 5);
        assertEquals(tile.getTile().length, 5);
        assertEquals(tile.getTile()[0].length, 5);
        tile = new Tile(15);
        assertEquals(tile.getSideLength(), 15);
        assertEquals(tile.getTile().length, 15);
        assertEquals(tile.getTile()[0].length, 15);
    }
    
    @Test
    public void constructorMustThrowExceptionIfSideLengthIsNegative() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("must be truly positive");
        tile = new Tile(-2);
    }
    
    @Test
    public void constructorMustThrowExceptionIfSideLengthIsZero() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("must be truly positive");
        tile = new Tile(0);
    }
    
    @Test
    public void constructorWithoutParameterUsesTheRightDefaultValue() {
        assertEquals(tile.getSideLength(), 3);
    }
    
    @Test
    public void setMarbleSetsRightMarbleIntoRightPlace() {
        Marble m = new Marble(Symbol.X);
        Marble m2 = new Marble(Symbol.O);
        assertTrue(tile.setMarble(m, 0, 0));
        assertTrue(tile.setMarble(m2, 1, 2));
        assertEquals(tile.getTile()[0][0].toString(), "[X]");
        assertEquals(tile.getTile()[2][1].toString(), "[O]");
    }
    
    @Test
    public void setMarbleDoesNotSetMarbleOutsideTheTile() {
        Marble m = new Marble(Symbol.X);
        assertFalse(tile.setMarble(m, -1, 2));
        assertFalse(tile.setMarble(m, 2, -1));
        assertFalse(tile.setMarble(m, 3, 0));
        assertFalse(tile.setMarble(m, 0, 3));
        assertFalse(tile.setMarble(m, 3, 15));
        assertFalse(tile.setMarble(m, 15, 2));
    }
    
    @Test
    public void setMarbleThrowsExceptionIfPlaceIsNotEmpty() {
        tile.setMarble(new Marble(Symbol.O), 1, 1);
        exception.expect(PentagoGameRuleException.class);
        tile.setMarble(new Marble(Symbol.O), 1, 1);
    }
    
    @Test
    public void getReturnsMarbleCorrectly() {
        Marble m = new Marble(Symbol.X);
        tile.setMarble(m, 0, 0);
        assertEquals(tile.get(0, 0), m);
    }
    
    @Test
    public void getReturnsNullIfThePlaceIsEmptyOrDoesNotExist() {
        assertNull(tile.get(0, 0));
        assertNull(tile.get(-1, 0));
        assertNull(tile.get(0, -1));
        assertNull(tile.get(-1, -1));
        assertNull(tile.get(-1, 10));
        assertNull(tile.get(10, -1));
        assertNull(tile.get(10, 10));
    }
    
    @Test
    public void removeMarbleRemovesMarble() {
        tile.setMarble(new Marble(Symbol.X), 0, 0);
        tile.removeMarble(0, 0);
        assertNull(tile.get(0,0));
    }
    
    @Test
    public void removeMarbleReturnsTheMarbleItRemoved() {
        Marble m = new Marble(Symbol.X);
        tile.setMarble(m, 0, 0);
        assertEquals(m, tile.removeMarble(0, 0));
    }
    
    @Test
    public void removeMarbleReturnsNullIfThePlaceIsEmptyOrDoesNotExist() {
        assertNull(tile.removeMarble(0, 0));
        assertNull(tile.removeMarble(-1, 0));
        assertNull(tile.removeMarble(10, 10));
    }
    
    @Test
    public void toStringReturnsRightKindOfString() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        assertEquals(tile.toString(), 
                "[[O], [X], null]\n"
              + "[[X], [O], null]\n"
              + "[null, null, [O]]");
    }
    
    @Test
    public void rowToStringReturnsRightKindOfString() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        assertEquals(tile.rowToString(0), "[[O], [X], null]");
        assertEquals(tile.rowToString(1), "[[X], [O], null]");
        assertEquals(tile.rowToString(2), "[null, null, [O]]");
    }
    
    @Test
    public void rotateThrowsExceptionIfTheDirectionIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid direction");
        tile.rotate(Direction.HORIZONTAL);
        tile.rotate(Direction.VERTICAL);
        tile.rotate(Direction.UPGRADING_DIAGONAL);
        tile.rotate(Direction.DOWNGRADING_DIAGONAL);
    }
    
    @Test
    public void rotateWorksCorrectlyClockwise() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        
        tile.rotate(Direction.CLOCKWISE);
        
        
        assertEquals(tile.toString(), 
                "[null, [X], [O]]\n"
              + "[null, [O], [X]]\n"
              + "[[O], null, null]");
    }
    
    @Test
    public void rotateWorksCorrectlyCounterClockWise() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        
        tile.rotate(Direction.COUNTER_CLOCKWISE);
       
        assertEquals(tile.toString(), 
                "[null, null, [O]]\n"
              + "[[X], [O], null]\n"
              + "[[O], [X], null]");
    }
    
    @Test
    public void doubleRotateInDifferentDirectionsBringsBackToTheBeginning() {
        String firstToString = tile.toString();
        tile.rotate(Direction.CLOCKWISE);
        tile.rotate(Direction.COUNTER_CLOCKWISE);
        assertEquals(tile.toString(), firstToString);
    }
    
    @Test
    public void doubleRotateIsTheSameInBothDirections() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        tile.rotate(Direction.CLOCKWISE);
        tile.rotate(Direction.CLOCKWISE);
        
        Tile tile2 = new Tile();
        tile2.setMarble(o, 0, 0);
        tile2.setMarble(x, 1, 0);
        tile2.setMarble(x, 0, 1);
        tile2.setMarble(o, 1, 1);
        tile2.setMarble(o, 2, 2);
        tile2.rotate(Direction.COUNTER_CLOCKWISE);
        tile2.rotate(Direction.COUNTER_CLOCKWISE);
        
        String rotatedString = 
                "[[O], null, null]\n"
              + "[null, [O], [X]]\n"
              + "[null, [X], [O]]";
        assertEquals(tile.toString(), rotatedString);
        assertEquals(tile2.toString(), rotatedString);
    }
    
    @Test
    public void getLastDirectionFirstReturnsNull() {
        assertNull(tile.getLastDirection());
    }
    
    @Test
    public void getLastDirectionReturnsTheLastDirection() {
        tile.rotate(Direction.CLOCKWISE);
        assertEquals(Direction.CLOCKWISE, tile.getLastDirection());
        tile.rotate(Direction.COUNTER_CLOCKWISE);
        assertEquals(Direction.COUNTER_CLOCKWISE, tile.getLastDirection());
    }
    
    @Test
    public void clearClearsTheTile() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 2, 0);
        tile.setMarble(x, 0, 2);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        
        tile.clear();
        for(Marble[] row : tile.getTile()) {
            for(Marble m : row) {
                assertNull(m);
            }
        }
    }
    
    @Test
    public void isEmptyReturnsRight() {
        assertTrue(tile.isEmpty());
        tile.setMarble(new Marble(Symbol.O), 0, 0);
        assertFalse(tile.isEmpty());
    }
    
    @Test
    public void isFullReturnsRight() {
        for(int y = 0; y < 3; y++) {
            for(int x = 0; x < 3; x++) {
                if(x != 2 || y != 2) {
                    tile.setMarble(new Marble(Symbol.O), x, y);
                    assertFalse(tile.isFull());
                }
            }
        }
        tile.setMarble(new Marble(Symbol.O), 2, 2);
        assertTrue(tile.isFull());
    }
    
}
