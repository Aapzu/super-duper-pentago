/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.aapzu.pentago.logic.marble;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Aapeli
 */
public class MarbleCollectionTest {
    
    MarbleCollection collection;
    
    public MarbleCollectionTest() {}
    
    @Before
    public void setUp() {
        collection = new MarbleCollection(Symbol.X);
    }
    
    @After
    public void tearDown() {
        collection = null;
    }

    @Test
    public void constructorFillsTheCollection() {
        assertEquals(18, collection.size());
    }
    
    @Test
    public void constructorWorksWithDifferentParameters() {
        collection = new MarbleCollection(Symbol.O, 9);
        assertEquals(Symbol.O, collection.getMarble().getSymbol());
        assertEquals(9, collection.size());
        assertEquals(9, collection.getMaxSize());
    }
    
    @Test
    public void removeMarbleWorksIfThereAreMarblesInTheCollection() {
        collection.removeMarble();
        assertEquals(17, collection.size());
    }
    
    @Test
    public void removeMarbleReturnsTrueIfThereAreMarblesInTheCollection() {
        assertTrue(collection.removeMarble());
    }
    
    @Test
    public void removeMarbleDoesNothingIfThereAreNoMarblesInTheCollection() {
        for(int i = 0; i < 18; i++) {
            collection.removeMarble();
        }
        assertEquals(0, collection.size());
        collection.removeMarble();
        assertEquals(0, collection.size());
    }
    
    @Test
    public void removeMarbleReturnsFalseIfThereAreNoMarblesInTheCollection() {
        for(int i = 0; i < 18; i++) {
            collection.removeMarble();
        }
        assertFalse(collection.removeMarble());
    }
    
    @Test
    public void addMarbleAddsMarbleIfSizeNotTooBig() {
        collection.removeMarble();
        Marble m = new Marble(Symbol.X);
        collection.addMarble(m);
        assertEquals(m, collection.getMarble());
    }
    
    @Test
    public void addMarbleReturnsTrueIfSizeNotTooBig() {
        collection.removeMarble();
        assertTrue(collection.addMarble(new Marble(Symbol.X)));
    }
    
    @Test
    public void addMarbleDoesNotAddMarbleIfSizeIsTooBig() {
        Marble m = new Marble(Symbol.O);
        assertEquals(18, collection.size());
        collection.addMarble(m);
        assertEquals(18, collection.size());
        assertNotEquals(collection.getMarble(), m);
    }
    
    @Test
    public void addMarbleReturnsFalseIfSizeIsTooBig() {
        assertFalse(collection.addMarble(new Marble(Symbol.X)));
    }
    
    @Test
    public void getMarbleReturnsTheLastMarble() {
        assertEquals(collection.getMarble(17), collection.getMarble());
    }
    
    @Test
    public void getMarbleReturnsNullIfInvalidCoordinates() {
        assertNull(collection.getMarble(-1));
        assertNull(collection.getMarble(18));
    }
}
