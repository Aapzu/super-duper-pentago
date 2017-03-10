/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Aapeli
 */
public class MarblesInSameTileTest {

    private Pentago game;

    /*
        These can be changed easily if the values in original class need to be checked
     */
    private static int one = 0;
    private static final int twoD = 10;
    private static final int two = 20;
    private static final int threeD = 100;
    private static final int three = 200;

    public MarblesInSameTileTest() {
    }

    @Before
    public void setUp() {
        game = new Pentago();
        game.addHumanPlayer("a");
        game.addHumanPlayer("b");
    }

    @After
    public void tearDown() {
        game = null;
    }

    @Test
    public void getScoreWorksWithEmptyBoard() {
        assertEquals(0, new MarblesInSameTile().getScore(game, 1));
        assertEquals(0, new MarblesInSameTile().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithLinesOfOne() {
        setBoard("-X---\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(one, new MarblesInSameTile().getScore(game, 0));
        setBoard("------\n" +
                "------\n" +
                "X-----\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(one, new MarblesInSameTile().getScore(game, 0));
        setBoard("------\n" +
                "------\n" +
                "--X---\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(one, new MarblesInSameTile().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithLineInOneTile() {
        setBoard("XXX---\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(three, new MarblesInSameTile().getScore(game, 0));
        setBoard("X-----\n" +
                "X-----\n" +
                "X-----\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(three, new MarblesInSameTile().getScore(game, 0));
        setBoard("X-----\n" +
                "-X----\n" +
                "--X---\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(threeD, new MarblesInSameTile().getScore(game, 0));
        setBoard("--X---\n" +
                "-X----\n" +
                "X-----\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(threeD, new MarblesInSameTile().getScore(game, 0));
        setBoard("XX----\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(two, new MarblesInSameTile().getScore(game, 0));
        setBoard("X------\n" +
                "X-----\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(two, new MarblesInSameTile().getScore(game, 0));
        setBoard("X-----\n" +
                "-X----\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(twoD, new MarblesInSameTile().getScore(game, 0));
        setBoard("--X---\n" +
                "-X----\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(twoD, new MarblesInSameTile().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithMultipleTiles() {
        setBoard("XXX--X\n" +
                "-----X\n" +
                "-----X\n" +
                "------\n" +
                "-X----\n" +
                "--X---\n");
        assertEquals(three + three + twoD, new MarblesInSameTile().getScore(game, 0));
    }

    @Test
    public void lineDoesNotCountOverTileBorder() {
        setBoard("------\n" +
                "-XXX--\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(two, new MarblesInSameTile().getScore(game, 0));
    }

    @Test
    public void linesInSameTileAreAddedUp() {
        setBoard("X-----\n" +
                "-XX---\n" +
                "--X---\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(threeD + two + two, new MarblesInSameTile().getScore(game, 0));
    }

    private void setBoard(String board) {
        Board b = new Board();
        int y = 0;
        int x = 0;
        char[] chars = board.toCharArray();
        for (char c : chars) {
            switch (c) {
                case 'O':
                    b.addMarble(new Marble(Symbol.O), x, y);
                    break;
                case 'X':
                    b.addMarble(new Marble(Symbol.X), x, y);
                    break;
                case '\n':
                    y++;
                    x = -1;
            }
            x++;
        }
        game.setBoard(b);
    }

}
