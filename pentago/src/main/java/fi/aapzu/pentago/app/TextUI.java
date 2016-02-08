
package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;
import java.util.Scanner;

public class TextUI {
    
    Pentago game;
    Scanner scanner;
    
    public TextUI() {
        game = new Pentago();
        scanner = new Scanner(System.in);
    }
    
    public void startPentago() {
        System.out.println("Give name for player X:");
        String name = scanner.nextLine();
        game.setPlayerName(0, name);
        System.out.println("Give name for player O:");
        name = scanner.nextLine();
        game.setPlayerName(1, name);
        while(game.getLine() == null) {
            System.out.println("Player in turn: " + game.whoseTurn().getName());
            System.out.println(game.getBoard().toString());
            boolean success = false;
            while(!success) {
                try {
                    System.out.println("Give coordinates for a new marble:\nX:");
                    int x = Integer.parseInt(scanner.nextLine());
                    System.out.println("Y:");
                    int y = Integer.parseInt(scanner.nextLine());
                    game.setMarble(x, y);
                    success = true;
                } catch (Exception e) {
                    System.out.println("Incorrect coordinates, try again.");
                }
            }
            System.out.println(game.getBoard().toString());
            success = false;
            while(!success) {
                try {
                    System.out.println("Give coordinates for the tile to be rotated:\nX:");
                    int x = Integer.parseInt(scanner.nextLine());
                    System.out.println("Y:");
                    int y = Integer.parseInt(scanner.nextLine());
                    int d = 0;
                    while(d != 1 && d != 2) {
                        System.out.println("Give direction (1 for clockwise, 2 for counterClockwise):");
                        d = Integer.parseInt(scanner.nextLine());
                    }
                    Direction dir = Direction.CLOCKWISE;
                    if(d == 2)
                        dir = Direction.COUNTER_CLOCKWISE;
                    game.rotateTile(x, y, Direction.CLOCKWISE);
                    success = true;
                } catch (Exception e) {
                    System.out.println("Incorrect coordinates, try again.");
                }
            }
        }
    }
}
