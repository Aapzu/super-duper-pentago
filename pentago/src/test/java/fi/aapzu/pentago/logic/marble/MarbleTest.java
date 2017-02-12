package fi.aapzu.pentago.logic.marble;

import fi.aapzu.pentago.logic.Board;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarbleTest {

    private Marble m;

    public MarbleTest() {
    }

    @Before
    public void setUp() {
        m = new Marble(Symbol.X);
    }

    @After
    public void tearDown() {
        m = null;
    }

    @Test
    public void constructorSetsTheSymbol() {
        assertEquals(m.getSymbol(), Symbol.X);
    }

    @Test
    public void copyConstructorWorks() {
        Marble m1 = new Marble(Symbol.O);
        Marble m2 = new Marble(m1);
        assert (m1.equals(m2));
    }

    @Test
    public void toStringReturnsRightString() {
        assertEquals("[X]", m.toString());
        m = new Marble(Symbol.O);
        assertEquals("[O]", m.toString());
    }

    @Test
    public void equalsReturnsTrueWhenTwoMarbleswithSameSymbols() {
        assertTrue(m.equals(new Marble(Symbol.X)));
        m = new Marble(Symbol.O);
        assertTrue(m.equals(new Marble(Symbol.O)));
    }

    @Test
    public void equalsReturnsFalseWhenTwoMarblesAreNotEquals() {
        assertFalse(m.equals(new Marble(Symbol.O)));
        assertFalse(m.equals(new Board()));
        assertFalse(m.equals(null));
    }

    @Test
    public void serializationWorks() {
        assertEquals(m.serialize(), "1");
        m.setSymbol(Symbol.O);
        assertEquals(m.serialize(), "2");
    }

    @Test
    public void deserializationWorks() {
        assert (m.deserialize("2"));
        assertEquals(m.getSymbol(), Symbol.O);
        assert (m.deserialize("1"));
        assertEquals(m.getSymbol(), Symbol.X);
        assertFalse(m.deserialize("moi"));
    }
}
