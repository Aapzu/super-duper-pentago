/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Symbol;
import fi.aapzu.pentago.util.DynamicArray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A line on a Board.
 *
 * @author Aapeli
 */
public class Line {

    private Symbol symbol;
    private final DynamicArray<Integer[]> coordinates = new DynamicArray<>();
    private Direction direction;
    private Player player;

    /**
     * Adds a coordinate (x, y) to the Line.
     *
     * @param c the coordinate to be added
     */
    void addCoordinate(Integer[] c) {
        coordinates.add(c);
    }

    public DynamicArray<Integer[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Gives the current length of the Line.
     *
     * @return length the length of the line
     */
    public int length() {
        return coordinates.size();
    }

    @Override
    public String toString() {
        String line = "Player: " + getPlayer()
                + "\nSymbol: " + getSymbol()
                + "\nLine:";
        for (Integer[] a : coordinates) {
            line += "\n\t" + Arrays.toString(a);
        }
        return line;
    }

    /**
     * Removes the Symbol and Player, clears coordinates.
     */
    public void clear() {
        symbol = null;
        player = null;
        coordinates.clear();
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
