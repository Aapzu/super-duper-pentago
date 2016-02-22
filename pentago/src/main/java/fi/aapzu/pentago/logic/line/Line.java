/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic.line;

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
    private ArrayList<LinePoint> linePoints = new ArrayList<>();
    private Player player;

    /**
     * Adds a coordinate (x, y) to the Line.
     *
     * @param c the coordinate to be added
     */
    public void addLinePoint(LinePoint lp) {
        if(!linePoints.isEmpty() && !linePoints.get(0).getMarble().getSymbol().equals(lp.getMarble().getSymbol())) {
            linePoints.remove(0);
        }
        linePoints.add(lp);
    }

    /**
     * Gives the coordinates in the Line.
     *
     * @return coordinates
     */
    public ArrayList<LinePoint> getLinePoints() {
        return linePoints;
    }

    /**
     * Gives the current length of the Line.
     *
     * @return length
     */
    public int length() {
        return linePoints.size();
    }

    /**
     * Gives the Symbol of the Line.
     * 
     * @return symbol
     */
    public Symbol getSymbol() {
        if(linePoints.isEmpty()) {
            return null;
        }
        return linePoints.get(0).getMarble().getSymbol();
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
        for (LinePoint l : linePoints) {
            line += "\n\t" + Arrays.toString(l.getCoords());
        }
        return line;
    }

    /**
     * Removes the Symbol and Player, clears coordinates.
     */
    public void clear() {
        symbol = null;
        player = null;
        linePoints.clear();
    }
}
