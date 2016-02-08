
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.Arrays;
import java.util.Map;

public class Pentago {
    
    private final Board BOARD;
    private final Player[] PLAYERS;    
    private int whoseTurn;
    private boolean allowedToRotate;    
    
    public Pentago() {
        BOARD = new Board();
        PLAYERS = new Player[2];
        PLAYERS[0] = new Player(Symbol.X);
        PLAYERS[1] = new Player(Symbol.O);
        whoseTurn = 0;
        allowedToRotate = false;
    }
    
    public void setPlayerName(int i, String name) {
        if(i < 0 || i > 1)
            throw new IllegalArgumentException("Invalid player index: "+i);
        PLAYERS[i].setName(name);
    }
    
    public boolean setMarble(int x, int y) {
        if(allowedToRotate)
            throw new RuntimeException("A tile must be rotated first!");
        Player player = whoseTurn();
        Marble m = player.takeOneMarble();
        boolean success = BOARD.addMarble(m, x, y);
        if(success)
           allowedToRotate = true;
        return success;
    }
    
    public void rotateTile(int x, int y, Direction direction) {
        if(!allowedToRotate)
            throw new PentagoGameRuleException("A marble must be added first!");
        if(!Arrays.asList(Direction.getRotateDirections()).contains(direction))
            throw new IllegalArgumentException("Invalid direction!");
        if(direction == BOARD.getLastDirection(x, y))
            throw new PentagoGameRuleException("The tile cannot be rotated back to the direction it was just rotated from!");
        
        BOARD.rotateTile(x, y, direction);
        whoseTurn = (whoseTurn + 1) % 2;
        allowedToRotate = false;
    }
   
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
    
    public Map<String, Object> getLine() {
        Map<String, Object> line = BOARD.checkLines(5);
        if(line != null && line.get("symbol") != null) {
            line.put("player", getPlayerBySymbol((Symbol)line.get("symbol")));
        }
        return line;
    }
    
    public Board getBoard() {
        return BOARD;
    }
}
