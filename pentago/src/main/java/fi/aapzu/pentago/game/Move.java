package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.marble.Marble;

/**
 * A move in the game
 */
public class Move {

    private Player player;
    private Marble marble;
    private int marbleX;
    private int marbleY;
    private int tileX;
    private int tileY;
    private Direction rotateDirection;

    Player getPlayer() {
        return player;
    }

    Marble getMarble() {
        return marble;
    }

    int getMarbleX() {
        return marbleX;
    }

    int getMarbleY() {
        return marbleY;
    }

    int getTileX() {
        return tileX;
    }

    int getTileY() {
        return tileY;
    }

    Direction getRotateDirection() {
        return rotateDirection;
    }

    /**
     *
     * @param player
     * @param marble
     * @param marbleX
     * @param marbleY
     * @param tileX
     * @param tileY
     * @param rotateDirection
     */
    Move(Player player, Marble marble, int marbleX, int marbleY, int tileX, int tileY, Direction rotateDirection) {
        this.player = player;
        this.marbleX = marbleX;
        this.marbleY = marbleY;
        this.marble = marble;
        this.tileX = tileX;
        this.tileY = tileY;
        this.rotateDirection = rotateDirection;
    }
}
