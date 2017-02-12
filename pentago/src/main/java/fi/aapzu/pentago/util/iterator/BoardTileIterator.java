package fi.aapzu.pentago.util.iterator;

import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Tile;

import java.util.Iterator;

public class BoardTileIterator implements Iterator<Tile> {

    private final Board board;
    private int y;
    private int x;

    /**
     * Creates new instance of BoardTileIterator.
     *
     * @param board to be iterated
     */
    public BoardTileIterator(Board board) {
        this.board = board;
        this.y = 0;
        this.x = -1;
    }

    @Override
    public boolean hasNext() {
        return y + 1 < board.getSideLength() || x + 1 < board.getSideLength();
    }

    @Override
    public Tile next() {
        x++;
        if (x >= board.getSideLength()) {
            x = 0;
            y++;
        }
        return board.getTile(getX(), getY());
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

}
