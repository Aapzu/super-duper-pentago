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
class TextUI {

    private final Pentago game;
    private final Scanner scanner;

    /**
     * Creates a new game and the scanner for the commands.
     */
    TextUI() {
        game = new Pentago();
        scanner = new Scanner(System.in);
    }

    /**
     * Starts the game. Every other time prints the board and the Player in
     * turn, and asks for a command.
     */
    void startPentago() {
        System.out.println("Give name for player X:");
        String name = scanner.nextLine();
        game.addHumanPlayer(name);

        System.out.println("Give name for bot:");
        name = scanner.nextLine();
        game.addBot(name);

        Line line = null;
        while (true) {
            if (line != null) {
                winGame(line);
                break;
            } else if (game.isEven()) {
                evenGame();
                break;
            }
            doTurn();
            line = game.checkLines();
        }
    }

    public Pentago getGame() {
        return game;
    }

    private void doTurn() {
        printGame();
        if (!game.whoseTurn().makeMove()) {
            System.out.println("Player in turn: " + game.whoseTurn().getName() + "\n");
            trySetMarble();
            printGame();
            tryRotateTile();
        }
    }

    private void trySetMarble() {
        boolean success = false;
        while (!success) {
            System.out.println("Give coordinates for the new marble! (x y)");
            String cmd = scanner.nextLine();
            try {
                int x = parseCoordinates(cmd)[0];
                int y = parseCoordinates(cmd)[1];
                game.addMarble(x, y);
                success = true;
            } catch (Exception e) {
                if (e instanceof IllegalArgumentException) {
                    System.out.println("Invalid coordinates! Try again:");
                } else if (e instanceof PentagoGameRuleException) {
                    System.out.println("The place is not empty! Try again:");
                }
            }
        }
    }

    private void tryRotateTile() {
        boolean success = false;
        boolean success2 = false;
        boolean success3 = false;
        int x = -1;
        int y = -1;
        int d = -1;
        while (!success) {
            while (!success2) {
                System.out.println("Give coordinates for the tile to be rotated (x y):");
                String cmd = scanner.nextLine();
                try {
                    x = parseCoordinates(cmd)[0];
                    y = parseCoordinates(cmd)[1];
                    success2 = game.getBoard().validateTileCoordinates(x, y);
                } catch (Exception ignored) {
                }
            }
            while (!success3) {
                System.out.println("Give the direction (1 for CLOCKWISE, 2 for COUNTER CLOCKWISE):");

                try {
                    d = Integer.parseInt(scanner.nextLine());
                    if (d == 1 || d == 2) {
                        success3 = true;
                    } else {
                        System.out.println("Invalid direction, try again");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid direction, try again");
                }
            }
            Direction direction = Direction.CLOCKWISE;
            if (d == 2) {
                direction = Direction.COUNTER_CLOCKWISE;
            }
            try {
                game.rotateTile(x, y, direction);
                success = true;
            } catch (PentagoGameRuleException e) {
                success2 = false;
                success3 = false;
                System.out.println("A tile cannot be rotated back to the direction it was just rotated from! Try again:");
            }
        }
    }

    private void winGame(Line line) {
        Player p = line.getPlayer();
        System.out.println("Congratulations " + p.getName() + "! You won the game!");
        System.out.println("The winning line: " + line.toString());
    }

    private void evenGame() {
        System.out.println("Draw! No possible moves anymore.");
    }

    private void printGame() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
        System.out.println(game.getBoard().toString());
    }

    private int[] parseCoordinates(String cmd) {
        if (!cmd.matches("^[0-9] [0-9]$")) {
            throw new IllegalArgumentException("Invalid coordinates!");
        } else {
            return new int[]{Integer.parseInt(cmd.split(" ")[0]), Integer.parseInt(cmd.split(" ")[1])};
        }
    }
}
