package fi.aapzu.pentago;


import fi.aapzu.pentago.ai.AlphaBetaPruning;
import fi.aapzu.pentago.ai.PentagoNode;
import fi.aapzu.pentago.game.Pentago;

/**
 * A simple class to test and print some performance testing about the program.
 */
public class PerformanceTester {

    static Pentago game;
    static AlphaBetaPruning alphaBetaPruning;

    /**
     * Runs the PerformanceTester.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        game = new Pentago();
        alphaBetaPruning = new AlphaBetaPruning();
        game.addHumanPlayer("P1");
        game.addHumanPlayer("P2");

        initGame();
        testAlphaBetaPruning(2, 10);
        testAlphaBetaPruning(3, 2);
    }

    private static void initGame() {
        game.deserialize("0000100000200000000000000000100000004300211");
        System.out.println(game.serialize());
    }

    private static void testAlphaBetaPruning(int moves, int times) {
        long ts1, time;
        int average = 0;
        System.out.println("MovesAhead: " + moves + ", Tests: " + times);
        for (int i = 0; i < times; i++) {
            ts1 = System.currentTimeMillis();
            PentagoNode n = new PentagoNode(game.serialize());
            alphaBetaPruning.getBest(n, moves);
            time = System.currentTimeMillis() - ts1;
            average += time / times;
            alphaBetaPruning.clear();
        }
        System.out.println("average: " + average + "ms");
    }
}
