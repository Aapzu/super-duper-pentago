package fi.aapzu.pentago.logic;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


public class DirectionTest {

    public DirectionTest() {
    }

    @Test
    public void getRotateDirectionsReturnsRight() {
        assertTrue(Arrays.equals(new Direction[]{Direction.CLOCKWISE, Direction.COUNTER_CLOCKWISE}, Direction.getRotateDirections()));
    }

    @Test
    public void getLineDirectionsReturnsRight() {
        assertTrue(Arrays.equals(new Direction[]{Direction.HORIZONTAL, Direction.VERTICAL, Direction.UPGRADING_DIAGONAL, Direction.DOWNGRADING_DIAGONAL}, Direction.getLineDirections()));
    }

    @Test
    public void directionOppositesAreCorrect() {
        assertEquals(Direction.CLOCKWISE.opposite, Direction.COUNTER_CLOCKWISE);
        assertEquals(Direction.COUNTER_CLOCKWISE.opposite, Direction.CLOCKWISE);
    }
}
