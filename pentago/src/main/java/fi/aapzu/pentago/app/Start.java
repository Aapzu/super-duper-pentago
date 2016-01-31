
package fi.aapzu.pentago.app;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.marble.Marble;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.ArrayList;
import java.util.Arrays;

public class Start {
    
    public static void main(String[] args) {
        Board b = new Board();
        Marble x = new Marble(Symbol.X);
        Marble o = new Marble(Symbol.O);
        b.addMarble(x, 0, 0);
        b.addMarble(x, 1, 0);
        b.addMarble(x, 2, 0);
        b.addMarble(x, 3, 0);
        System.out.println(b.checkForRows(5));
        b.addMarble(x, 4, 0);
        System.out.println(b.checkForRows(5).get("symbol"));
        ArrayList<Integer[]> c = (ArrayList<Integer[]>)b.checkForRows(5).get("coordinates");
        for(Integer[] i : c) {
            System.out.print(Arrays.toString(i));
        }
    }
}
