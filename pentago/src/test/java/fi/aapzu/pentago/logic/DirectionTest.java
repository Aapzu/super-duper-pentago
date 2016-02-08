
package fi.aapzu.pentago.logic;

import java.util.Arrays;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class DirectionTest {
    
    public DirectionTest() {}

    @Test
    public void getRotateDirectionsReturnsRight() {
        assertTrue(Arrays.equals(new Direction[]{Direction.CLOCKWISE, Direction.COUNTER_CLOCKWISE}, Direction.getRotateDirections()));
    }

    @Test
    public void getLineDirectionsReturnsRight() {
        assertTrue(Arrays.equals(new Direction[]{Direction.HORIZONTAL, Direction.VERTICAL, Direction.UPGRADING_DIAGONAL, Direction.DOWNGRADING_DIAGONAL}, Direction.getLineDirections()));
    }
    
    @Test
    public void getOppositeReturnsRight() {
        assertEquals(Direction.CLOCKWISE, Direction.getOpposite(Direction.COUNTER_CLOCKWISE));
        assertEquals(Direction.COUNTER_CLOCKWISE, Direction.getOpposite(Direction.CLOCKWISE));
    }
    
    @Test
    public void getOppositeReturnsNullIfNoOpposite() {
        assertNull(Direction.getOpposite(Direction.HORIZONTAL));
        assertNull(Direction.getOpposite(Direction.VERTICAL));
        assertNull(Direction.getOpposite(Direction.DOWNGRADING_DIAGONAL));
        assertNull(Direction.getOpposite(Direction.UPGRADING_DIAGONAL));
    }
}
