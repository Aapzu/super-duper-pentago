package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;


public class TileTest {

    private Tile tile;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public TileTest() {
    }

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
    public void copyConstructorWorks() {
        tile.addMarble(new Marble(Symbol.X), 0, 2);
        tile.addMarble(new Marble(Symbol.O), 1, 2);
        tile.rotate(Direction.CLOCKWISE);
        Tile t2 = new Tile(tile);
        assert (tile.equals(t2));
        assert (t2.equals(tile));
    }

    @Test
    public void copyConstructorReallyCopiesTheTile() {
        tile.addMarble(new Marble(Symbol.X), 0, 2);
        tile.addMarble(new Marble(Symbol.O), 1, 2);
        tile.rotate(Direction.CLOCKWISE);
        Tile t2 = new Tile(tile);
        t2.addMarble(new Marble(Symbol.X), 0, 2);
        assertFalse(tile.equals(t2));
    }

    @Test
    public void constructorWithoutParameterUsesTheRightDefaultValue() {
        assertEquals(tile.getSideLength(), 3);
    }

    @Test
    public void setMarbleSetsRightMarbleIntoRightPlace() {
        Marble m = new Marble(Symbol.X);
        Marble m2 = new Marble(Symbol.O);
        assertTrue(tile.addMarble(m, 0, 0));
        assertTrue(tile.addMarble(m2, 1, 2));
        assertEquals(tile.getTile()[0][0].toString(), "[X]");
        assertEquals(tile.getTile()[2][1].toString(), "[O]");
    }

    @Test
    public void setMarbleDoesNotSetMarbleOutsideTheTile() {
        Marble m = new Marble(Symbol.X);
        assertFalse(tile.addMarble(m, -1, 2));
        assertFalse(tile.addMarble(m, 2, -1));
        assertFalse(tile.addMarble(m, 3, 0));
        assertFalse(tile.addMarble(m, 0, 3));
        assertFalse(tile.addMarble(m, 3, 15));
        assertFalse(tile.addMarble(m, 15, 2));
    }

    @Test
    public void setMarbleThrowsExceptionIfPlaceIsNotEmpty() {
        tile.addMarble(new Marble(Symbol.O), 1, 1);
        exception.expect(PentagoGameRuleException.class);
        tile.addMarble(new Marble(Symbol.O), 1, 1);
    }

    @Test
    public void getReturnsMarbleCorrectly() {
        Marble m = new Marble(Symbol.X);
        tile.addMarble(m, 0, 0);
        assertEquals(tile.getMarble(0, 0), m);
    }

    @Test
    public void getReturnsNullIfThePlaceIsEmptyOrDoesNotExist() {
        assertNull(tile.getMarble(0, 0));
        assertNull(tile.getMarble(-1, 0));
        assertNull(tile.getMarble(0, -1));
        assertNull(tile.getMarble(-1, -1));
        assertNull(tile.getMarble(-1, 10));
        assertNull(tile.getMarble(10, -1));
        assertNull(tile.getMarble(10, 10));
    }

    @Test
    public void removeMarbleRemovesMarble() {
        tile.addMarble(new Marble(Symbol.X), 0, 0);
        tile.removeMarble(0, 0);
        assertNull(tile.getMarble(0, 0));
    }

    @Test
    public void removeMarbleReturnsTheMarbleItRemoved() {
        Marble m = new Marble(Symbol.X);
        tile.addMarble(m, 0, 0);
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
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 1, 0);
        tile.addMarble(x, 0, 1);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);
        assertEquals(tile.toString(),
                "[O][X][ ]\n"
                        + "[X][O][ ]\n"
                        + "[ ][ ][O]");
    }

    @Test
    public void rowToStringReturnsRightKindOfString() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 1, 0);
        tile.addMarble(x, 0, 1);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);
        assertEquals(tile.rowToString(0), "[O][X][ ]");
        assertEquals(tile.rowToString(1), "[X][O][ ]");
        assertEquals(tile.rowToString(2), "[ ][ ][O]");
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
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 1, 0);
        tile.addMarble(x, 0, 1);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);

        tile.rotate(Direction.CLOCKWISE);


        assertEquals(tile.toString(),
                "[ ][X][O]\n"
                        + "[ ][O][X]\n"
                        + "[O][ ][ ]");
    }

    @Test
    public void rotateWorksCorrectlyCounterClockWise() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 1, 0);
        tile.addMarble(x, 0, 1);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);

        tile.rotate(Direction.COUNTER_CLOCKWISE);

        assertEquals(tile.toString(),
                "[ ][ ][O]\n"
                        + "[X][O][ ]\n"
                        + "[O][X][ ]");
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
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 1, 0);
        tile.addMarble(x, 0, 1);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);
        tile.rotate(Direction.CLOCKWISE);
        tile.rotate(Direction.CLOCKWISE);

        Tile tile2 = new Tile();
        tile2.addMarble(o, 0, 0);
        tile2.addMarble(x, 1, 0);
        tile2.addMarble(x, 0, 1);
        tile2.addMarble(o, 1, 1);
        tile2.addMarble(o, 2, 2);
        tile2.rotate(Direction.COUNTER_CLOCKWISE);
        tile2.rotate(Direction.COUNTER_CLOCKWISE);

        String rotatedString =
                "[O][ ][ ]\n"
                        + "[ ][O][X]\n"
                        + "[ ][X][O]";
        assertEquals(tile.toString(), rotatedString);
        assertEquals(tile2.toString(), rotatedString);
    }

    @Test
    public void clearClearsTheTile() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        tile.addMarble(o, 0, 0);
        tile.addMarble(x, 2, 0);
        tile.addMarble(x, 0, 2);
        tile.addMarble(o, 1, 1);
        tile.addMarble(o, 2, 2);

        tile.clear();
        for (Marble[] row : tile.getTile()) {
            for (Marble m : row) {
                assertNull(m);
            }
        }
    }

    @Test
    public void isEmptyReturnsRight() {
        assertTrue(tile.isEmpty());
        tile.addMarble(new Marble(Symbol.O), 0, 0);
        assertFalse(tile.isEmpty());
    }

    @Test
    public void isFullReturnsRight() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (x != 2 || y != 2) {
                    tile.addMarble(new Marble(Symbol.O), x, y);
                    assertFalse(tile.isFull());
                }
            }
        }
        tile.addMarble(new Marble(Symbol.O), 2, 2);
        assertTrue(tile.isFull());
    }

    @Test
    public void equalsWorks() {
        Tile t1 = new Tile();
        t1.addMarble(new Marble(Symbol.X), 2, 2);
        t1.rotate(Direction.CLOCKWISE);
        Tile t2 = new Tile();
        t2.addMarble(new Marble(Symbol.X), 2, 2);
        t2.rotate(Direction.CLOCKWISE);
        Tile t3 = new Tile();
        t3.addMarble(new Marble(Symbol.X), 2, 1);
        t3.rotate(Direction.COUNTER_CLOCKWISE);
        t3.rotate(Direction.CLOCKWISE);
        Tile t4 = new Tile();
        t4.addMarble(new Marble(Symbol.O), 2, 2);
        t4.rotate(Direction.CLOCKWISE);
        assert (t1.equals(t2));
        assert (t2.equals(t1));
        assertFalse(t1.equals(t3));
        assertFalse(t3.equals(t1));
        assertFalse(t1.equals(t4));
        assertFalse(t4.equals(t1));
        assertFalse(t3.equals(t4));
        assertFalse(t4.equals(t3));
    }

    @Test
    public void serializationWorks() {
        Tile t1 = new Tile();
        t1.addMarble(new Marble(Symbol.X), 2, 2);
        t1.addMarble(new Marble(Symbol.O), 0, 2);
        t1.addMarble(new Marble(Symbol.X), 2, 1);
        assertEquals("000001201", t1.serialize());
    }

    @Test
    public void deserializationWorks() {
        Tile t1 = new Tile();
        t1.addMarble(new Marble(Symbol.X), 2, 2);
        t1.addMarble(new Marble(Symbol.O), 0, 2);
        t1.addMarble(new Marble(Symbol.X), 2, 1);
        Tile t2 = new Tile();
        assertTrue(t2.deserialize("000001201"));
        assertEquals(t1, t2);
        assertFalse(t2.deserialize("0000012012")); // Too long
    }

}
