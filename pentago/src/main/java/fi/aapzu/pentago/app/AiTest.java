package fi.aapzu.pentago.app;

import fi.aapzu.pentago.game.Pentago;
import fi.aapzu.pentago.logic.Board;
import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Line;

import java.util.*;

public class AiTest {

    static Direction[] directions = Direction.getRotateDirections();
    static int maxDepth;
    static int tests;
    static HashSet<Pentago> games;

    public static void main(String[] args) throws CloneNotSupportedException {
        games = new HashSet<>();
        Pentago game = new Pentago();
        maxDepth = 1;
        tests = 0;
        long ts = System.currentTimeMillis();
        start();
        System.out.println("Time: " + (System.currentTimeMillis() - ts));
    }

    static void start() {

    }

    static void putMarble(Pentago oldGame, int depth) {
        int sideLength = oldGame.getBoard().getSideLength() * oldGame.getBoard().getTileSideLength();
        ArrayList<Pentago> games = new ArrayList<>();
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                if (oldGame.getBoard().getMarble(x, y) == null) {
                    Pentago game = oldGame.clone();
                    game.addMarble(x, y);
                    games.add(game);
                }
            }
        }
        for (Pentago game : games) {
            tryRotateTile(game, depth);
        }
    }

    static void tryRotateTile(Pentago oldGame, int depth) {
        int sideLength = oldGame.getBoard().getSideLength();
        ArrayList<Map> games = new ArrayList<>();
        for (int y = 0; y < sideLength; y++) {
            for (int x = 0; x < sideLength; x++) {
                for (Direction d : directions) {
                    Pentago game = oldGame.clone();
                    Map<String, Object> map = new HashMap<>();
                    map.put("game", game);
                    map.put("x", x);
                    map.put("y", y);
                    map.put("d", d);
                    games.add(map);
                }
            }
        }
        for (Map map : games) {
            Pentago game = (Pentago)map.get("game");
            try {
                game.rotateTile((int)map.get("x"), (int)map.get("y"), (Direction)map.get("d"));
                testWinning(game, depth);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    static void testWinning(Pentago game, int depth) {
        System.out.println(++tests);
        Line line = game.checkLines();
        if (line != null) {
            System.out.println("Found a line! Line: " + line.toString());
        } else {
            if (depth < maxDepth && !games.contains(game)) {
                games.add(game);
                putMarble(game, depth+1);
            }
        }
    }
}
