package fi.aapzu.pentago.util;

/**
 * A collection of utility methods for modifying arrays.
 */
public class ArrayUtils {

    public static boolean deepEquals(Object[] a1, Object[] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (a1[i] == a2[i]) {
                continue;
            }
            boolean equals;
            if (a1[i] instanceof Object[] && a2[i] instanceof Object[]) {
                equals = deepEquals((Object[]) a1[i], (Object[]) a2[i]);
            } else {
                equals = a1[i] != null && a1[i].equals(a2[i]);
            }
            if (!equals) {
                return false;
            }
        }
        return true;
    }

    public static <T> DynamicArray<T> asList(T[] array) {
        DynamicArray<T> list = new DynamicArray<>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }
}
