package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.util.DynamicArray;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void getReturnsRightGamesInTheBeginning() {
        DynamicArray<String> games = PossibleGamesWithOneMove.get(game.serialize());
        assertEquals(288, games.size());

        for (String p : games) {
            int marbles = 0;
            assertEquals(40, p.length());
            char[] chars = p.toCharArray();
            for (int i = 0; i < 36; i++) {
                char c = chars[i];
                assert (c == '0' || c == '1' || c == '2');
                if (c != '0') {
                    marbles++;
                }
            }
            assertEquals(1, marbles);
            assert (chars[36] == '0' || chars[36] == '1');
            assert (chars[37] == '0' || chars[37] == '1');
            assert (chars[38] == '1' || chars[38] == '2');
        }
    }

    @Test
    public void itOnlyReturnsValidMoves() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        DynamicArray<String> games = PossibleGamesWithOneMove.get(game.serialize());
        for (String s : games) {
            Pentago g = new Pentago();
            g.deserialize(s);
            Move m = g.getLastMove();
            assertNotNull(m);
            assertEquals(m.getPlayer(), game.getWhoseTurnIndex());
            assert (m.getMarbleX() != 2 || m.getMarbleY() != 0);
            assert (m.getTileX() != 0 || m.getTileY() != 0 || m.getRotateDirection() != Direction.COUNTER_CLOCKWISE);
        }
    }
}
