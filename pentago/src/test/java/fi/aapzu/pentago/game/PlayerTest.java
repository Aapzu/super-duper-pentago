/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Aapeli
 */
public class PlayerTest {

    public PlayerTest() {
    }

    @Test
    public void constructorsWorksRight() {
        Player p = new Player(0);
        assertEquals((Integer) 0, p.getPlayerNumber());

        p = new Player("test");
        assertEquals("test", p.getName());
        assertNull(p.getPlayerNumber());
    }

    @Test
    public void setNameSetsName() {
        Player p = new Player(0);
        p.setName("test");
        assertEquals("test", p.getName());
    }

    @Test
    public void setSymbolSetsSymbol() {
        Player p = new Player(0);
        p.setSymbol(Symbol.X);
        assertEquals(Symbol.X, p.getSymbol());
    }

    @Test
    public void toStringReturnsName() {
        Player p = new Player(0);
        p.setName("test");
        assertEquals("test", p.toString());
    }
}
