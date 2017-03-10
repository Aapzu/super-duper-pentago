
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.util.ArrayUtils;
import org.junit.Test;

import static org.junit.Assert.*;


public class DirectionTest {

    public DirectionTest() {
    }

    @Test
    public void getRotateDirectionsReturnsRight() {
        assertTrue(ArrayUtils.deepEquals(
                new Direction[]{Direction.CLOCKWISE, Direction.COUNTER_CLOCKWISE},
                Direction.getRotateDirections())
        );
    }

    @Test
    public void getLineDirectionsReturnsRight() {
        assertTrue(ArrayUtils.deepEquals(
                new Direction[]{Direction.HORIZONTAL, Direction.VERTICAL, Direction.UPGRADING_DIAGONAL, Direction.DOWNGRADING_DIAGONAL},
                Direction.getLineDirections())
        );
    }

    @Test
    public void directionOppositesAreCorrect() {
        assertEquals(Direction.CLOCKWISE.opposite, Direction.COUNTER_CLOCKWISE);
        assertEquals(Direction.COUNTER_CLOCKWISE.opposite, Direction.CLOCKWISE);
    }
}
