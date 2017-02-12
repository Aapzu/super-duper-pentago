package fi.aapzu.pentago.ai.heuristics;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PossibleLinesTest {

    private Pentago game;

    /*
        These can be changed easily if the values in original class need to be checked
     */
    private static final int initialPoints = 10;
    private static final int pointFactor = 10;

    public PossibleLinesTest() {
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
        assertEquals(0, new PossibleLines().getScore(game, 1));
        assertEquals(0, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithOneOfALine() {
        setBoard("--X---\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        assertEquals(3 * initialPoints, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithTwoOfALine() {
        setBoard("--X---\n" +
                "--X---\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        int points = 6 * initialPoints + initialPoints * pointFactor;
        assertEquals(points, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithThreeOfALine() {
        setBoard("---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "------\n" +
                "------\n" +
                "------\n");
        int points = 10 * initialPoints +
                initialPoints * pointFactor +
                initialPoints * (int) Math.pow(pointFactor, 2);
        assertEquals(points, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithFourOfALine() {
        setBoard("---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "------\n" +
                "------\n");
        int points = 15 * initialPoints +
                initialPoints * (int) Math.pow(pointFactor, 2) +
                initialPoints * (int) Math.pow(pointFactor, 3);
        assertEquals(points, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreWorksWithFiveOfALine() {
        setBoard("---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "---X--\n" +
                "------\n");
        assertEquals(Integer.MAX_VALUE, new PossibleLines().getScore(game, 0));
    }

    @Test
    public void getScoreAddsPointsForOwnAndReducesForOpponents() {
        setBoard("OXXX--\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n" +
                "------\n");
        int points = 2 * initialPoints +
                initialPoints * (int) Math.pow(pointFactor, 2);
        assertEquals(points, new PossibleLines().getScore(game, 0));
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
