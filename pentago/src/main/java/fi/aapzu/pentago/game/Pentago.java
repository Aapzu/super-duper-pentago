
package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;

public class Pentago {
    
    private Board board;
    private Player[] players;    
    private int whoseTurn;
    private boolean allowedToRotate;    
    
    public Pentago() {
        board = new Board();
        players = new Player[2];
        players[0] = new Player(Symbol.X);
        players[1] = new Player(Symbol.O);
        whoseTurn = 0;
        allowedToRotate = false;
    }
    
    public void setPlayerName(int i, String name) {
        // Check range?
        players[i].setName(name);
    }
    
    public boolean setMarble(int x, int y) {
        if(allowedToRotate)
            throw new RuntimeException("A tile must be rotated first!");
        Player player = playerInTurn();
        Marble m = player.takeOneMarble();
        boolean success = board.addMarble(m, x, y);
        if(success)
           allowedToRotate = true;
        return success;
    }
    
    public void rotateTile(int x, int y, int direction) {
        if(!allowedToRotate)
            throw new RuntimeException("A marble must be added first!");
        if(direction == 0)
            board.rotateClockWise(x, y);
        else if(direction == 1)
            board.rotateCounterClockWise(x, y);
        else
            throw new RuntimeException("The direction incorrect!");
        
        allowedToRotate = false;
    }
   
    private Player playerInTurn() {
        return players[whoseTurn];
    }
}
