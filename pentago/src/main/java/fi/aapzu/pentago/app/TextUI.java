
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
            printGame();
            for(int i = 0; i < 50; i++) System.out.println();
            System.out.println("Player in turn: " + game.whoseTurn().getName() + "\n");
            System.out.println(game.getBoard().toString());
            boolean success = false;
            while(!success) {
                try {
                    System.out.println("Give coordinates for a new marble(x y):");
                    String cmd = scanner.nextLine();
                    int x;
                    int y;
                    if(cmd.contains("(")) {
                        x = Integer.parseInt(cmd.split("\\(| |\\)")[1]);
                        y = Integer.parseInt(cmd.split("\\(| |\\)")[2]);
                    } else {
                        x = Integer.parseInt(cmd.split(" ")[0]);
                        y = Integer.parseInt(cmd.split(" ")[1]);
                    }
                    game.setMarble(x, y);
                    success = true;
                } catch (Exception e) {
                    System.out.println("Incorrect coordinates, try again.");
                }
            }
            printGame();
            success = false;
            while(!success) {
                try {
                    System.out.println("Give coordinates for the tile to be rotated (x y):");
                    String cmd = scanner.nextLine();
                    int x;
                    int y;
                    if(cmd.contains("(")) {
                        x = Integer.parseInt(cmd.split("\\(| |\\)")[1]);
                        y = Integer.parseInt(cmd.split("\\(| |\\)")[2]);
                    } else {
                        x = Integer.parseInt(cmd.split(" ")[0]);
                        y = Integer.parseInt(cmd.split(" ")[1]);
                    }
                    int d = 0;
                    while(d != 1 && d != 2) {
                        System.out.println("Give direction (1 for clockwise, 2 for counterClockwise):");
                        try {
                            d = Integer.parseInt(scanner.nextLine());
                        } catch (Exception e) {
                            System.out.println("Incorrect direction, try again");
                        }
                    }
                    Direction dir = Direction.CLOCKWISE;
                    if(d == 2)
                        dir = Direction.COUNTER_CLOCKWISE;
                    game.rotateTile(x, y, Direction.CLOCKWISE);
                    success = true;
                } catch (Exception e) {
                    System.out.println("Incorrect coordinates, try again");
                }
            }
        }
    }
    
    private void printGame() {
        for(int i = 0; i < 20; i++) System.out.println();
        System.out.println(game.getBoard().toString());
    }
}
