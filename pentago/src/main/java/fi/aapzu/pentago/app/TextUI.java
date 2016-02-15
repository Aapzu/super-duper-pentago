package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.game.PentagoGameRuleException;
import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;
import java.util.Scanner;

/**
 * Text form user interface for Pentago.
 *
 * @author Aapeli
 */
public class TextUI {

    Pentago game;
    Scanner scanner;

    /**
     * Creates a new game and the scanner for the commands.
     */
    public TextUI() {
        game = new Pentago();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the game. Every other time prints the board and the Player in
     * turn, and asks for a command.
     */
    public void startPentago() {
        System.out.println("Give name for player X:");
        String name = scanner.nextLine();
        game.setPlayerName(0, name);

        System.out.println("Give name for player O:");
        name = scanner.nextLine();
        game.setPlayerName(1, name);

        Line line = null;
        while (line == null) {
            doTurn();
            line = game.getLine();
        }
        winGame(line);
    }

    public void doTurn() {
        printGame();
        System.out.println("Player in turn: " + game.whoseTurn().getName() + "\n");
        trySetMarble();
        printGame();
        tryRotateTile();
    }

    public void trySetMarble() {
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Give coordinates for a new marble(x y):");
                String cmd = scanner.nextLine();
                int x;
                int y;
                if (cmd.contains("(")) {
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
    }

    public void tryRotateTile() {
        int x = 0;
        int y = 0;
        boolean success = false;
        boolean success2 = false;
        while(!success2) {
            while (!success) {
                System.out.println("Give coordinates for the tile to be rotated (x y):");
                String cmd = scanner.nextLine();
                if (cmd.contains("(")) {
                    x = Integer.parseInt(cmd.split("\\(| |\\)")[1]);
                    y = Integer.parseInt(cmd.split("\\(| |\\)")[2]);
                } else {
                    x = Integer.parseInt(cmd.split(" ")[0]);
                    y = Integer.parseInt(cmd.split(" ")[1]);
                }
                if (game.getBoard().validateTileCoordinates(x, y)) {
                    success = true;
                } else {
                    System.out.println("Incorrect tile coordinates! Try again:");
                }
            }
            int d = 0;
            while (d != 1 && d != 2) {
                System.out.println("Give direction (1 for clockwise, 2 for counterClockwise):");
                try {
                    d = Integer.parseInt(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Incorrect direction, try again");
                }
            }
            Direction dir = Direction.CLOCKWISE;
            if (d == 2) {
                dir = Direction.COUNTER_CLOCKWISE;
            }
            try {
                game.rotateTile(x, y, dir);
            } catch (PentagoGameRuleException e) {
                System.out.println("The tile cannot be rotated back to the direction it was just rotated from!");
            }
        }
    }

    public void winGame(Line line) {
        Player p = line.getPlayer();
        System.out.println("Congratulations " + p.getName() + "! You won the game!");
        System.out.println("The winning line: " + line.toString());
    }

    private void printGame() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
        System.out.println(game.getBoard().toString());
    }
}
