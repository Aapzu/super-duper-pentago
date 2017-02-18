package fi.aapzu.pentago.util;

import fi.aapzu.pentago.logic.marble.Marble;
import org.junit.Test;

import static fi.aapzu.pentago.logic.marble.Symbol.O;
import static fi.aapzu.pentago.logic.marble.Symbol.X;
import static org.junit.Assert.assertEquals;

public class ArrayUtilsTest {

    @Test
    public void deepEqualsWorks() {
        String[] a1 = new String[]{"1", "2", "3"};
        String[] a2 = new String[]{"1", "2", "3"};
        assert(ArrayUtils.deepEquals(a1, a2));

        String[] a3 = new String[]{"1", "2", "3", "4"};
        String[] a4 = new String[]{"1", "2", "3", "4"};
        assert(!ArrayUtils.deepEquals(a1, a3));
        assert(ArrayUtils.deepEquals(a3, a4));

        String[] a5 = new String[]{"1", "3", "3"};
        assert(!ArrayUtils.deepEquals(a1, a5));

        String[][] a6 = new String[][]{
                {"1", "2"}, {"3", "4"}
        };
        String[][] a7 = new String[][]{
                {"1", "2"}, {"3", "4"}
        };
        String[][] a8 = new String[][]{
                {"1", "2"}, {"4", "4"}
        };
        assert(ArrayUtils.deepEquals(a6, a7));
        assert(!ArrayUtils.deepEquals(a6, a8));

        assert(ArrayUtils.deepEquals(new Marble[][]{
                {new Marble(O), null, null, null, new Marble(X), null},
                {null, new Marble(O), new Marble(O), null, new Marble(X), null},
                {null, null, null, null, new Marble(X), null},
                {null, null, null, null, null, null},
                {null, new Marble(O), new Marble(O), null, new Marble(O), null},
                {null, null, null, null, null, null}
        }, new Marble[][]{
                {new Marble(O), null, null, null, new Marble(X), null},
                {null, new Marble(O), new Marble(O), null, new Marble(X), null},
                {null, null, null, null, new Marble(X), null},
                {null, null, null, null, null, null},
                {null, new Marble(O), new Marble(O), null, new Marble(O), null},
                {null, null, null, null, null, null}
        }));

    }

    @Test
    public void asListWorks() {
        Marble[] m1 = new Marble[30];
        m1[10] = new Marble(O);
        m1[20] = new Marble(X);
        DynamicArray<Marble> da = ArrayUtils.asList(m1);
        assertEquals(30, da.size());
        assertEquals(new Marble(O), da.get(10));
        assertEquals(new Marble(X), da.get(20));
    }
}
