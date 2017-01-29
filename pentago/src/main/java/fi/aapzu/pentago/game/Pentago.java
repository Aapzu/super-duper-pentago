package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.*;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * The mother class of the game.
 *
 * @author Aapeli
 */
public class Pentago {

    private Board board;
    private Player[] players;
    private LineChecker lineChecker;

    private int whoseTurn;
    private boolean allowedToRotate;
    private int turnsDone;

    /**
     * Creates a new game with a Board, two Players and the bookkeeping of whose
     * turn it is.
     */
    public Pentago() {
        board = new Board();
        lineChecker = new LineChecker(board);
        players = new Player[2];
        whoseTurn = 0;
        allowedToRotate = false;
    }

    /**
     * Copy-constructor to create a clone of the Pentago
     *
     * @param other the game to be cloned
     */
    public Pentago(Pentago other) {
        setBoard(new Board(other.getBoard()));
        setPlayers(new Player[other.getPlayers().length]);
        for (int i = 0; i < getPlayers().length; i++) {
            getPlayers()[i] = new Player(other.getPlayers()[i]);
        }
        setWhoseTurn(other.getWhoseTurn());
        setAllowedToRotate(other.getAllowedToRotate());
    }

    /**
     * Adds a human Player
     *
     * @param name
     */
    public void addHumanPlayer(String name) {
        addPlayer(new Player(name));
    }

    /**
     * Adds a Bot
     *
     * @param name
     */
    public void addBot(String name) {
        if (name == null) {
            name = "Bot" + System.currentTimeMillis();
        }
        Bot player = new Bot(this, name);
        addPlayer(player);
    }

    private void addPlayer(Player player) {
        int playerNumber = players[0] == null ? 0 : (players[1] == null ? 1 : 2);
        if (playerNumber == 2) {
            throw new PentagoGameRuleException("Game already has two players!");
        }
        player.setPlayerNumber(playerNumber);
        player.setSymbol(player.getSymbolForPlayerNumber(playerNumber));
        players[playerNumber] = player;
    }

    /**
     * Clears everything in the game. Starts new game.
     */
    public void clear() {
        board.clear();
        whoseTurn = 0;
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
        turnsDone++;
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
        if (board.getTile(x, y) == board.getLastRotatedTile() && d == Direction.getOpposite(board.getLastDirection(x, y))) {
            throw new PentagoGameRuleException("The tile cannot be rotated back to the direction it was just rotated from!");
        }

        board.rotateTile(x, y, d);
        allowedToRotate = false;
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
        return players[whoseTurn];
    }

    /**
     * Changes the turn from other player to another.
     */
    public void nextTurn() {
        whoseTurn = (whoseTurn + 1) % 2;
    }

    /**
     * Gets a Symbol and return the corresponding Player. If not found, returns null.
     *
     * @param symbol
     * @return player or null
     */
    public Player getPlayerBySymbol(Symbol symbol) {
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
     * @return move the last move of the game
     */
    public Move getLastMove() {
        Player lastPlayer = getPlayerBySymbol(Symbol.getOpponent(whoseTurn().getSymbol()));
        Marble lastMarble = getBoard().getLastMarble();
        int lastMarbleX = getBoard().getLastMarbleX();
        int lastMarbleY = getBoard().getLastMarbleY();
        int lastTileY = getBoard().getLastRotatedTileY();
        int lastTileX = getBoard().getLastRotatedTileX();
        Direction lastDirection = getBoard().getLastDirection(lastTileX, lastTileY);
        return new Move(lastPlayer, lastMarble, lastMarbleX, lastMarbleY, lastTileX, lastTileY, lastDirection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pentago pentago = (Pentago) o;

        return whoseTurn == pentago.whoseTurn && allowedToRotate == pentago.allowedToRotate && board.equals(pentago.board);
    }

    @Override
    public int hashCode() {
        int result = board.hashCode();
        result = 31 * result + whoseTurn;
        result = 31 * result + (allowedToRotate ? 1 : 0);
        return result;
    }

    public Board getBoard() {
        return board;
    }

//    public LineChecker getLineChecker() {
//        return lineChecker;
//    }

    Player[] getPlayers() {
        return players;
    }

    private void setPlayers(Player[] players) {
        this.players = players;
    }

//    public int getRoundNumber() {
//        return turnsDone / 2;
//    }

//    public void setLineChecker(LineChecker lineChecker) {
//        this.lineChecker = lineChecker;
//    }

    private int getWhoseTurn() {
        return whoseTurn;
    }

    private void setWhoseTurn(int whoseTurn) {
        this.whoseTurn = whoseTurn;
    }

    public boolean getAllowedToRotate() {
        return allowedToRotate;
    }

    private void setAllowedToRotate(boolean allowedToRotate) {
        this.allowedToRotate = allowedToRotate;
    }

//    public int getTurnsDone() {
//        return turnsDone;
//    }

//    public void setTurnsDone(int turnsDone) {
//        this.turnsDone = turnsDone;
//    }
}
