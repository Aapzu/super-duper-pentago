package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.BoardLineChecker;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

/**
 * The mother class of the game.
 *
 * @author Aapeli
 */
public class Pentago {

    private final Board board;
    private final Player[] players;
    private final BoardLineChecker lineChecker;
    private int whoseTurn;
    private boolean allowedToRotate;

    /**
     * Creates a new game with a Board, two Players and the bookkeeping of whose
     * turn it is.
     */
    public Pentago() {
        board = new Board();
        lineChecker = new BoardLineChecker(board);
        players = new Player[2];
        players[0] = new Player(Symbol.O);
        players[1] = new Player(Symbol.X);
        whoseTurn = 0;
        allowedToRotate = false;
    }

    /**
     * Clears everything in the game. Starts new game.
     */
    public void clear() {
        board.clear();
        players[0] = new Player(Symbol.O);
        players[1] = new Player(Symbol.X);
        whoseTurn = 0;
        allowedToRotate = false;
    }

    /**
     * Sets the given name to the given Player.
     *
     * @param i the index of the Player
     * @param name the name to be Given to the Player
     */
    public void setPlayerName(int i, String name) {
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
    public void setMarble(int x, int y) {
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
        return players[whoseTurn];
    }

    /**
     * Changes the turn from other player to another.
     */
    public void nextTurn() {
        whoseTurn = (whoseTurn + 1) % 2;
    }

    protected Player getPlayerBySymbol(Symbol symbol) {
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
            line.setPlayer(getPlayerBySymbol((Symbol) line.getSymbol()));
        }
        return line;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getAllowedToRotate() {
        return allowedToRotate;
    }

    public BoardLineChecker getLineChecker() {
        return lineChecker;
    }

    public Player[] getPlayers() {
        return players;
    }
    
}
