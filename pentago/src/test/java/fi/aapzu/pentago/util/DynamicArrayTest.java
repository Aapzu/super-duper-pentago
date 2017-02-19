package fi.aapzu.pentago.util;


import fi.aapzu.pentago.logic.marble.Marble;
import org.junit.Test;

import static fi.aapzu.pentago.logic.marble.Symbol.O;
import static fi.aapzu.pentago.logic.marble.Symbol.X;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class DynamicArrayTest {

    @Test
    public void arrayConstructorWorks() {
        Long[] longs = new Long[]{0L, 1L, 2L};
        DynamicArray<Long> da = new DynamicArray<>(longs);
        da.add(3L);
        da.add(4L);
        assertEquals(5, da.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(new Long(i), da.get(i));
        }
    }

    @Test
    public void addWorksCorrectly() {
        DynamicArray<String> a = new DynamicArray<>();
        a.add("10");
        a.add("20");
        a.add("30");
        String test = "";
        for (String s : a) {
            test += s;
        }
        assertEquals("102030", test);
        assertEquals(3, a.size());
    }

    @Test
    public void addExpandsDynamicArray() {
        DynamicArray<Integer[]> a = new DynamicArray<>();
        for (int i = 0; i < 100; i++) {
            a.add(new Integer[]{i});
        }
        assertEquals(100, a.size());
        Integer[][] arrays = new Integer[100][1];
        int i = 0;
        for (Integer[] arr : a) {
            arrays[i] = arr;
            i++;
        }
        assertEquals(new Integer(36), arrays[36][0]);
        assertEquals(new Integer(50), arrays[50][0]);
        assertEquals(new Integer(99), arrays[99][0]);
    }

    @Test
    public void sizeWorksCorrectly() {
        DynamicArray<Long> da = new DynamicArray<>();
        assertEquals(0, da.size());
        da.add(1L);
        assertEquals(1, da.size());
        da.add(1L);
        assertEquals(2, da.size());
        for (int i = 0; i < 100; i++) {
            da.add(1L);
        }
        assertEquals(102, da.size());
    }

    @Test
    public void isEmptyWorksCorrectly() {
        DynamicArray<String> da1 = new DynamicArray<>();
        da1.add("moi");
        DynamicArray<String> da2 = new DynamicArray<>();

        assertFalse(da1.isEmpty());
        assert(da2.isEmpty());
    }

    @Test
    public void getWorksCorrectly() {
        DynamicArray<Long> da = new DynamicArray<>();
        for (int i = 0; i < 100; i++) {
            da.add((long) i);
        }
        assertEquals((Long) 36L,da.get(36));
        assertEquals((Long) 69L,da.get(69));
    }

    @Test
    public void containsWorksCorrectly() {
        DynamicArray<Long> da = new DynamicArray<>(new Long[]{1L, 3L, 5L});
        assert(da.contains(1L));
        assertFalse(da.contains(2L));
        assert(da.contains(3L));
        assertFalse(da.contains(4L));
        assert(da.contains(5L));

        DynamicArray<Marble[]> da2 = new DynamicArray<>(new Marble[][]{
                {new Marble(O), new Marble(X), null},
                {null, new Marble(X), null},
                {new Marble(O), new Marble(O), new Marble(O)},
        });
        assert(da2.contains(new Marble[]{new Marble(O), new Marble(X), null}));
        assert(da2.contains(new Marble[]{null, new Marble(X), null}));
        assert(da2.contains(new Marble[]{new Marble(O), new Marble(O), new Marble(O)}));
        assertFalse(da2.contains(new Marble[]{new Marble(X), new Marble(O), new Marble(O)}));
        assertFalse(da2.contains(new Marble[]{null, null, null}));
    }

    @Test
    public void equalsWorksCorrectly() {
        DynamicArray<Marble[]> da1 = new DynamicArray<>(new Marble[][]{
                {new Marble(O), null, null, null, new Marble(X), null},
                {null, new Marble(O), new Marble(O), null, new Marble(X), null},
                {null, null, null, null, new Marble(X), null},
                {null, null, null, null, null, null},
                {null, new Marble(O), new Marble(O), null, new Marble(O), null},
                {null, null, null, null, null, null}
        });
        DynamicArray<Marble[]> da2 = new DynamicArray<>(new Marble[][]{
                {new Marble(O), null, null, null, new Marble(X), null},
                {null, new Marble(X), new Marble(O), null, new Marble(X), null},
                {null, null, null, null, new Marble(X), null},
                {null, null, null, null, null, null},
                {null, new Marble(O), new Marble(O), null, new Marble(O), null},
                {null, null, null, null, null, null}
        });
        DynamicArray<Marble[]> da3 = new DynamicArray<>(new Marble[][]{
                {new Marble(O), null, null, null, new Marble(X), null},
                {null, new Marble(O), new Marble(O), null, new Marble(X), null},
                {null, null, null, null, new Marble(X), null},
                {null, null, null, null, null, null},
                {null, new Marble(O), new Marble(O), null, new Marble(O), null},
                {null, null, null, null, null, null}
        });
        DynamicArray<String> da4 = new DynamicArray<>();
        da4.add("test");
        da4.add("test2");
        DynamicArray<String> da5 = new DynamicArray<>();
        da5.add("test");

        assert(da1.equals(da3));
        assertFalse(da1.equals(da2));
        assertFalse(da2.equals(da3));

        assertFalse(da4.equals(da5));
        da5.add("test2");
        assert(da4.equals(da5));
    }

    @Test
    public void clearWorksCorrectly() {
        DynamicArray da = new DynamicArray();
        da.add("moi");
        assertFalse(da.isEmpty());
        da.clear();
        assert(da.isEmpty());
        for (int i = 0; i < 100; i++) {
            da.add(new Integer[]{1, 2, 3});
        }
        da.clear();
        assert(da.isEmpty());
    }
}
