package fi.aapzu.pentago.app;

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
        GUI gui = new GUI();
        gui.startGUI(args);
    }
}
