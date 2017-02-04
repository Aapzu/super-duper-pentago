package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Direction;

import java.util.ArrayList;

/**
 * A class to determine the possible games one can get from a game state with a single move
 */
public class PossibleGamesWithOneMove {

    /**
     * @param game
     * @return gameStates
     */
    public static Pentago[] get(Pentago game) {
        ArrayList<Pentago> games = new ArrayList<>();
        Board board = game.getBoard();
        int wholeLength = board.getTileSideLength() * board.getSideLength();
        for (int y = 0; y < wholeLength; y++) {
            for (int x = 0; x < wholeLength; x++) {
                Pentago newGame = new Pentago(game);
                try {
                    newGame.addMarble(x, y);
                    for (int tileY = 0; tileY < board.getSideLength(); tileY++) {
                        for (int tileX = 0; tileX < board.getSideLength(); tileX++) {
                            for (Direction d :Direction.getRotateDirections()) {
                                try {
                                    Pentago newNewGame = new Pentago(newGame);
                                    newNewGame.rotateTile(tileX, tileY, d);
                                    games.add(newNewGame);
                                } catch (Exception ignored) {}
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }
        }
        return games.toArray(new Pentago [games.size()]);
    }
}
