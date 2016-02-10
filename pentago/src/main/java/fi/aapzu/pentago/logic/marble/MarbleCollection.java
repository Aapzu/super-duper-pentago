
package fi.aapzu.pentago.logic.marble;

import java.util.ArrayList;

/**
 * Contains a collection of Marbles. Also controls the size of the collection.
 * 
 * @author Aapeli
 */
public class MarbleCollection {
    
    private int maxSize;
    private ArrayList<Marble> marbleList;
    
    /**
     * Creates the collection, and adds the given amount of Marbles to it with the given Symbol.
     *
     * @param symbol the Symbol of the marbles in the collection
     * @param maxSize the maximum size of the collection.
     */
    public MarbleCollection(Symbol symbol, int maxSize) {
        this.marbleList = new ArrayList<>();
        this.maxSize = maxSize;
        for (int i = 0; i < maxSize; i++) {
            this.marbleList.add(new Marble(symbol));
        }
    }
    
    /**
     * Another constructor, which uses 18 as the default value for the maxSize.
     *
     * @param symbol the Symbol to be given to the constructor
     */
    public MarbleCollection(Symbol symbol) {
        this(symbol, 18);
    }
    
    /**
     * Adds the given Marble to the collection, if the size of the collection is under maxSize.
     * 
     * @param m the Marble to be added
     * @return true if the addition succeeded, otherwise false
     */
    public boolean addMarble(Marble m) {
        if (this.marbleList.size() < maxSize) {
            return this.marbleList.add(m);
        } else {
            return false;
        }
    }
    
    /**
     * Removes the last Marble from the collection.
     * 
     * @return the removed Marble
     */
    public boolean removeMarble() {
        return removeMarble(this.getMarble());
    }
    
    /**
     * Removes the given Marble from the collection.
     * 
     * @param m the Marble wanted to remove.
     * @return the removed Marble.
     */
    public boolean removeMarble(Marble m) {
        return marbleList.remove(m);
    }
    
    /**
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }
    
    /**
     * @return the last Marble in the collection.
     */
    public Marble getMarble() {
        return getMarble(marbleList.size() - 1);
    }
    
    /**
     * Gives the Marble in the specified index. 
     * If the index is invalid (under 0 or over the size of the collection), return null.
     * 
     * @param index the index of the Marble in the list.
     * @return the Marble or null
     */
    public Marble getMarble(int index) {
        if (!rangeCheck(index)) {
            return null;
        } else {
            return marbleList.get(index);
        }
    }
    
    private boolean rangeCheck(int index) {
        return index >= 0 && index <= marbleList.size();
    }
    
    /**
     * @return the amount of the remaining Marbles.
     */
    public int getSize() {
        return marbleList.size();
    }
    
}
