package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * A player in the game.
 *
 * @author Aapeli
 */
public class Player {

    private Symbol symbol;
    private String name;

    /**
     * Creates a Player with the given Symbol.
     *
     * @param symbol the symbol of the player
     * @param name the name of the player
     */
    public Player(Symbol symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Creates a Player with the given Symbol. Uses null for the name.
     *
     * @param symbol the symbol of the player
     */
    public Player(Symbol symbol) {
        this(symbol, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
