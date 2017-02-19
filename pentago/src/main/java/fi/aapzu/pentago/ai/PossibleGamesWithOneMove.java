package fi.aapzu.pentago.ai;

import fi.aapzu.pentago.logic.Direction;
import fi.aapzu.pentago.logic.Tile;
import fi.aapzu.pentago.logic.marble.Marble;
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
            for (int[] pos : ROTATION_TILE_DIRECTION_POSSIBILITIES) {
                if (!rotationTileDirectionIsOpposite(ints, pos)) {
                    chars = rotateTilePart(pos[0], pos[1], pos[2], chars);
                    chars[38] = Character.forDigit(pos[0], 10);
                    chars[39] = Character.forDigit(pos[1], 10);
                    chars[40] = Character.forDigit(pos[2], 10);
                    chars[41] = Character.forDigit(1 - Character.getNumericValue(chars[41]), 10);
                    games.add(new String(chars));
                }
            }
        }
        return games;
    }

    private static char[] rotateTilePart(int tileX, int tileY, int dir, char[] serChar) {
        int startI = tileX * 18 + tileY * 9;
        char c0 = serChar[startI],
                c1 = serChar[startI + 1],
                c2 = serChar[startI + 2],
                c3 = serChar[startI + 3],
                c4 = serChar[startI + 4],
                c5 = serChar[startI + 5],
                c6 = serChar[startI + 6],
                c7 = serChar[startI + 7],
                c8 = serChar[startI + 8];
        if (dir == 1) {
            serChar[startI] = c6;
            serChar[startI + 1] = c3;
            serChar[startI + 2] = c0;
            serChar[startI + 3] = c7;
            serChar[startI + 5] = c1;
            serChar[startI + 6] = c8;
            serChar[startI + 7] = c5;
            serChar[startI + 8] = c2;
        } else if (dir == 2) {
            serChar[startI] = c2;
            serChar[startI + 1] = c5;
            serChar[startI + 2] = c8;
            serChar[startI + 3] = c1;
            serChar[startI + 5] = c7;
            serChar[startI + 6] = c0;
            serChar[startI + 7] = c3;
            serChar[startI + 8] = c6;
        }
        return serChar;
    }

    private static String[] getGamesAfterMarbleInsertion(String s, char symbol) {
        ArrayList<String> gamesBeforeRotation = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            if (s.charAt(i) == '0') {
                char[] chars = s.toCharArray();
                chars[i] = symbol;
                chars[36] = getMarbleXFromSerializationStringIndex(i);
                chars[37] = getMarbleYFromSerializationStringIndex(i);
                gamesBeforeRotation.add(new String(chars));
            }
        }
        return gamesBeforeRotation.toArray(new String[gamesBeforeRotation.size()]);
    }

    private static char getMarbleXFromSerializationStringIndex(int i) {
        return Character.forDigit(3 * (i / 18) + i % 3, 10);
    }

    private static char getMarbleYFromSerializationStringIndex(int i) {
        return Character.forDigit(i / 3 - 6 * (i / 18), 10);
    }
}
