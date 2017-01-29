package fi.aapzu.pentago.game;

import fi.aapzu.pentago.ai.AlphaBetaPruning;
import fi.aapzu.pentago.game.Move;
import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Direction;

/**
 * A bot which makes decisions on behalf of a Player
 */
class Bot extends Player {

    private final Pentago game;
    private final AlphaBetaPruning alphaBetaPruning;

    /**
     * @param game Pentago game the bot is playing
     */
    Bot(Pentago game, String name) {
        super(name);
        this.game = game;
        this.alphaBetaPruning = new AlphaBetaPruning(game);
    }

    /**
     * Calculates the best move and acts by it.
     *
     * @return true
     */
    @Override
    public boolean makeMove() {
        Move move = alphaBetaPruning.getBest(1);
        actByMove(move);
        return true;
    }

    // TODO: remove this
    /**
     * Makes a random move
     */
    void makeRandomMove() {
        boolean success = false;
        int y;
        int x;
        while (!success) {
            try {
                y = (int)(Math.random() * 6);
                x = (int)(Math.random() * 6);
                this.game.addMarble(x, y);
                success = true;
            } catch (Exception ignored) {}
        }
        success = false;
        Direction[] directions = Direction.getRotateDirections();
        int i;
        while (!success) {
            try {
                x = (int)(Math.random() * 2);
                y = (int)(Math.random() * 2);
                i = (int)(Math.random() * 2);
                this.game.rotateTile(x, y, directions[i]);
                success = true;
            } catch (Exception ignored) {}
        }
    }

    /**
     * Gets a Move and sets a marble on the board and rotates a tile.
     * Expects the Move to be valid, throws error if it is not.
     *
     * @param move the move to be done
     */
    private void actByMove(Move move) {
        game.addMarble(move.getMarbleX(), move.getMarbleY());
        game.rotateTile(move.getTileX(), move.getTileY(), move.getRotateDirection());
    }
}
