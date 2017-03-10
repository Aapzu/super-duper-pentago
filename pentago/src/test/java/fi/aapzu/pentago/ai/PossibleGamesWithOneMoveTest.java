package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.util.DynamicArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PossibleGamesWithOneMoveTest {

    private Pentago game;

    @Before
    public void setUp() {
        game = new Pentago();
        game.addHumanPlayer("test1");
        game.addHumanPlayer("test2");
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void getMarbleXFromSerializationStringIndexWorks() {
        DynamicArray[] answers = new DynamicArray[]{
                new DynamicArray<>(new Integer[]{0,3,6,18,21,24}),
                new DynamicArray<>(new Integer[]{1,4,7,19,22,25}),
                new DynamicArray<>(new Integer[]{2,5,8,20,23,26}),
                new DynamicArray<>(new Integer[]{9,12,15,27,30,33}),
                new DynamicArray<>(new Integer[]{10,13,16,28,31,34}),
                new DynamicArray<>(new Integer[]{11,14,17,29,32,35}),
        };
        for (int i = 0; i < 36; i++) {
            char y = PossibleGamesWithOneMove.getMarbleXFromSerializationStringIndex(i);
            assertTrue("i: " + i + ", x: " + y, answers[Character.getNumericValue(y)].contains(i));
        }
    }

    @Test
    public void getMarbleYFromSerializationStringIndexWorks() {
        DynamicArray[] answers = new DynamicArray[]{
                new DynamicArray<>(new Integer[]{0,1,2,9,10,11}),
                new DynamicArray<>(new Integer[]{3,4,5,12,13,14}),
                new DynamicArray<>(new Integer[]{6,7,8,15,16,17}),
                new DynamicArray<>(new Integer[]{18,19,20,27,28,29}),
                new DynamicArray<>(new Integer[]{21,22,23,30,31,32}),
                new DynamicArray<>(new Integer[]{24,25,26,33,34,35}),
        };
        for (int i = 0; i < 36; i++) {
            char y = PossibleGamesWithOneMove.getMarbleYFromSerializationStringIndex(i);
            assertTrue("i: " + i + ", y: " + y, answers[Character.getNumericValue(y)].contains(i));
        }
    }

    @Test
    public void getReturnsRightGamesInTheBeginning() {
        String s = game.serialize();
        DynamicArray<String> games = PossibleGamesWithOneMove.get(s);
        assertEquals(288, games.size());

        for (String p : games) {
            int marbles = 0;
            assertEquals(43, p.length());
            char[] chars = p.toCharArray();
            for (int i = 0; i < 36; i++) {
                char c = chars[i];
                assertTrue(c == '0' || c == '1' || c == '2');
                if (c != '0') {
                    marbles++;
                }
            }
            assertEquals(1, marbles);
            assertTrue(chars[38] == '0' || chars[38] == '1'); // Last tile X
            assertTrue(chars[39] == '0' || chars[39] == '1'); // Last tile y
            assertTrue(chars[40] == '1' || chars[40] == '2'); // Last tile Direction
            assertTrue(chars[41] == '1'); // Whose turn index
            assertTrue(chars[42] == '1'); // Game has started
        }
    }

    @Test
    public void itOnlyReturnsValidMoves() {
        game.addMarble(0, 0);
        game.rotateTile(1, 1, Direction.CLOCKWISE);
        DynamicArray<String> games = PossibleGamesWithOneMove.get(game.serialize());
        assertEquals(245, games.size());
        for (String s : games) {
            Pentago g = new Pentago();
            assertTrue(g.deserialize(s));
            Move m = g.getLastMove();
            assertNotNull(m);
            assertFalse(m.getMarbleX() == 0 && m.getMarbleY() == 0);
            assertEquals(m.getPlayer(), game.getWhoseTurnIndex());
            assertTrue(m.getMarbleX() != 0 || m.getMarbleY() != 0);
            assertTrue(m.getTileX() != 1 || m.getTileY() != 1 || m.getRotateDirection() != Direction.COUNTER_CLOCKWISE);
        }
    }
}
