
package fi.aapzu.pentago.logic.marble;

public class Marble {
    
    private final Symbol symbol;
    
    public Marble(Symbol symbol) {
        this.symbol = symbol;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    @Override
    public String toString() {
        return "["+ getSymbol() + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof Marble)) {
            return false;
        }
        return this.getSymbol().equals(((Marble)o).getSymbol());
    }

}
