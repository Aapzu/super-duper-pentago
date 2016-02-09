
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.Arrays;
import java.util.Map;

/**
 * The mother class of the game.
 * 
 * @author Aapeli
 */
public class Pentago {
    
    private final Board BOARD;
    private final Player[] PLAYERS;    
    private int whoseTurn;
    private boolean allowedToRotate;    
    
    /**
     * Creates a new game with a Board, two Players and the bookkeeping of whose turn it is.
     */
    public Pentago() {
        BOARD = new Board();
        PLAYERS = new Player[2];
        PLAYERS[0] = new Player(Symbol.X);
        PLAYERS[1] = new Player(Symbol.O);
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
        if(i < 0 || i > 1)
            throw new IllegalArgumentException("Invalid player index: "+i);
        PLAYERS[i].setName(name);
    }
    
    /**
     * Sets a Marble to the Board. Takes it from the MarbleCollection of the Player in turn.
     * Throws PentagoGameRuleException if a Tile hasn't been rotated first.
     * 
     * @param x the X coordinate of the Marble
     * @param y the Y coordinate of the Marble
     * @return true if succeeded, otherwise false
     */
    public boolean setMarble(int x, int y) {
        if(allowedToRotate)
            throw new PentagoGameRuleException("A tile must be rotated first!");
        Player player = whoseTurn();
        Marble m = player.takeOneMarble();
        boolean success = BOARD.addMarble(m, x, y);
        if(success)
           allowedToRotate = true;
        return success;
    }
    
    /**
     * Rotates the given Tile and gives the turn to the other Player.
     * Throws PentagoGameRuleException if a Marble hasn't been set to the Board first.
     * 
     * @param x the X coordinate of the Tile
     * @param y the X coordinate of the Tile
     * @param d the Direction
     */
    public void rotateTile(int x, int y, Direction d) {
        if(!allowedToRotate)
            throw new PentagoGameRuleException("A marble must be added first!");
        if(!Arrays.asList(Direction.getRotateDirections()).contains(d))
            throw new IllegalArgumentException("Invalid direction!");
        if(d == BOARD.getLastDirection(x, y))
            throw new PentagoGameRuleException("The tile cannot be rotated back to the direction it was just rotated from!");
        
        BOARD.rotateTile(x, y, d);
        whoseTurn = (whoseTurn + 1) % 2;
        allowedToRotate = false;
    }
   
    /**
     * @return the Player whose turn it is currently
     */
    public Player whoseTurn() {
        return PLAYERS[whoseTurn];
    }
    
    private Player getPlayerBySymbol(Symbol symbol) {
        for(Player p : PLAYERS) {
            if(p.getSymbol() == symbol)
                return p;
        }
        return null;
    }
    
    /**
     * Checks if the Board contains any lines (length 5).
     * Returns the Player, and the coordinates of the line, if one is found, otherwise returns null.
     * 
     * @return line or null
     */
    public Map<String, Object> getLine() {
        Map<String, Object> line = BOARD.checkLines(5);
        if(line != null && line.get("symbol") != null) {
            line.put("player", getPlayerBySymbol((Symbol)line.get("symbol")));
        }
        return line;
    }
    
    /**
     * @return board
     */
    public Board getBoard() {
        return BOARD;
    }
}
