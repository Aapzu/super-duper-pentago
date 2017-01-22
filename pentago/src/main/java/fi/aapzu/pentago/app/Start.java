package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;

/**
 * The class that starts Pentago.
 *
 * @author Aapeli
 */
public class Start {

    /**
     * Checks for argument "text" and starts whether TextUI or GUI.
     * 
     * @param args the arguments for starting
     */
    public static void main(String[] args) {
        Pentago game = new Pentago();
        if (args.length > 0 && args[0].equals("text")) {
            TextUI textUI = new TextUI(game);
            textUI.startPentago();
        } else {
            GUI gui = new GUI(game);
            gui.startGUI(args);
        }
    }
}
