
package fi.pentago.logic;



import fi.pentago.logic.marble.Marble;
import fi.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        exception.expect(RuntimeException.class);
        exception.expectMessage("must be truly positive");
        tile = new Tile(-2);
    }
    
    @Test
    public void constructorMustThrowExceptionIfSideLengthIsZero() {
        exception.expect(RuntimeException.class);
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
        assertFalse(tile.setMarble(m, 3, 0));
        assertFalse(tile.setMarble(m, 3, 15));
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
        assertEquals(tile.removeMarble(0, 0), m);
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
    public void rotateClockWiseWorksCorrectly() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        
        tile.rotateClockWise();
        
        
        assertEquals(tile.toString(), 
                "[null, [X], [O]]\n"
              + "[null, [O], [X]]\n"
              + "[[O], null, null]");
    }
    
    @Test
    public void rotateCounterClockWiseWorksCorrectly() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.setMarble(o, 0, 0);
        tile.setMarble(x, 1, 0);
        tile.setMarble(x, 0, 1);
        tile.setMarble(o, 1, 1);
        tile.setMarble(o, 2, 2);
        
        tile.rotateCounterClockWise();
       
        assertEquals(tile.toString(), 
                "[null, null, [O]]\n"
              + "[[X], [O], null]\n"
              + "[[O], [X], null]");
    }
    
    @Test
    public void doubleRotateInDifferentDirectionsBringsBackToTheBeginning() {
        String firstToString = tile.toString();
        tile.rotateClockWise();
        tile.rotateCounterClockWise();
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
        tile.rotateClockWise();
        tile.rotateClockWise();
        
        Tile tile2 = new Tile();
        tile2.setMarble(o, 0, 0);
        tile2.setMarble(x, 1, 0);
        tile2.setMarble(x, 0, 1);
        tile2.setMarble(o, 1, 1);
        tile2.setMarble(o, 2, 2);
        tile2.rotateCounterClockWise();
        tile2.rotateCounterClockWise();
        
        String rotatedString = 
                "[[O], null, null]\n"
              + "[null, [O], [X]]\n"
              + "[null, [X], [O]]";
        assertEquals(tile.toString(), rotatedString);
        assertEquals(tile2.toString(), rotatedString);
    }
    
}
