/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic.line;

import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aapeli
 */
public class LineTest {
    
    Line line;
    
    public LineTest() {}
    
    @Before
    public void setUp() {
        line = new Line();
    }
    
    @After
    public void tearDown() {
        line = null;
    }

    @Test
    public void addLinePointAddsLinePoint() {
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{0,0}));
        assertEquals(1, line.getLinePoints().size());
    }
    
    @Test
    public void lengthReturnsRight() {
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{0,0}));
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{1,0}));
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{2,0}));
        assertEquals(3, line.length());
    }
    
    @Test
    public void clearClearsAll() {
        line.setPlayer(new Player(Symbol.O));
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{0,0}));
        line.clear();
        assertTrue(line.getLinePoints().isEmpty());
        assertNull(line.getPlayer());
        assertNull(line.getSymbol());
    }
    
    @Test
    public void toStringReturnsRight() {
        Player p = new Player(Symbol.O);
        p.setName("test");
        line.setPlayer(p);
        
        line.addLinePoint(new LinePoint(new Marble(Symbol.O), new Integer[]{0,0}));
        String expected = "" +
                "Player: test\n" +
                "Symbol: O\n" +
                "Line:\n" +
                "\t[0, 0]";
        System.out.println(line.toString());
        assertEquals(expected, line.toString());
    }
}
