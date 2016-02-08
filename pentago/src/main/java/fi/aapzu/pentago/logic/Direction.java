
package fi.aapzu.pentago.logic;

public enum Direction {
    CLOCKWISE, COUNTER_CLOCKWISE, VERTICAL, HORIZONTAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL;
    
    public static Direction[] getRotateDirections() {
        return new Direction[]{CLOCKWISE, COUNTER_CLOCKWISE};
    }
    
    public static Direction[] getLineDirections() {
        return new Direction[]{HORIZONTAL, VERTICAL, UPGRADING_DIAGONAL, DOWNGRADING_DIAGONAL};
    }
    
    public static Direction getOpposite(Direction d) {
        if(d == CLOCKWISE)
            return COUNTER_CLOCKWISE;
        else if(d == COUNTER_CLOCKWISE)
            return CLOCKWISE;
        else
            return null;
    }
}
