/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.ArrayUtils;
import fi.aapzu.pentago.util.DynamicArray;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LineCheckerTest {

    private Board board;
    private LineChecker lineChecker;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private static long ts;

    @BeforeClass
    public static void setUpTest() {
        ts = System.currentTimeMillis();
    }

    @AfterClass
    public static void tearDownTest() {
        long ts2 = System.currentTimeMillis();
        System.out.println("Time: " + (ts2 - ts));
    }

    @Before
    public void setUp() {
        board = new Board();
        lineChecker = new LineChecker(board);
    }

    @After
    public void tearDown() {
        board = null;
        lineChecker = null;
    }

    @Test
    public void checkLinesThrowsExceptionWithRightTextIfLengthIsUnder2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("must be between 2 and 6");
        lineChecker.checkLines(1);
    }

    @Test
    public void checkLinesThrowsExceptionIfTheDirectionIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("direction is incorrect");
        lineChecker.checkLines(5, Direction.CLOCKWISE);
        lineChecker.checkLines(5, Direction.COUNTER_CLOCKWISE);
    }

    @Test
    public void checkLinesThrowsExceptionWithRightTextIfLengthIsOver6() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("must be between 2 and 6");
        lineChecker.checkLines(7);
    }

    @Test
    public void checkLinesThrowsExceptionIfNoRows() {
        exception.expect(PentagoGameRuleException.class);
        for (int i = 2; i < 6; i++)
            assertNull(lineChecker.checkLines(i));
        Marble m = new Marble(Symbol.X);
        board.addMarble(m, 0, 0);
        board.addMarble(m, 2, 1);
        board.addMarble(m, 0, 0);
        board.addMarble(m, 4, 0);
        board.addMarble(m, 4, 4);
        board.addMarble(m, 0, 3);
        board.addMarble(m, 0, 5);
        for (int i = 2; i < 6; i++)
            assertNull(lineChecker.checkLines(i));
    }

    // This is not meant to be a test. This is just a help-method for the tests below.
    private void checkLinesWorks(ArrayList<Integer[]> points, ArrayList<Symbol> symbols, int length) {
        for (int i = 0; i < points.size(); i++) {
            Integer[] point = points.get(i);
            Marble m;
            if (symbols != null) {
                m = new Marble(symbols.get(i));
            } else {
                m = new Marble(Symbol.O);
            }
            board.addMarble(m, point[0], point[1]);
        }
        Line line = lineChecker.checkLines(length);
        assertNotNull(line);
        assertNotNull(line.getSymbol());
        assertNotNull(line.getCoordinates());
        assertEquals(Symbol.O, line.getSymbol());
        assertEquals(length, line.getCoordinates().size());

        // Needed because ArrayList.contains(T[]) doesn't work
        DynamicArray<DynamicArray<Integer>> copyOfPoints = new DynamicArray<>();
        for (Integer[] point : points) {
            copyOfPoints.add(ArrayUtils.asList(point));
        }

        for (Integer[] point : line.getCoordinates()) {
            assertTrue(copyOfPoints.contains(new DynamicArray<>(point)));
        }
    }

    // This is not meant to be a test. This is just a help-method for the tests below.
    private void checkLinesReturnsNull(ArrayList<Integer[]> points, ArrayList<Symbol> symbols) {
        for (int i = 0; i < points.size(); i++) {
            Integer[] point = points.get(i);
            Marble m;
            if (symbols != null) {
                m = new Marble(symbols.get(i));
            } else {
                m = new Marble(Symbol.X);
            }
            board.addMarble(m, point[0], point[1]);
        }
        Line line = (Line) lineChecker.checkLines(5);
        assertNull(line);
    }

    @Test
    public void checkLinesWorksHorizontally() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksHorizontally2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 4});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 4});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        checkLinesWorks(points, symbols, 5);
    }

    @Test
    public void checkLinesWorksHorizontally3() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 4});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 4});
        checkLinesWorks(points, null, 6);
    }

    @Test
    public void checkLinesWorksVertically() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{3, 0});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{3, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksVertically2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{3, 0});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{3, 5});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        checkLinesWorks(points, symbols, 5);
    }

    @Test
    public void checkLinesWorksDowngradingDiagonallyOverTwoTiles() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 5});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksDowngradingDiagonallyOverTwoTiles2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 0});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 5});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        checkLinesWorks(points, symbols, 5);
    }

    @Test
    public void checkLinesWorksDowngradingDiagonallyOverThreeTiles() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 0});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{4, 3});
        points.add(new Integer[]{5, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksUpgradingDiagonallyOverTwoTiles2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 5});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 3});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{4, 1});
        points.add(new Integer[]{5, 0});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        checkLinesWorks(points, symbols, 5);
    }

    @Test
    public void checkLinesWorksUpgradingDiagonallyOverThreeTiles() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 2});
        points.add(new Integer[]{5, 1});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbols() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 1});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{4, 1});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        checkLinesReturnsNull(points, symbols);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineIsNotLongEnough() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0, 1});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 1});
        checkLinesReturnsNull(points, null);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineGoesPastTheBoard() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 3});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 0});
        points.add(new Integer[]{2, 1});
        checkLinesReturnsNull(points, null);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbolsPastTheBoard() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1, 3});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 0});
        points.add(new Integer[]{2, 1});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.X);
        checkLinesReturnsNull(points, symbols);
    }

    @Test
    public void checkLinesWorksWithShorterLines() {
        ArrayList<Integer[]> points = new ArrayList<>();

        points.add(new Integer[]{0, 1});
        points.add(new Integer[]{1, 0});
        checkLinesWorks(points, null, 2);

        board.clear();
        points.clear();
        points.add(new Integer[]{0, 4});
        points.add(new Integer[]{1, 5});
        checkLinesWorks(points, null, 2);

        board.clear();
        points.clear();
        points.add(new Integer[]{5, 1});
        points.add(new Integer[]{4, 0});
        checkLinesWorks(points, null, 2);

        board.clear();
        points.clear();
        points.add(new Integer[]{5, 4});
        points.add(new Integer[]{4, 5});
        checkLinesWorks(points, null, 2);
    }

    @Test
    public void equalsWorks() {
        board.addMarble(new Marble(Symbol.O), 0, 0);
        Board board2 = new Board();
        board2.addMarble(new Marble(Symbol.O), 0, 0);
        LineChecker lc2 = new LineChecker(board2);
        assert(lineChecker.equals(lc2));
        assert(lc2.equals(lineChecker));
    }
}
