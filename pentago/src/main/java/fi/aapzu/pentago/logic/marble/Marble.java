
package fi.aapzu.pentago.logic.marble;

/**
 * The main instruments in the game. Marbles are added to the board.
 *
 * @author Aapeli
 */
public class Marble {
    
    private final Symbol symbol;
    
    /**
     * Creates a new Marble with the given Symbol.
     * 
     * @param symbol to be played with
     */
    public Marble(Symbol symbol) {
        this.symbol = symbol;
    }

    public Marble(Marble other) {
        this(other.getSymbol());
    }
    
    public Symbol getSymbol() {
        return symbol;
    }
    
    @Override
    public String toString() {
        return "[" + getSymbol() + "]";
    }

    
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Marble)) {
            return false;
        }
        return this.getSymbol().equals(((Marble) o).getSymbol());
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }
}
