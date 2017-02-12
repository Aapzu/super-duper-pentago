package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * A player in the game.
 */
public class Player {

    private Symbol symbol;
    private String name;
    private Integer playerNumber;

    /**
     * Creates a Player with the given name and playerNumber.
     *
     * @param name         the name of the player
     * @param playerNumber index of the Player
     */
    public Player(String name, Integer playerNumber) {
        this.symbol = getSymbolForPlayerNumber(playerNumber);
        this.name = name;
        this.playerNumber = playerNumber;
    }

    /**
     * Creates new Player with the given name.
     *
     * @param name name of the Player
     */
    public Player(String name) {
        this(name, null);
    }

    /**
     * Creates new Player with null name and playerNumber.
     */
    public Player() {
        this(null, null);
    }

    /**
     * Copy-constructor to clone the Player.
     *
     * @param other Player to be cloned
     */
    public Player(Player other) {
        setSymbol(other.getSymbol());
        setName(other.getName());
        setPlayerNumber(other.getPlayerNumber());
    }

    /**
     * Creates a Player with the given Symbol. Uses null for the name.
     *
     * @param playerNumber index of the player
     */
    public Player(int playerNumber) {
        this(null, playerNumber);
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * If this is called, it means the Player is not bot so it returns false.
     *
     * @return false
     */
    public boolean makeMove() {
        return false;
    }

    /**
     * Return symbol for number. 0 equals 'X' while 1 equals 'O'
     *
     * @param playerNumber index to get the symbol from
     * @return symbol
     */
    public static Symbol getSymbolForPlayerNumber(Integer playerNumber) {
        if (playerNumber == null || playerNumber > 1 || playerNumber < 0) {
            return null;
        }
        return playerNumber == 0 ? Symbol.X : Symbol.O;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Player player = (Player) o;

        return symbol == player.symbol &&
                (name == null || player.name == null || name.equals(player.name)) &&
                (playerNumber == null || player.playerNumber == null || playerNumber.equals(player.playerNumber));
    }
}
