
package fi.pentago.logic.marble;

public class Marble {
    
    Symbol symbol;
    
    public Marble(Symbol symbol) {
        this.symbol = symbol;
    }
    
    public String toString() {
        return "["+ symbol + "]";
    }
}
