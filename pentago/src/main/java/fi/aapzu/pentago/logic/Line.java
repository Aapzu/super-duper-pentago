/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic;

import fi.aapzu.pentago.game.Player;
import fi.aapzu.pentago.logic.marble.Symbol;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A line on a Board.
 *
 * @author Aapeli
 */
public class Line {

    private Symbol symbol;
    private ArrayList<Integer[]> coordinates = new ArrayList<>();
    private Player player;

    /**
     * Adds a coordinate (x, y) to the Line.
     *
     * @param c the coordinate to be added
     */
    public void addCoordinate(Integer[] c) {
        coordinates.add(c);
    }

    /**
     * Gives the coordinates in the Line.
     *
     * @return coordinates
     */
    public ArrayList<Integer[]> getCoordinates() {
        return coordinates;
    }

    /**
     * Gives the current length of the Line.
     *
     * @return length
     */
    public int length() {
        return coordinates.size();
    }

    /**
     * Sets the Symbol of the Line.
     * 
     * @param s symbol
     */
    public void setSymbol(Symbol s) {
        symbol = s;
    }

    /**
     * Gives the Symbol of the Line.
     * 
     * @return symbol
     */
    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Sets the Player who owns the Line.
     * 
     * @param p player
     */
    public void setPlayer(Player p) {
        player = p;
    }

    /**
     * Returns the owner of the Line.
     * 
     * @return player
     */
    public Player getPlayer() {
        return player;
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
}
