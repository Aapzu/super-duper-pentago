
package fi.aapzu.pentago.logic.marble;

public class Marble {
    
    Symbol symbol;
    
    public Marble(Symbol symbol) {
        this.symbol = symbol;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    public String toString() {
        return "["+ symbol + "]";
    }
    
    public boolean equals(Marble m) {
        return this.getSymbol().equals(m.getSymbol());
    }
}
