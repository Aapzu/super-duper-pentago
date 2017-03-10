
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.DynamicArray;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class LineCheckerTest {

    private Board board;
    private LineChecker lineChecker;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

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
        for (int i = 2; i < 6; i++) {
            assertNull(lineChecker.checkLines(i));
        }
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
    private void checkLinesWorks(DynamicArray<Integer[]> points, DynamicArray<Symbol> symbols, int length) {
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

        for (Integer[] point : line.getCoordinates()) {
            assertTrue(points.contains(point));
        }
    }

    // This is not meant to be a test. This is just a help-method for the tests below.
    private void checkLinesReturnsNull(DynamicArray<Integer[]> points, DynamicArray<Symbol> symbols) {
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksHorizontally2() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{0, 4});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 4});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{3, 0});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{3, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksVertically2() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{3, 0});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{3, 4});
        points.add(new Integer[]{3, 5});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 5});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksDowngradingDiagonallyOverTwoTiles2() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{0, 0});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 2});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 4});
        points.add(new Integer[]{5, 5});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 0});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{4, 3});
        points.add(new Integer[]{5, 4});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesWorksUpgradingDiagonallyOverTwoTiles2() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{0, 5});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{2, 3});
        points.add(new Integer[]{3, 2});
        points.add(new Integer[]{4, 1});
        points.add(new Integer[]{5, 0});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
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
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 4});
        points.add(new Integer[]{3, 3});
        points.add(new Integer[]{4, 2});
        points.add(new Integer[]{5, 1});
        checkLinesWorks(points, null, 5);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbols() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{0, 1});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 1});
        points.add(new Integer[]{4, 1});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.O);
        checkLinesReturnsNull(points, symbols);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineIsNotLongEnough() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{0, 1});
        points.add(new Integer[]{1, 1});
        points.add(new Integer[]{2, 1});
        points.add(new Integer[]{3, 1});
        checkLinesReturnsNull(points, null);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineGoesPastTheBoard() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 3});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 0});
        points.add(new Integer[]{2, 1});
        checkLinesReturnsNull(points, null);
    }

    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbolsPastTheBoard() {
        DynamicArray<Integer[]> points = new DynamicArray<>();
        points.add(new Integer[]{1, 3});
        points.add(new Integer[]{1, 4});
        points.add(new Integer[]{1, 5});
        points.add(new Integer[]{2, 0});
        points.add(new Integer[]{2, 1});
        DynamicArray<Symbol> symbols = new DynamicArray<>();
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.X);
        checkLinesReturnsNull(points, symbols);
    }

    @Test
    public void checkLinesWorksWithShorterLines() {
        DynamicArray<Integer[]> points = new DynamicArray<>();

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
