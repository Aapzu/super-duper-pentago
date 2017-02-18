package fi.aapzu.pentago.util;

import java.util.Iterator;

/**
 * An Array that expands when needed.
 */
public class DynamicArray<T> implements Iterable<T> {

    private int arrayLength = 20;
    private Object[] itemArray = new Object[arrayLength];
    private int arrayIndex = 0;

    /**
     * Creates and initializes a new DynamicArray.
     */
    public DynamicArray() {}

    /**
     * Creates new DynamicArray and adds the content of the array to it.
     *
     * @param array
     */
    public DynamicArray(T[] array) {
        itemArray = array;
        arrayLength = array.length;
    }

    /**
     * Adds a item into the list and expands its size if necessary.
     *
     * @param item
     */
    public void add(T item) {
        itemArray[arrayIndex] = item;
        arrayIndex++;
        if (arrayIndex == arrayLength) {
            doubleArraySize();
        }
    }

    /**
     * Tells how many items are currently in the list.
     *
     * @return size of the list
     */
    public int size() {
        return arrayIndex;
    }

    /**
     * Tells if the DynamicArray has any items in it.
     *
     * @return true if is empty, else false
     */
    public boolean isEmpty() {
        return arrayIndex == 0;
    }

    /**
     *
     * @param index index of the Object
     * @return i:th Object in the list
     */
    public T get(int index) {
        return (T) itemArray[index];
    }

    /**
     * Tells if the DynamicArray has the requested item in it.
     *
     * @param item requested item
     * @return true if has, else false
     */
    public boolean contains(T item) {
        for (Object o : itemArray) {
            if (o == item || (o instanceof Object[] && ArrayUtils.deepEquals((Object[]) o, (Object[]) item)) ||
                    (item.equals(o)) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DynamicArray)) {
            return false;
        }
        DynamicArray that = (DynamicArray) obj;
        int i = 0;
        for (Object o : that) {
            if (!o.equals(this.get(i))) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Removes all the items.
     */
    public void clear() {
        arrayIndex = 0;
        arrayLength = 20;
        itemArray = new Object[arrayLength];
    }

    private void doubleArraySize() {
        arrayLength *= 2;
        Object[] newItemArray = new Object[arrayLength];
        for (int i = 0; i < arrayIndex; i++) {
            newItemArray[i] = itemArray[i];
        }
        itemArray = newItemArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T> () {
            private int iteratorIndex = 0;

            @Override
            public boolean hasNext() {
                return iteratorIndex < arrayIndex;
            }

            @Override
            public T next() {
                return (T) itemArray[iteratorIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("No changes allowed");
            }
        };
    }
}
