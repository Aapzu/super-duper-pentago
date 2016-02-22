/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Aapeli
 */
public class PentagoTest {
    
    Pentago game;
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    public PentagoTest() {}
    
    @Before
    public void setUp() {
        game = new Pentago();
    }
    
    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void constructorSetsTheDataRight() {
        assertTrue(game.getBoard().isEmpty());
        assertEquals(game.getBoard(), game.getLineChecker().getBoard());
        assertEquals(2, game.getPlayers().length);
        assertEquals(Symbol.O, game.getPlayers()[0].getSymbol());
        assertEquals(Symbol.X, game.getPlayers()[1].getSymbol());
        assertEquals(game.getPlayers()[0], game.whoseTurn());
        assertFalse(game.getAllowedToRotate());
    }
    
    @Test
    public void clearClearsThePentago() {
        game.clear();
        assertTrue(game.getBoard().isEmpty());
        assertEquals(2, game.getPlayers().length);
        assertEquals(Symbol.O, game.getPlayers()[0].getSymbol());
        assertEquals(Symbol.X, game.getPlayers()[1].getSymbol());
        assertEquals(game.getPlayers()[0], game.whoseTurn());
        assertFalse(game.getAllowedToRotate());
    }
        
    @Test
    public void setPlayerNameSetsRightName() {
        game.setPlayerName(0, "test0");
        game.setPlayerName(1, "test1");
        assertEquals("test0", game.getPlayers()[0].getName());
        assertEquals("test1", game.getPlayers()[1].getName());
    }
        
    @Test
    public void setPlayerThrowsExceptionIfTheIndexIsIllegl() {
        exception.expect(IllegalArgumentException.class);
        game.setPlayerName(-1, "test");
        game.setPlayerName(2, "test");
    }
    
    @Test
    public void setMarbleSetsCorrectMarbleToCorrectPlace() {
        game.setMarble(0,0);
        assertEquals(Symbol.O, game.getBoard().toMarbleArray()[0][0].getSymbol());
    }
    
    @Test
    public void setMarbleSetsAllowedToRotateToTrue() {
        game.setMarble(0,0);
        assertTrue(game.getAllowedToRotate());
    }
    
    @Test
    public void setMarbleThrowsExceptionIfIllegalCoordinates() {
        exception.expect(IllegalArgumentException.class);
        game.setMarble(-1, -1);
    }
    
    @Test
    public void setMarbleThrowsExceptionIfAllowedToRotate() {
        game.setMarble(0, 1);
        exception.expect(PentagoGameRuleException.class);
        game.setMarble(0, 0);
    }
    
    @Ignore
    @Test
    public void rotateTileRotatestheRightTile() {
        assert(false);
    }
    
    @Test
    public void rotateTileSetsAllowedToRotateToFalse() {
        game.setMarble(0, 0);
        game.rotateTile(0,0, Direction.CLOCKWISE);
        assertFalse(game.getAllowedToRotate());
    }
    
    @Test
    public void rotateTileThrowsExceptionIfNotAllowedToRotate() {
        exception.expect(PentagoGameRuleException.class);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
    }
    
    @Test
    public void rotateTileThrowsExceptionIfIllegalDirection() {
        game.setMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        exception.expect(PentagoGameRuleException.class);
        game.setMarble(0, 1);
        game.rotateTile(0, 0, Direction.COUNTER_CLOCKWISE);
    }
    
    @Test
    public void nextTurnChangestheTurn() {
        Player one = game.getPlayers()[0];
        Player two = game.getPlayers()[1];
        assertEquals(game.whoseTurn(), one);
        game.nextTurn();
        assertEquals(game.whoseTurn(), two);
        game.nextTurn();
        assertEquals(game.whoseTurn(), one);
    }
    
    @Test
    public void getPlayerBySymbolReturnsRight() {
        Player one = game.getPlayers()[0];
        Player two = game.getPlayers()[1];
        assertEquals(two, game.getPlayerBySymbol(Symbol.X));
        assertEquals(one, game.getPlayerBySymbol(Symbol.O));
    }
    
    @Test
    public void getPlayerBySymbolReturnsNullWithNullSymbol() {
        assertNull(game.getPlayerBySymbol(null));
    }
    
    private void addMarble(int x, int y) {
        game.setMarble(x, y);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        assertNull(game.checkLines());
    }
    
    @Test
    public void checkLinesWorks() {
        addMarble(1, 5); // White
        addMarble(2, 0); // Black
        addMarble(1, 4); // White
        addMarble(2, 1); // Black
        addMarble(1, 3); // White
        addMarble(2, 2); // Black
        addMarble(1, 2); // White
        addMarble(2, 3); // Black
        
        
        game.setMarble(1, 1);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        Line line = game.checkLines();
        assertNotNull(line);
        assertEquals(game.getPlayers()[0], line.getPlayer());
    }
}
