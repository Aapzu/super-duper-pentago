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
//        if (args.length > 0 && args[0].contains("text")) {
//            TextUI textUI = new TextUI();
//            textUI.startPentago();
//        } else {
        GUI gui = new GUI();
        gui.startGUI(args);
//        }
//        AlphaBetaPruning abp = new AlphaBetaPruning();
//        Pentago game = new Pentago();
//        game.addHumanPlayer("a");
//        game.addHumanPlayer("b");
//        System.out.println(((Pentago)abp.getBest(game, 2)).getLastMove());
//        System.out.println(abp.times);
    }
}
