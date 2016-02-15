
package fi.aapzu.pentago.app;

/**
 * The class that starts Pentago.
 * 
 * @author Aapeli
 */
public class Start {
    //        TextUI ui = new TextUI();
//        ui.startPentago();
    /**
     * @param args
     */
    public static void main(String[] args) {

        GUI gui = new GUI();
        gui.startGUI(args);
    }
}
