package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * A player in the game.
 *
 * @author Aapeli
 */
public class Player {

    private Symbol symbol;
//    private MarbleCollection marbles;
    private String name;

    /**
     * Creates a Player with a MarbleCollection with the given Symbol. Set the
     * name to 'new_player'.
     *
     * @param symbol
     */
    public Player(Symbol symbol) {
        this.symbol = symbol;
//        this.marbles = new MarbleCollection(symbol);
        this.name = "new_player";
    }

    /**
     * Gives the name of the player.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the given name for the Player.
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gives the symbol of the player.
     *
     * @return symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Sets symbol.
     *
     * @param symbol
     */
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
