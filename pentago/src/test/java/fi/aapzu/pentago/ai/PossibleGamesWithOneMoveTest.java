package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PossibleGamesWithOneMoveTest {

    Pentago game;

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
        Pentago[] games = PossibleGamesWithOneMove.get(game);
        assertEquals(games.length, 288);

        for (Pentago p : games) {
            int marbles = 0;
            int sideLength = p.getBoard().getSideLength() * p.getBoard().getTileSideLength();
            assertEquals(sideLength, game.getBoard().getSideLength() * game.getBoard().getTileSideLength());
            for (int y = 0; y < sideLength; y++) {
                for (int x = 0; x < sideLength; x++) {
                    if (p.getBoard().getMarble(x, y) != null) {
                        marbles++;
                    }
                }
            }
            assertEquals(1, marbles);
            assertNotNull(p.getBoard().getLastMarble());
            assertNotNull(p.getBoard().getLastMarbleX());
            assertNotNull(p.getBoard().getLastMarbleY());
            assertNotNull(p.getBoard().getLastRotatedTile());
            assertNotNull(p.getBoard().getLastRotatedTileDirection());
            assertNotNull(p.getBoard().getLastRotatedTileX());
            assertNotNull(p.getBoard().getLastRotatedTileY());
        }
    }

    @Test
    public void itOnlyReturnsValidMoves() {
        game.addMarble(0, 0);
        game.rotateTile(0, 0, Direction.CLOCKWISE);
        Pentago[] games = PossibleGamesWithOneMove.get(game);
        for (Pentago p : games) {
            Move m = p.getLastMove();
            assert(m.getPlayer().equals(game.getPlayers()[1]));
            assert(m.getMarbleX() != 2 || m.getMarbleY() != 0);
            assert(m.getTileX() != 0 || m.getTileY() != 0 || m.getRotateDirection() != Direction.COUNTER_CLOCKWISE);
        }
    }
}
