
package fi.pentago.app;

import fi.pentago.logic.marble.Symbol;
import fi.pentago.logic.Tile;
import fi.pentago.logic.marble.Marble;

public class Start {
    
    public static void main(String[] args) {
        Tile t = new Tile();
        t.setMarble(new Marble(Symbol.O), 0, 0);
        t.setMarble(new Marble(Symbol.X), 0, 1);
        t.setMarble(new Marble(Symbol.O), 0, 2);
        t.setMarble(new Marble(Symbol.X), 1, 0);
        t.setMarble(new Marble(Symbol.O), 1, 1);
        t.setMarble(new Marble(Symbol.X), 2, 1);
        t.setMarble(new Marble(Symbol.O), 2, 2);
        System.out.println(t.toString());
        System.out.println("");
        t.rotateClockWise();
        System.out.println(t.toString());
        System.out.println("");
        t.rotateClockWise();
        System.out.println(t.toString());
    }
}
