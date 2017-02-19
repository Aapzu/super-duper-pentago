package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class BoardTest {

    private Board board;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public BoardTest() {
    }

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
    public void copyConstructorWorks() {
        board.addMarble(new Marble(Symbol.X), 0, 0);
        board.addMarble(new Marble(Symbol.O), 2, 2);
        board.addMarble(new Marble(Symbol.X), 4, 1);
        board.addMarble(new Marble(Symbol.O), 0, 4);
        board.addMarble(new Marble(Symbol.X), 5, 5);
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        board.rotateTile(0, 1, Direction.CLOCKWISE);
        board.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);

        Board board2 = new Board(board);

        assert (board.equals(board2));
    }

    @Test
    public void copyConstructorWorksPt2() {
        board = new Board(10, 20);
        Board board2 = new Board(board);

        assertEquals(board.getSideLength(), board2.getSideLength());
        assertEquals(board.getTileSideLength(), board2.getTileSideLength());
    }

    @Test
    public void getTileThrowsErrorIfCoordinatesAreInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Invalid coordinates");
        board.getTile(-1, 0);
        board.getTile(0, -1);
        board.getTile(0, 2);
        board.getTile(2, 0);
        board.getTile(2, -1);
        board.getTile(-1, 2);
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
                .getTile()[1][1], m);
        m = new Marble(Symbol.O);
        board.addMarble(m, 4, 4);
        assertEquals(board.getTileByCoordinates(4, 4)
                .getTile()[1][1], m);
    }

    @Test
    public void addMarbleReturnsTrueIfTheCoordinatesAreRight() {
        assert (board.addMarble(new Marble(Symbol.O), 0, 0));
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

        assertEquals("[X][ ][ ][ ][ ][ ]\n" +
                "[ ][O][ ][ ][X][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n" +
                "[X][ ][ ][ ][ ][ ]\n" +
                "[ ][O][ ][ ][O][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n", board.toString());
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
        assertEquals("[ ][ ][X][ ][ ][ ]\n" +
                "[ ][O][ ][ ][X][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n" +
                "[X][ ][ ][ ][ ][ ]\n" +
                "[ ][O][ ][ ][O][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n", board.toString());

        board.rotateTile(0, 1, Direction.COUNTER_CLOCKWISE);
        assertEquals("[ ][ ][X][ ][ ][ ]\n" +
                "[ ][O][ ][ ][X][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ][ ]\n" +
                "[ ][O][ ][ ][O][ ]\n" +
                "[X][ ][ ][ ][ ][ ]\n", board.toString());
    }

    @Test
    public void rotateTileThrowsExceptionIfTheDirectionIsIllegal() {
        exception.expect(IllegalArgumentException.class);
        board.rotateTile(0, 0, Direction.VERTICAL);
    }

    @Test
    public void validateTileCoordinatesWorksRight() {
        assert (board.validateTileCoordinates(0, 0));
        assert (board.validateTileCoordinates(0, 1));
        assert (board.validateTileCoordinates(1, 0));
        assert (board.validateTileCoordinates(1, 1));

        assertFalse(board.validateTileCoordinates(-1, 0));
        assertFalse(board.validateTileCoordinates(2, 0));
        assertFalse(board.validateTileCoordinates(0, -1));
        assertFalse(board.validateTileCoordinates(0, 2));
        assertFalse(board.validateTileCoordinates(-1, -1));
        assertFalse(board.validateTileCoordinates(2, 2));
        assertFalse(board.validateTileCoordinates(-1, 2));
        assertFalse(board.validateTileCoordinates(2, -1));
    }

    @Test
    public void getLastRotatedTileWorks() {
        Tile t1 = board.getTile(0, 0);
        Tile t2 = board.getTile(0, 1);
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        assert (t1 == board.getLastRotatedTile());
        assertFalse(t2 == board.getLastRotatedTile());
        board.rotateTile(0, 1, Direction.CLOCKWISE);
        assert (t2 == board.getLastRotatedTile());
        assertFalse(t1 == board.getLastRotatedTile());
    }

    @Test
    public void toMarbleArrayReturnsRight() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        board.addMarble(x, 0, 0);
        board.addMarble(o, 2, 0);
        board.addMarble(x, 4, 1);
        board.addMarble(o, 0, 4);
        board.addMarble(x, 5, 5);
        Marble[][] arr = board.toMarbleArray();

        assertEquals(6, arr.length);
        assertEquals(6, arr[0].length);

        assertEquals(x, arr[0][0]);
        assertEquals(o, arr[0][2]);
        assertEquals(x, arr[1][4]);
        assertEquals(o, arr[4][0]);
        assertEquals(x, arr[5][5]);

        assertNull(arr[1][0]);
        assertNull(arr[4][2]);
        assertNull(arr[1][3]);
    }

    @Test
    public void clearSetsLastRotatedTileToNull() {
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        board.clear();
        assertNull(board.getLastRotatedTile());
    }

    @Test
    public void clearClearsTheBoard() {
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        board.addMarble(x, 0, 0);
        board.addMarble(o, 2, 0);
        board.addMarble(x, 4, 1);
        board.addMarble(o, 0, 4);
        board.addMarble(x, 5, 5);
        board.clear();

        Marble[][] arr = board.toMarbleArray();
        for (Marble[] row : arr) {
            for (Marble m : row) {
                assertNull(m);
            }
        }
    }

    @Test
    public void isEmptyReturnsRight() {
        assert (board.isEmpty());
        board.addMarble(new Marble(Symbol.O), 3, 3);
        assertFalse(board.isEmpty());
    }

    @Test
    public void isFullReturnsRight() {
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                if (x != 5 || y != 5) {
                    board.addMarble(new Marble(Symbol.O), x, y);
                    assertFalse(board.isFull());
                }
            }
        }
        board.addMarble(new Marble(Symbol.O), 5, 5);
        assertTrue(board.isFull());
    }

    @Test
    public void getMarbleArrayWorks() {
        Marble O = new Marble(Symbol.O);
        Marble X = new Marble(Symbol.X);
        board.addMarble(O, 0, 0);
        board.addMarble(O, 1, 1);
        board.addMarble(O, 2, 1);
        board.addMarble(X, 4, 0);
        board.addMarble(X, 4, 1);
        board.addMarble(X, 4, 2);
        board.addMarble(O, 1, 4);
        board.addMarble(O, 2, 4);
        board.addMarble(O, 4, 4);
        assert(ArrayUtils.deepEquals(new Marble[][]{
                {O,     null,   null,   null, X,    null},
                {null,  O,      O,      null, X,    null},
                {null,  null,   null,   null, X,    null},
                {null,  null,   null,   null, null, null},
                {null,  O,      O,      null, O,    null},
                {null,  null,   null,   null, null, null}
        }, board.getMarbleArray()));
    }

    @Test
    public void equalsWorks() {
        board.addMarble(new Marble(Symbol.X), 0, 0);
        board.addMarble(new Marble(Symbol.O), 2, 2);
        board.addMarble(new Marble(Symbol.X), 4, 1);
        board.addMarble(new Marble(Symbol.O), 0, 4);
        board.addMarble(new Marble(Symbol.X), 5, 5);
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        board.rotateTile(0, 1, Direction.CLOCKWISE);
        board.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);

        Board board2 = new Board();
        board2.addMarble(new Marble(Symbol.X), 0, 0);
        board2.addMarble(new Marble(Symbol.O), 2, 2);
        board2.addMarble(new Marble(Symbol.X), 4, 1);
        board2.addMarble(new Marble(Symbol.O), 0, 4);
        board2.addMarble(new Marble(Symbol.X), 5, 5);
        board2.rotateTile(0, 0, Direction.CLOCKWISE);
        board2.rotateTile(0, 1, Direction.CLOCKWISE);
        board2.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board2.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);

        Board board3 = new Board();
        board3.addMarble(new Marble(Symbol.X), 0, 0);
        board3.addMarble(new Marble(Symbol.O), 2, 2);
        board3.addMarble(new Marble(Symbol.X), 4, 1);
        board3.addMarble(new Marble(Symbol.O), 0, 4);
        board3.addMarble(new Marble(Symbol.O), 5, 5);
        board3.rotateTile(0, 0, Direction.CLOCKWISE);
        board3.rotateTile(0, 1, Direction.CLOCKWISE);
        board3.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board3.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);

        Board board4 = new Board();
        board4.addMarble(new Marble(Symbol.X), 0, 0);
        board4.addMarble(new Marble(Symbol.O), 2, 2);
        board4.addMarble(new Marble(Symbol.X), 4, 1);
        board4.addMarble(new Marble(Symbol.O), 0, 4);
        board4.addMarble(new Marble(Symbol.X), 5, 5);
        board4.rotateTile(0, 0, Direction.CLOCKWISE);
        board4.rotateTile(0, 1, Direction.COUNTER_CLOCKWISE);
        board4.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board4.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);

        assert (board.equals(board2));
        assert (board2.equals(board));
        assertFalse(board.equals(board3));
        assertFalse(board2.equals(board3));
        assertFalse(board4.equals(board));
        assertFalse(board3.equals(board4));

        assertFalse(board.equals("aapeli"));
        assertFalse(board.equals(null));
    }

    @Test
    public void serializationWorks() {
        board.setTiles(new Tile[][]{
                {mock(Tile.class), mock(Tile.class)},
                {mock(Tile.class), mock(Tile.class)}
        });
        when(board.getTile(0, 0).serialize()).thenReturn("");
        when(board.getTile(0, 1).serialize()).thenReturn("");
        when(board.getTile(1, 0).serialize()).thenReturn("");
        when(board.getTile(1, 1).serialize()).thenReturn("");

        board.addMarble(new Marble(Symbol.X), 0, 0);
        board.addMarble(new Marble(Symbol.O), 2, 2);
        board.addMarble(new Marble(Symbol.X), 4, 1);
        board.addMarble(new Marble(Symbol.O), 0, 4);
        board.addMarble(new Marble(Symbol.X), 5, 5);
        board.rotateTile(0, 0, Direction.CLOCKWISE);
        board.rotateTile(0, 1, Direction.CLOCKWISE);
        board.rotateTile(1, 0, Direction.COUNTER_CLOCKWISE);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        assertEquals(5, board.serialize().length());
        assertEquals(board.serialize(), "55112");
        board.rotateTile(1, 0, Direction.CLOCKWISE);
        assertEquals(board.serialize(), "55101");
    }

    @Test
    public void deserializationWorks() {
        board.addMarble(new Marble(Symbol.X), 0, 0);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        board.addMarble(new Marble(Symbol.O), 2, 2);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        board.addMarble(new Marble(Symbol.X), 4, 1);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        board.addMarble(new Marble(Symbol.O), 0, 4);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        board.addMarble(new Marble(Symbol.X), 3, 5);
        board.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        String s = board.getTile(0, 0).serialize() +
                board.getTile(1, 0).serialize() +
                board.getTile(0, 1).serialize() +
                board.getTile(1, 1).serialize() +
                "35112";
        Board b2 = new Board();
        assert (b2.deserialize(s));
        assertEquals(board, b2);
    }
}
