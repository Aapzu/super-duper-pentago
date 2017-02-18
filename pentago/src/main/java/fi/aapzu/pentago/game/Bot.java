package fi.aapzu.pentago.game;

import fi.aapzu.pentago.ai.AlphaBetaPruning;
import fi.aapzu.pentago.ai.PentagoNode;
import fi.aapzu.pentago.logic.Direction;

/**
 * A bot which makes decisions on behalf of a Player.
 */
public class Bot extends Player {

    private final Pentago game;
    private final AlphaBetaPruning alphaBetaPruning;

    /**
     * Constructor for Bot.
     *
     * @param game Pentago game the bot is playing
     */
    Bot(Pentago game, String name) {
        super(name);
        this.game = game;
        this.alphaBetaPruning = new AlphaBetaPruning();
    }

    /**
     * Calculates the best move and acts by it.
     *
     * @return true
     */
    @Override
    public boolean makeMove() {
        PentagoNode bestGame = (PentagoNode) alphaBetaPruning.getBest(new PentagoNode(game.serialize()), 2);
        Pentago game = new Pentago();
        game.deserialize(bestGame.getSerializationString());
        Move move = game.getLastMove();
        actByMove(move);
        return true;
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
