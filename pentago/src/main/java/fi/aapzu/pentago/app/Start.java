
package fi.aapzu.pentago.app;

/**
 * The class that starts Pentago.
 * 
 * @author Aapeli
 */
public class Start {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if(args.length > 0 && args[0].equals("text")) {
            TextUI textUI = new TextUI();
            textUI.startPentago();
        } else {
            GUI gui = new GUI();
            gui.startGUI(args);
        }
    }
}
