package fi.aapzu.pentago.util;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

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
    public DynamicArray() {
    }

    /**
     * Creates new DynamicArray and adds the content of the array to it.
     *
     * @param array array of which items will be used
     */
    public DynamicArray(T[] array) {
        itemArray = array;
        arrayLength = array.length;
        arrayIndex = array.length;
        doubleArraySize();
    }

    /**
     * Adds a item into the list and expands its size if necessary.
     *
     * @param item item to be added
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
     * Gets the item of which index is given.
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
                    (item.equals(o))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gives the index of given item.
     *
     * @param item item of which the index will be given
     * @return -1 if item is not in the list, else index
     */
    public int indexOf(T item) {
        for (int i = 0; i < size(); i++) {
            if (this.get(i) == item || this.get(i).equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DynamicArray)) {
            return false;
        }
        DynamicArray<Object> that = (DynamicArray<Object>) obj;
        if (size() != that.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            Object a = get(i);
            Object b = that.get(i);
            if (a == b) {
                continue;
            }
            if (a instanceof Object[] && b instanceof Object[] && !ArrayUtils.deepEquals((Object[]) a, (Object[]) b)) {
                return false;
            }
            if (a != null && !(a instanceof Object[]) && !a.equals(b)) {
                return false;
            }
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

    /**
     * Adds all the items in Set to DynamicArray.
     *
     * @param setToBeAdded set of which items are added to DynamicList
     */
    public void addAll(Set<T> setToBeAdded) {
        for (T t : setToBeAdded) {
            add(t);
        }
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
        return new Iterator<T>() {
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
