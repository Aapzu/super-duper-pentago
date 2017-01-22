package fi.aapzu.pentago.game;


import fi.aapzu.pentago.logic.Direction;

public class Bot {

    Pentago game;

    public Bot(Pentago game) {
        this.game = game;
    }

    public void makeMove() {
        boolean success = false;
        int y;
        int x;
        while (!success) {
            try {
                y = (int)(Math.random() * 6);
                x = (int)(Math.random() * 6);
                this.game.addMarble(x, y);
                success = true;
            } catch (Exception e) {}
        }
        success = false;
        Direction[] directions = Direction.getRotateDirections();
        int i;
        while (!success) {
            try {
                x = (int)(Math.random() * 2);
                y = (int)(Math.random() * 2);
                i = (int)(Math.random() * 2);
                this.game.rotateTile(x, y, directions[i]);
                success = true;
            } catch (Exception e) {}
        }
    }
}
