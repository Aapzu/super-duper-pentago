
package fi.pentago.app;

import fi.pentago.logic.Board;
import fi.pentago.logic.marble.Marble;
import fi.pentago.logic.marble.Symbol;

public class Start {
    
    public static void main(String[] args) {
        Board b = new Board();
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        b.setMarble(x, 0, 0);
        b.setMarble(o, 1, 1);
        b.setMarble(x, 0, 3);
        b.setMarble(o, 1, 4);
        b.setMarble(x, 4, 1);
        b.setMarble(o, 4, 4);
        System.out.println(b.toString());
        b.rotateClockWise(0,0);
        System.out.println(b.toString());
    }
}
