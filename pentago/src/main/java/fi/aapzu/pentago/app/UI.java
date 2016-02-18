
package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Line;

/**
 *
 * @author Aapeli
 */
public abstract class UI {
    Pentago game = new Pentago();
    
    /**
     * Starts the game.
     **/
    public void start() {
        setup();
        Line line = null;
        while (line == null) {
            doTurn();
            line = game.getLine();
        }
        winGame(line);
    }
    
    /**
     * The setup made before the game.
     */
    protected abstract void setup();
    
    /**
     * A turn.
     */
    protected abstract void doTurn();
    
    /**
     * Win the game with the line.
     * @param line 
     */
    protected abstract void winGame(Line line);
}
