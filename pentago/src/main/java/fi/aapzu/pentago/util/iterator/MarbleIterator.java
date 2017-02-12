package fi.aapzu.pentago.util.iterator;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Tile;
import fi.aapzu.pentago.logic.marble.Marble;

import java.util.Iterator;

/**
 * Iterates through all marbles in a Board.
 */
public class MarbleIterator implements Iterator<Marble> {

    private final Marble[][] marbles;
    private int y;
    private int x;

    /**
     * Creates new instance of the MarbleIterator for the given Board.
     *
     * @param board to be used
     */
    public MarbleIterator(Board board) {
        this.marbles = board.toMarbleArray();
        this.y = 0;
        this.x = -1;
    }

    /**
     * Creates new instance of the MarbleIterator for the given Tile.
     *
     * @param tile to be used
     */
    public MarbleIterator(Tile tile) {
        this.marbles = tile.toMarbleArray();
        this.y = 0;
        this.x = -1;
    }

    @Override
    public boolean hasNext() {
        return x != marbles[0].length - 1 || y + 1 < marbles.length;
    }

    @Override
    public Marble next() {
        x++;
        if (getX() >= marbles[0].length) {
            x = 0;
            y++;
        }
        return marbles[y][x];
    }

    private void setY(int y) {
        this.y = y;
    }

    private void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
