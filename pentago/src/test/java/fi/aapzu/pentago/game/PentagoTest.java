/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;

/**
 * @author Aapeli
 */
public class PentagoTest {

    private Pentago game;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    public PentagoTest() {}

    private void addMarble(int x, int y) {
        game.addMarble(x, y);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        assertNull(game.checkLines());
    }

    @Before
    public void setUp() {
        game = new Pentago();
        game.addHumanPlayer("test");
        game.addHumanPlayer("test2");
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void constructorSetsTheDataRight() {
        assertTrue(game.getBoard().isEmpty());
        assertEquals(2, game.getPlayers().length);
        assertEquals(Symbol.X, game.getPlayers()[0].getSymbol());
        assertEquals(Symbol.O, game.getPlayers()[1].getSymbol());
        assertEquals(game.getPlayers()[0], game.whoseTurn());
        assertFalse(game.getAllowedToRotate());
    }

    @Test
    public void copyConstructorWorks() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        Pentago game2 = new Pentago(game);

        assert(game.equals(game2));
    }

    @Test
    public void clearClearsThePentago() {
        Board b = mock(Board.class);
        game.setBoard(b);
        game.clear();
        verify(b).clear();
        assertEquals(2, game.getPlayers().length);
        assertEquals(Symbol.X, game.getPlayers()[0].getSymbol());
        assertEquals(Symbol.O, game.getPlayers()[1].getSymbol());
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
        game.addMarble(0, 0);
        assertEquals(Symbol.X, game.getBoard().toMarbleArray()[0][0].getSymbol());
    }

    @Test
    public void setMarbleSetsAllowedToRotateToTrue() {
        game.addMarble(0, 0);
        assertTrue(game.getAllowedToRotate());
    }

    @Test
    public void setMarbleThrowsExceptionIfIllegalCoordinates() {
        exception.expect(IllegalArgumentException.class);
        game.addMarble(-1, -1);
    }

    @Test
    public void setMarbleThrowsExceptionIfAllowedToRotate() {
        game.addMarble(0, 1);
        exception.expect(PentagoGameRuleException.class);
        game.addMarble(0, 0);
    }

    @Test
    public void rotateTileRotatestheRightTile() {
        Board board = mock(Board.class);
        when(board.addMarble(isA(Marble.class), anyInt(), anyInt())).thenReturn(true);
        game.setBoard(board);

        // A marble must be added always before rotating
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        verify(board).rotateTile(0, 0, Direction.CLOCKWISE);

        game.addMarble(0, 1);
        game.rotateTile(0, 1, Direction.COUNTER_CLOCKWISE);
        verify(board).rotateTile(0, 1, Direction.COUNTER_CLOCKWISE);

        game.addMarble(0, 2);
        game.rotateTile(1, 0, Direction.CLOCKWISE);
        verify(board).rotateTile(1, 0, Direction.CLOCKWISE);

        game.addMarble(0, 3);
        game.rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
        verify(board).rotateTile(1, 1, Direction.COUNTER_CLOCKWISE);
    }

    @Test
    public void rotateTileSetsAllowedToRotateToFalse() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        assertFalse(game.getAllowedToRotate());
    }

    @Test
    public void rotateTileThrowsExceptionIfNotAllowedToRotate() {
        exception.expect(PentagoGameRuleException.class);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
    }

    @Test
    public void rotateTileThrowsExceptionIfIllegalDirection() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        exception.expect(PentagoGameRuleException.class);
        game.addMarble(0, 1);
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
        assertEquals(one, game.getPlayerBySymbol(Symbol.X));
        assertEquals(two, game.getPlayerBySymbol(Symbol.O));
    }

    @Test
    public void getPlayerBySymbolReturnsNullWithNullSymbol() {
        assertNull(game.getPlayerBySymbol(null));
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


        game.addMarble(1, 1);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        Line line = game.checkLines();
        assertNotNull(line);
        assertEquals(game.getPlayers()[0], line.getPlayer());
    }

    @Test
    public void isEvenClassIsFullOfBoard() {
        Board board = mock(Board.class);
        when(board.isFull()).thenReturn(false);
        game.setBoard(board);
        boolean isEven = game.isEven();
        assertFalse(isEven);
        verify(board).isFull();
    }

    @Test
    public void equalsWorks() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        game.addMarble(0, 1);

        Pentago game2 = new Pentago();
        game2.addHumanPlayer("test");
        game2.addHumanPlayer("test2");
        game2.addMarble(0, 0);
        game2.rotateTile(0, 0, Direction.CLOCKWISE);
        game2.addMarble(0, 1);

        Pentago game4 = new Pentago();
        game4.addHumanPlayer("test");
        game4.addHumanPlayer("test2");
        game4.addMarble(0, 0);
        game4.rotateTile(0, 0, Direction.CLOCKWISE);

        assert (game.equals(game2));
        assert (game2.equals(game));
        assertFalse(game.equals(game4));
    }

    @Test
    public void getLastMoveWorks() {
        assertNull(game.getLastMove());
        game.addMarble(0, 0);
        game.rotateTile(1, 1, Direction.CLOCKWISE);

        Move newMove = new Move(0, new Marble(game.getPlayers()[0].getSymbol()), 0, 0, 1, 1, Direction.CLOCKWISE);
        Move oldMove = game.getLastMove();
        assert (oldMove.equals(newMove));
    }

    @Test
    public void serializeWorks() {
        game.setBoard(mock(Board.class));
        when(game.getBoard().serialize()).thenReturn("");
        when(game.getBoard().addMarble(any(Marble.class), anyInt(), anyInt())).thenReturn(true);

        assertEquals(2, game.serialize().length());
        assertEquals("00", game.serialize());
        game.nextTurn();
        assertEquals("10", game.serialize());
        game.nextTurn();
        game.addMarble(0, 0);
        assertEquals("01", game.serialize());
    }

    @Test
    public void deserializeWorks() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        String s = game.getBoard().serialize() + "11";
        Pentago game2 = new Pentago();
        assert(game2.deserialize(s));
        assert(game.equals(game2));
    }

    @Test
    public void getLastMoveWorksAlsoForSerializedPentago() {
        game.addMarble(0, 0);
        game.rotateTile(1, 1, Direction.CLOCKWISE);

        Pentago game2 = new Pentago();
        game2.deserialize(game.serialize());
        Move newMove = new Move(0, new Marble(game.getPlayers()[0].getSymbol()), 0, 0, 1, 1, Direction.CLOCKWISE);
        Move oldMove = game2.getLastMove();
        assertNotNull(oldMove);
        assert (oldMove.equals(newMove));
    }
}