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
    private int playerNumber;
    private Bot bot;

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Bot getBot() {
        return bot;
    }

    /**
     * Creates a Player with the given Symbol.
     *
     * @param name the name of the player
     */
    public Player(String name, Bot bot, int playerNumber) {
        this.symbol = getSymbolForPlayerNumber(playerNumber);
        this.name = name;
        this.playerNumber = playerNumber;
        this.bot = bot;
    }

    public Player(String name, int playerNumber) {
        this(name, null, playerNumber);
    }

    public Player(String name, Bot bot) {
        this(name, bot, -1);
    }

    public Player(String name) {
        this(name, null, -1);
    }

    /**
     * Creates a Player with the given Symbol. Uses null for the name.
     *
     */
    public Player(int playerNumber) {
        this(null, null, playerNumber);
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

    /**
     * If player has bot, require it two make the move
     *
     * @return true if player is bot and has made its move, else false
     */
    public boolean makeMove() {
        if (this.getBot() != null) {
            this.getBot().makeMove();
            return true;
        }
        return false;
    }

    /**
     * Return symbol for number. 0 => X, 1 => O
     * @param playerNumber
     * @return symbol
     */
    public Symbol getSymbolForPlayerNumber(int playerNumber) {
        if (playerNumber > 1 || playerNumber < 0) {
            return null;
        }
        return playerNumber == 0 ? Symbol.X : Symbol.O;
    }
}
