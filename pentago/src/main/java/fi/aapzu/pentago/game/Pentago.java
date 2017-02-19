package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.*;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.ArrayUtils;
import fi.aapzu.pentago.util.Serializable;

import java.util.Arrays;

/**
 * The mother class of the game.
 *
 * @author Aapeli
 */
public class Pentago implements Serializable {

    private Board board;
    private Player[] players;

    private LineChecker lineChecker;

    private int whoseTurnIndex;
    private boolean allowedToRotate;

    /**
     * Creates a new game with a Board, two Players and the bookkeeping of whose
     * turn it is.
     */
    public Pentago() {
        board = new Board();
        lineChecker = new LineChecker(board);
        players = new Player[2];
        whoseTurnIndex = 0;
        allowedToRotate = false;
    }

    /**
     * Copy-constructor to create a clone of the Pentago.
     *
     * @param other the game to be cloned
     */
    public Pentago(Pentago other) {
        setBoard(new Board(other.getBoard()));
        setLineChecker(new LineChecker(getBoard()));
        setPlayers(new Player[other.getPlayers().length]);
        for (int i = 0; i < getPlayers().length; i++) {
            getPlayers()[i] = new Player(other.getPlayers()[i]);
        }
        setWhoseTurnIndex(other.getWhoseTurnIndex());
        setAllowedToRotate(other.getAllowedToRotate());
    }

    /**
     * Adds a human Player.
     *
     * @param name name of the player
     */
    public void addHumanPlayer(String name) {
        addPlayer(new Player(name));
    }

    /**
     * Adds a Bot.
     *
     * @param name name of the Bot
     */
    public void addBot(String name) {
        if (name == null) {
            name = "Bot" + System.currentTimeMillis();
        }
        Bot player = new Bot(this, name);
        addPlayer(player);
    }

    /**
     * Removes the Players.
     */
    public void clearPlayers() {
        setPlayers(new Player[2]);
    }

    private void addPlayer(Player player) {
        int playerNumber = players[0] == null ? 0 : (players[1] == null ? 1 : 2);
        if (playerNumber == 2) {
            throw new PentagoGameRuleException("Game already has two players!");
        }
        player.setPlayerNumber(playerNumber);
        player.setSymbol(Player.getSymbolForPlayerNumber(playerNumber));
        players[playerNumber] = player;
    }

    /**
     * Clears everything in the game. Starts new game.
     */
    public void clear() {
        board.clear();
        whoseTurnIndex = 0;
        allowedToRotate = false;
    }

    /**
     * Sets the given name to the given Player.
     *
     * @param i    the index of the Player
     * @param name the name to be Given to the Player
     */
    void setPlayerName(int i, String name) {
        if (i < 0 || i > 1) {
            throw new IllegalArgumentException("Invalid player index: " + i);
        }
        players[i].setName(name);
    }

    /**
     * Sets a Marble to the Board. Takes it from the MarbleCollection of the
     * Player in turn. Throws PentagoGameRuleException if a Tile hasn't been
     * rotated first.
     *
     * @param x the X coordinate of the Marble
     * @param y the Y coordinate of the Marble
     */
    public void addMarble(int x, int y) {
        if (allowedToRotate) {
            throw new PentagoGameRuleException("A tile must be rotated first!");
        }
        Player player = whoseTurn();
        Marble m = new Marble(player.getSymbol());
        boolean success = board.addMarble(m, x, y);
        if (!success) {
            throw new IllegalArgumentException("the coordinates outside the board!");
        }
        allowedToRotate = true;
    }

    /**
     * Rotates the given Tile and gives the turn to the other Player. Throws
     * PentagoGameRuleException if a Marble hasn't been set to the Board first.
     *
     * @param x the X coordinate of the Tile
     * @param y the X coordinate of the Tile
     * @param d the Direction
     */
    public void rotateTile(int x, int y, Direction d) {
        if (!allowedToRotate) {
            throw new PentagoGameRuleException("A marble must be added first!");
        }
        if (board.getLastRotatedTileX() == x && board.getLastRotatedTileY() == y && d == Direction.getOpposite(board.getLastRotatedTileDirection())) {
            throw new PentagoGameRuleException("The tile cannot be rotated back to the direction it was just rotated from!");
        }

        board.rotateTile(x, y, d);
        allowedToRotate = false;
        nextTurn();
    }

    /**
     * Tells if the game has no any possible moves anymore.
     *
     * @return true or false
     */
    public boolean isEven() {
        return board.isFull();
    }

    /**
     * Tells whose turn it is.
     *
     * @return the Player whose turn it is currently
     */
    public Player whoseTurn() {
        if (players[0] == null || players[1] == null) {
            throw new PentagoGameRuleException("Not enough players!");
        }
        return players[whoseTurnIndex];
    }

    /**
     * Tells whose turn it is in number.
     *
     * @return int whose turn it is currently
     */
    public int getWhoseTurnIndex() {
        return whoseTurnIndex;
    }

    /**
     * Changes the turn from other player to another.
     */
    void nextTurn() {
        whoseTurnIndex = (whoseTurnIndex + 1) % 2;
    }

    /**
     * Gets a Symbol and return the corresponding Player. If not found, returns null.
     *
     * @param symbol symbol to get the Player of
     * @return player or null
     */
    Player getPlayerBySymbol(Symbol symbol) {
        for (Player p : players) {
            if (p.getSymbol() == symbol) {
                return p;
            }
        }
        return null;
    }

    /**
     * Checks if the Board contains any lines (length 5). Returns the Player,
     * and the coordinates of the line, if one is found, otherwise returns null.
     *
     * @return line or null
     */
    public Line checkLines() {
        Line line = lineChecker.checkLines(5);
        if (line != null && line.getSymbol() != null) {
            line.setPlayer(getPlayerBySymbol(line.getSymbol()));
        }
        return line;
    }

    /**
     * Sets the Board. Also replaces the new LineChecker with a new one.
     *
     * @param board the new Board
     */
    public void setBoard(Board board) {
        this.board = board;
        this.lineChecker = new LineChecker(board);
    }

    /**
     * @return move the last move of the game.
     */
    public Move getLastMove() {
        Marble lastMarble = getBoard().getLastMarble();
        if (lastMarble != null) {
            Tile lastTile = getBoard().getLastRotatedTile();
            if (lastTile != null) {
                int lastMarbleX = getBoard().getLastMarbleX();
                int lastMarbleY = getBoard().getLastMarbleY();
                int lastTileY = getBoard().getLastRotatedTileY();
                int lastTileX = getBoard().getLastRotatedTileX();
                Direction lastDirection = getBoard().getLastRotatedTileDirection();
                return new Move(Math.abs(getWhoseTurnIndex() - 1), lastMarble, lastMarbleX, lastMarbleY, lastTileX, lastTileY, lastDirection);
            }
        }
        return null;
    }

    public Board getBoard() {
        return board;
    }

    Player[] getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = players;
    }

    private void setWhoseTurnIndex(int whoseTurn) {
        this.whoseTurnIndex = whoseTurn;
    }

    public boolean getAllowedToRotate() {
        return allowedToRotate;
    }

    private void setAllowedToRotate(boolean allowedToRotate) {
        this.allowedToRotate = allowedToRotate;
    }

    @Override
    public String serialize() {
        String s = getBoard().serialize();
        s += getWhoseTurnIndex();
        return s;
    }

    @Override
    public boolean deserialize(String s) {
        boolean success = false;
        if (s.length() == 42) {
            setWhoseTurnIndex(Integer.parseInt(Character.toString(s.charAt(41))));
            if (getBoard().deserialize(s.substring(0, 41))) {
                success = true;
            }
            setLineChecker(new LineChecker(getBoard()));
        }
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pentago pentago = (Pentago) o;

        return whoseTurnIndex == pentago.getWhoseTurnIndex() &&
                allowedToRotate == pentago.getAllowedToRotate() &&
                board.equals(pentago.getBoard()) &&
                lineChecker.equals(pentago.getLineChecker());
    }

    public LineChecker getLineChecker() {
        return lineChecker;
    }

    public void setLineChecker(LineChecker lineChecker) {
        this.lineChecker = lineChecker;
    }
}
