package fi.aapzu.pentago.util;

import fi.aapzu.pentago.logic.marble.Marble;
import org.junit.Test;

import static fi.aapzu.pentago.logic.marble.Symbol.O;
import static fi.aapzu.pentago.logic.marble.Symbol.X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArrayUtilsTest {

    @Test
    public void deepEqualsWorks() {
        String[] a1 = new String[]{"1", "2", "3"};
        String[] a2 = new String[]{"1", "2", "3"};
        assertTrue(ArrayUtils.deepEquals(a1, a2));

        // Same items
        assertTrue(ArrayUtils.deepEquals(a1, a1));

        // null item
        assertFalse(ArrayUtils.deepEquals(a1, null));
        assertFalse(ArrayUtils.deepEquals(null, a1));

        String[] a3 = new String[]{"1", "2", "3", "4"};
        String[] a4 = new String[]{"1", "2", "3", "4"};
        assertFalse(ArrayUtils.deepEquals(a1, a3));
        assertTrue(ArrayUtils.deepEquals(a3, a4));

        String[] a5 = new String[]{"1", "3", "3"};
        assertFalse(ArrayUtils.deepEquals(a1, a5));

        String[][] a6 = new String[][]{
                {"1", "2"}, {"3", "4"}
        };
        String[][] a7 = new String[][]{
                {"1", "2"}, {"3", "4"}
        };
        String[][] a8 = new String[][]{
                {"1", "2"}, {"4", "4"}
        };
        assertTrue(ArrayUtils.deepEquals(a6, a7));
        assertFalse(ArrayUtils.deepEquals(a6, a8));

        assertTrue(ArrayUtils.deepEquals(new Marble[][]{
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
        assertFalse(ArrayUtils.deepEquals(new Integer[]{null}, new Integer[]{2}));

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

    @Test
    public void reverseWorks() {
        int[] arr = new int[]{1, 2, 3, 4};
        int[] arr2 = new int[]{4, 3, 2, 1};
        int[] arr3 = ArrayUtils.reverse(ArrayUtils.reverse(arr));
        for (int i = 0; i < 4; i++) {
            assertEquals(ArrayUtils.reverse(arr)[i], arr2[i]);
            assertEquals(arr[i], arr3[i]);
        }
    }
}
