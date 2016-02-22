/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class BoardLineCheckerTest {
    
    Board board;
    BoardLineChecker lineChecker;
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    public BoardLineCheckerTest() {}
    
    @Before
    public void setUp() {
        board = new Board();
        lineChecker = new BoardLineChecker(board);
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
    public void checkLinesWorks(ArrayList<Integer[]> points) {
        Marble o = new Marble(Symbol.O);
        for (Integer[] point : points) {
            board.addMarble(o, point[0], point[1]);
        }
        Line line = (Line) lineChecker.checkLines(5);
        assertNotNull(line);
        assertNotNull(line.getSymbol());
        assertNotNull(line.getCoordinates());
        assertEquals(Symbol.O, line.getSymbol());
        assertEquals(5, ((ArrayList)line.getCoordinates()).size());
        
        // Needed because ArrayList.contains(T[]) doesn't work
        ArrayList<ArrayList<Integer>> copyOfPoints = new ArrayList<>();
        for (Integer[] point : points) copyOfPoints.add(new ArrayList<>(Arrays.asList(point)));
        
        for (Integer[] point : (ArrayList<Integer[]>) line.getCoordinates()) {
            assertTrue(copyOfPoints.contains(new ArrayList<>(Arrays.asList(point))));
        }
    }
    
        // This is not meant to be a test. This is just a help-method for the tests below.
    public void checkLinesReturnsNull(ArrayList<Integer[]> points, ArrayList<Symbol> symbols) {
        for (int i = 0; i < points.size(); i++) {
            Integer[] point = points.get(i);
            Marble m;
            if(symbols != null) {
                m = new Marble(symbols.get(i));
            }  else {
                m = new Marble(Symbol.X);
            }
            board.addMarble(m, point[0], point[1]);
            System.out.println(board.toString());
        }
        Line line = (Line) lineChecker.checkLines(5);
        assertNull(line);
    }
    
    @Test
    public void checkLinesWorksHorizontally() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,4});
        points.add(new Integer[]{2,4});
        points.add(new Integer[]{3,4});
        points.add(new Integer[]{4,4});
        points.add(new Integer[]{5,4});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesWorksVertically() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{3,0});
        points.add(new Integer[]{3,1});
        points.add(new Integer[]{3,2});
        points.add(new Integer[]{3,3});
        points.add(new Integer[]{3,4});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesWorksDiagonallyOverTwoTiles() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,1});
        points.add(new Integer[]{2,2});
        points.add(new Integer[]{3,3});
        points.add(new Integer[]{4,4});
        points.add(new Integer[]{5,5});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesWorksDiagonallyOverThreeTiles() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,0});
        points.add(new Integer[]{2,1});
        points.add(new Integer[]{3,2});
        points.add(new Integer[]{4,3});
        points.add(new Integer[]{5,4});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesWorksDiagonallyOverTwoTilesDirection2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0,5});
        points.add(new Integer[]{1,4});
        points.add(new Integer[]{2,3});
        points.add(new Integer[]{3,2});
        points.add(new Integer[]{4,1});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesWorksDiagonallyOverThreeTilesDirection2() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,5});
        points.add(new Integer[]{2,4});
        points.add(new Integer[]{3,3});
        points.add(new Integer[]{4,2});
        points.add(new Integer[]{5,1});
        checkLinesWorks(points);
    }
    
    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbols() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{0,1});
        points.add(new Integer[]{1,1});
        points.add(new Integer[]{2,1});
        points.add(new Integer[]{3,1});
        points.add(new Integer[]{4,1});
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
        points.add(new Integer[]{0,1});
        points.add(new Integer[]{1,1});
        points.add(new Integer[]{2,1});
        points.add(new Integer[]{3,1});
        checkLinesReturnsNull(points, null);
    }
    
    @Test
    public void checkLinesReturnsNullWhenTheLineGoesPastTheBoard() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,3});
        points.add(new Integer[]{1,4});
        points.add(new Integer[]{1,5});
        points.add(new Integer[]{2,0});
        points.add(new Integer[]{2,1});
        checkLinesReturnsNull(points, null);
    }
    
    @Test
    public void checkLinesReturnsNullWhenTheLineHasDifferentSymbolsPastTheBoard() {
        ArrayList<Integer[]> points = new ArrayList<>();
        points.add(new Integer[]{1,3});
        points.add(new Integer[]{1,4});
        points.add(new Integer[]{1,5});
        points.add(new Integer[]{2,0});
        points.add(new Integer[]{2,1});
        ArrayList<Symbol> symbols = new ArrayList<>();
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.O);
        symbols.add(Symbol.X);
        symbols.add(Symbol.X);
        checkLinesReturnsNull(points, symbols);
    }
}
