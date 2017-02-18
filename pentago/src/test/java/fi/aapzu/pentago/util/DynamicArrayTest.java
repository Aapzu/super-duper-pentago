package fi.aapzu.pentago.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DynamicArrayTest {

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
        DynamicArray<Long> da = new DynamicArray<>();

    }


}
