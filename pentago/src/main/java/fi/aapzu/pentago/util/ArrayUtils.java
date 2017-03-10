package fi.aapzu.pentago.util;

/**
 * A collection of utility methods for modifying arrays.
 */
public class ArrayUtils {

    /**
     * Tests if two arrays have equal items in them.
     * If arrays contain other arrays, also tests if they are equal.
     * @param a1 first array
     * @param a2 another array
     * @return true if equals, else false
     */
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

    /**
     * Return a DynamicList which contains the same elements than the array.
     *
     * @param array array to be returned as a list
     * @param <T> type of the items in list
     * @return list containing the items
     */
    public static <T> DynamicArray<T> asList(T[] array) {
        DynamicArray<T> list = new DynamicArray<>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }

    /**
     * Gives the same array but in reverse order.
     *
     * @param array array to be reversed
     * @return reverse array
     */
    public static int[] reverse(int[] array) {
        int[] rev = new int[array.length];
        for (int i = 0, j = array.length - 1; i < array.length; i++, j--) {
            rev[i] = array[j];
        }
        return rev;
    }

    /**
     * Clones char array.
     *
     * @param array array to be cloned
     * @return another array equal to the original one
     */
    public static char[] clone(char[] array) {
        char[] clone = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            clone[i] = array[i];
        }
        return clone;
    }

    /**
     * Returns a string representation of the given array.
     *
     * @param array to be presented
     * @param <T> type of items in array
     * @return String representation
     */
    public static <T> String toString(T[] array) {
        String returnString = "[";
        for (int i = 0; i < array.length; i++) {
            returnString += array[i].toString();
            if (i < array.length - 1) {
                returnString += ", ";
            }
        }
        returnString += "]";
        return returnString;
    }

    /**
     * Calculates hashCode for the array.
     *
     * @param array array of which the hasCode will be calculated
     * @return hashCode
     */
    public static int hashCode(Object array[]) {
        if (array == null) {
            return 0;
        }

        int result = 1;
        for (Object a : array) {
            result = 31 * result + (a == null ? 0 : a.hashCode());
        }
        return result;
    }

    /**
     * Calculates hashCode which also takes arrays in the array into account.
     *
     * @param array array of which the hasCode will be calculated
     * @return hashCode
     */
    public static int deepHashCode(Object array[]) {
        if (array == null) {
            return 0;
        }

        int result = 1;
        for (Object a : array) {
            int elementHash = 0;
            if (a instanceof Object[]) {
                elementHash = hashCode((Object[]) a);
            } else if (a != null) {
                elementHash = a.hashCode();
            }
            result = 31 * result + elementHash;
        }
        return result;
    }
}
