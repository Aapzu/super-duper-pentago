package fi.aapzu.pentago.game;

import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.marble.Marble;

/**
 * A move in the game.
 */
public class Move {

    private final int player;

    private final Marble marble;

    private final int marbleX;
    private final int marbleY;
    private final int tileX;
    private final int tileY;
    private final Direction rotateDirection;

    /**
     * Constructor of Move.
     *
     * @param player index of the Player who made the move
     * @param marble instance of the Marble set in the move
     * @param marbleX x position of the Marble set
     * @param marbleY y position of the Marble set
     * @param tileX x position of the rotated Tile
     * @param tileY y position of the rotated Tile
     * @param rotateDirection Direction of the rotated tile
     */
    Move(int player, Marble marble, int marbleX, int marbleY, int tileX, int tileY, Direction rotateDirection) {
        this.player = player;
        this.marbleX = marbleX;
        this.marbleY = marbleY;
        this.marble = marble;
        this.tileX = tileX;
        this.tileY = tileY;
        this.rotateDirection = rotateDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Move move = (Move) o;
        return marbleX == move.marbleX &&
            marbleY == move.marbleY &&
            tileX == move.tileX &&
            tileY == move.tileY &&
            player == move.getPlayer() &&
            marble.equals(move.marble) &&
            rotateDirection == move.rotateDirection;
    }

    @Override
    public String toString() {
        return "Move{" +
            "player=" + player +
            ", marble=" + marble +
            ", marbleX=" + marbleX +
            ", marbleY=" + marbleY +
            ", tileX=" + tileX +
            ", tileY=" + tileY +
            ", rotateDirection=" + rotateDirection +
            '}';
    }

    public int getPlayer() {
        return player;
    }

    public Marble getMarble() {
        return marble;
    }

    public int getMarbleX() {
        return marbleX;
    }

    public int getMarbleY() {
        return marbleY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public Direction getRotateDirection() {
        return rotateDirection;
    }

}
