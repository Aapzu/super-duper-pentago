package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.MarbleCollection;
import fi.aapzu.pentago.logic.marble.Symbol;

public class Player {

    private Symbol symbol;
    private MarbleCollection marbles;
    private String name;

    public Player(Symbol symbol) {
        this.symbol = symbol;
        this.marbles = new MarbleCollection(symbol);
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Marble takeOneMarble() {
        return marbles.getMarble();
    }
    
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        String plural = "";
        int size = marbles.size();
        if (size != 1) {
            plural += "s";
        }
        return "Player " + getName() + ": " + size + " marble" + plural + " left.";
    }
}
