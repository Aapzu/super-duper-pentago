
package fi.pentago.logic.marble;

import java.util.ArrayList;

public class MarbleCollection extends ArrayList<Marble> {
    
    private int maxSize;
    
    public MarbleCollection(Symbol symbol, int maxSize) {
        this.maxSize = maxSize;
        for(int i = 0; i < maxSize; i++) {
            this.add(new Marble(symbol));
        }
    }
    
    public MarbleCollection(Symbol symbol) {
        this(symbol, 18);
    }
    
    
    @Override
    public boolean add(Marble m) {
        if(this.size() < maxSize)
            return super.add(m);
        else
            return false;
    }
    
    public boolean remove() {
        return super.remove(this.get(this.size() - 1));
    }
    
    public Marble getMarble() {
        return this.get(this.size() - 1);
    }
    
}
