/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic.line;

import fi.aapzu.pentago.logic.marble.Marble;

/**
 *
 * @author aapeli
 */
public class LinePoint {
    
    private final Marble m;
    private final Integer[] coords;
    
    public LinePoint(Marble m, Integer[] coords) {
        this.m = m;
        this.coords = coords;
    }
    
    public Marble getMarble() {
        return m;
    }
    
    public Integer[] getCoords() {
        return coords;
    }
}
