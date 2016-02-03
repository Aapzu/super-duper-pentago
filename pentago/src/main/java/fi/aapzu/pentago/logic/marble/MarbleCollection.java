
package fi.aapzu.pentago.logic.marble;

import java.util.ArrayList;

public class MarbleCollection {
    
    private int maxSize;
    private ArrayList<Marble> marbleList;
    
    public MarbleCollection(Symbol symbol, int maxSize) {
        this.marbleList = new ArrayList<>();
        this.maxSize = maxSize;
        for(int i = 0; i < maxSize; i++) {
            this.marbleList.add(new Marble(symbol));
        }
    }
    
    public MarbleCollection(Symbol symbol) {
        this(symbol, 18);
    }
    
    public boolean addMarble(Marble m) {
        if(this.marbleList.size() < maxSize)
            return this.marbleList.add(m);
        else
            return false;
    }
    
    public boolean removeMarble() {
        return removeMarble(this.getMarble());
    }
    
    public boolean removeMarble(Marble m) {
        return marbleList.remove(m);
    }
    
    public int getMaxSize() {
        return maxSize;
    }
    
    public Marble getMarble() {
        return getMarble(marbleList.size() - 1);
    }
    
    public Marble getMarble(int index) {
        if(!rangeCheck(index))
            return null;
        else
            return marbleList.get(index);
    }
    
    private boolean rangeCheck(int index) {
        return index >= 0 && index <= marbleList.size();
    }
    
    public int size() {
        return marbleList.size();
    }
    
}
