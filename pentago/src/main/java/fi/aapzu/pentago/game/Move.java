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

    public Player getPlayer() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (marbleX != move.marbleX) return false;
        if (marbleY != move.marbleY) return false;
        if (tileX != move.tileX) return false;
        if (tileY != move.tileY) return false;
        if (!player.equals(move.player)) return false;
        if (!marble.equals(move.marble)) return false;
        return rotateDirection == move.rotateDirection;
    }
}
