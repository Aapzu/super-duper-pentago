package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.MarbleCollection;
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
     * Creates a Player with a MarbleCollection with the given Symbol.
     * Set the name to 'new_player'.
     * 
     * @param symbol
     */
    public Player(Symbol symbol) {
        this.symbol = symbol;
//        this.marbles = new MarbleCollection(symbol);
        this.name = "new_player";
    }

    /**
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
     * Gives the last Marble in the Player's MarbleCollection.
     * 
     * @return Marble
     */
//    public Marble takeOneMarble() {
//        return marbles.getMarble();
//    }
    
    /**
     * @return symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
//        String plural = "";
//        int size = marbles.size();
//        if (size != 1) {
//            plural += "s";
//        }
//        return "Player " + getName() + ": " + size + " marble" + plural + " left.";
        return name;
    }
}
