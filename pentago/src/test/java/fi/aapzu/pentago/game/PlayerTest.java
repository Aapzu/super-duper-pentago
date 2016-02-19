/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Symbol;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Aapeli
 */
public class PlayerTest {
    
    public PlayerTest() {}

    @Test
    public void defaultNameForPlayerIsnew_player() {
        Player p = new Player(Symbol.O);
        assertEquals("new_player", p.getName());
    }
    
    @Test
    public void setNameSetsName() {
        Player p = new Player(Symbol.O);
        p.setName("test");
        assertEquals("test", p.getName());
    }
    
    @Test
    public void setSymbolSetsSymbol() {
        Player p = new Player(Symbol.O);
        p.setSymbol(Symbol.X);
        assertEquals(Symbol.X, p.getSymbol());
    }
    
    @Test
    public void toStringReturnsName() {
        Player p = new Player(Symbol.O);
        p.setName("test");
        assertEquals("test", p.toString());
    }
}
