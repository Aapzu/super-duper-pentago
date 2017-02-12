package fi.aapzu.pentago.logic.marble;

import fi.aapzu.pentago.util.Serializable;

/**
 * The main instruments in the game. Marbles are added to the board.
 *
 * @author Aapeli
 */
public class Marble implements Serializable {

    private Symbol symbol;

    /**
     * Creates a new Marble with the given Symbol.
     *
     * @param symbol to be played with
     */
    public Marble(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Copy-constructor for cloning the Marble.
     *
     * @param other Marble to be cloned
     */
    public Marble(Marble other) {
        this(other.getSymbol());
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "[" + getSymbol() + "]";
    }


    @Override
    public boolean equals(Object o) {
        return !(o == null || !(o instanceof Marble)) && this.getSymbol().equals(((Marble) o).getSymbol());
    }

    @Override
    public int hashCode() {
        return symbol != null ? symbol.hashCode() : 0;
    }

    @Override
    public String serialize() {
        return getSymbol() != null ? (getSymbol() == Symbol.X ? "1" : "2") : "0";
    }

    @Override
    public boolean deserialize(String s) {
        if (s.length() == 1) {
            setSymbol(!s.equals("0") ? (s.equals("1") ? Symbol.X : Symbol.O) : null);
            return true;
        }
        return false;
    }
}
