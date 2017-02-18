
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class LineTest {

    private Line line;

    public LineTest() {
    }

    @Before
    public void setUp() {
        line = new Line();
    }

    @After
    public void tearDown() {
        line = null;
    }

    @Test
    public void addCoordinateAddsCoordinate() {
        line.addCoordinate(new Integer[]{0, 0});
        assertEquals(1, line.getCoordinates().size());
        assertTrue(ArrayUtils.deepEquals(new Integer[]{0, 0}, line.getCoordinates().get(0)));
    }

    @Test
    public void lengthReturnsRight() {
        line.addCoordinate(new Integer[]{0, 0});
        line.addCoordinate(new Integer[]{1, 0});
        line.addCoordinate(new Integer[]{2, 0});
        assertEquals(3, line.length());
    }

    @Test
    public void clearClearsAll() {
        line.setPlayer(new Player());
        line.setSymbol(Symbol.O);
        line.addCoordinate(new Integer[]{0, 0});
        line.clear();
        assertTrue(line.getCoordinates().isEmpty());
        assertNull(line.getPlayer());
        assertNull(line.getSymbol());
    }

    @Test
    public void toStringReturnsRight() {
        Player p = new Player("test");
        line.setPlayer(p);

        line.setSymbol(Symbol.O);
        line.addCoordinate(new Integer[]{0, 0});
        String expected = "" +
                "Player: test\n" +
                "Symbol: O\n" +
                "Line:\n" +
                "\t[0, 0]";
        System.out.println(line.toString());
        assertEquals(expected, line.toString());
    }
}
