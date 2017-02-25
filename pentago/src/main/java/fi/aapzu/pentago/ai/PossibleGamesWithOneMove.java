package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Tile;
import fi.aapzu.pentago.util.ArrayUtils;
import fi.aapzu.pentago.util.DynamicArray;

import java.util.ArrayList;

/**
 * A class to determine the possible games one can get from a game state with a single move
 */
class PossibleGamesWithOneMove {

    private static final int[][] ROTATION_TILE_DIRECTION_POSSIBILITIES = {
        {0, 0, 1}, {0, 0, 2}, {0, 1, 1}, {0, 1, 2},
        {1, 0, 1}, {1, 0, 2}, {1, 1, 1}, {1, 1, 2},
    };

    private static boolean rotationTileDirectionIsOpposite(int[] first, int[] second) {
        return first[0] == second[0] &&
                first[1] == second[1] &&
                first[2] != 0 &&
                second[2] != 0 &&
                ((first[2] == 1 && second[2] == 2) ||
                (first[2] == 2 && second[2] == 1));
    }

    /**
     * Gives all the possible moves after one move
     *
     * @param s serialization String of the Pentago game
     * @return gameStates
     */
    static DynamicArray<String> get(String s) {
        char symbol = s.charAt(s.length() - 1) == '0' ? '1' : '2';
        return getGamesAfterRotationAndMarbleInsertion(s, symbol);
    }

    private static DynamicArray<String> getGamesAfterRotationAndMarbleInsertion(String s, char symbol) {
        DynamicArray<String> games = new DynamicArray<>();
        for (String g : getGamesAfterMarbleInsertion(s, symbol)) {
            char[] chars = g.toCharArray();
            int[] ints = new int[]{
                    Character.getNumericValue(chars[38]),
                    Character.getNumericValue(chars[39]),
                    Character.getNumericValue(chars[40])
            };
            int gameStarted = Character.getNumericValue(chars[42]);
            for (int[] pos : ROTATION_TILE_DIRECTION_POSSIBILITIES) {
                char[] newChars = ArrayUtils.clone(chars);
                if (gameStarted == 0 || !rotationTileDirectionIsOpposite(ints, pos)) {
                    int tileX = pos[0], tileY = pos[1], tileDir = pos[2];
                    int startI = tileY * 18 + tileX * 9;
                    char[] clonedChars = ArrayUtils.clone(chars);
                    for (int i = startI, j = 0; i < startI + 9; i++, j++) {
                        int newI = startI + translateTileIndex(j, tileDir);
                        newChars[newI] = clonedChars[i];
                    }
                    newChars[38] = Character.forDigit(pos[0], 10); // lastRotatedTileX
                    newChars[39] = Character.forDigit(pos[1], 10); // lastRotatedTileY
                    newChars[40] = Character.forDigit(pos[2], 10); // lastRotatedTileDirection
                    newChars[41] = Character.forDigit(1 - Character.getNumericValue(newChars[41]), 10);
                    newChars[42] = '1';
                    String string = new String(newChars);
                    games.add(string);
                }
            }
        }
        return games;
    }

    private static int translateTileIndex(int i, int dir) {
        int x = i % 3, y = i / 3;
        int[] newCoordinates;
        if (dir == 1) {
            newCoordinates = Tile.translateCoordinates(x, y, Direction.CLOCKWISE, 3);
        } else if (dir == 2) {
            newCoordinates = Tile.translateCoordinates(x, y, Direction.COUNTER_CLOCKWISE, 3);
        } else {
            throw new IllegalArgumentException("Invalid direction!");
        }
        return newCoordinates[1] * 3 + newCoordinates[0];
    }

    private static DynamicArray<String> getGamesAfterMarbleInsertion(String s, char symbol) {
        DynamicArray<String> gamesBeforeRotation = new DynamicArray<>();
        for (int i = 0; i < 36; i++) {
            if (s.charAt(i) == '0') {
                char[] chars = s.toCharArray();
                chars[i] = symbol;
                chars[36] = getMarbleXFromSerializationStringIndex(i);
                chars[37] = getMarbleYFromSerializationStringIndex(i);
                gamesBeforeRotation.add(new String(chars));
            }
        }
        return gamesBeforeRotation;
    }

    static char getMarbleXFromSerializationStringIndex(int i) {
        int x = (i % 18 / 9 * 3) + (i % 18) % 3; // TODO: simplify if possible
        return Character.forDigit(x, 10);
    }

    static char getMarbleYFromSerializationStringIndex(int i) {
        int y = i / 18 * 3 + i / 3 % 3; // TODO: simplify if possible
        return Character.forDigit(y, 10);
    }
}
